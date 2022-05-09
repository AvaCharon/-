package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.basic.Bomb;
import edu.hitsz.bullet.AbstractBaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.dao.User;
import edu.hitsz.dao.UserDaoImpl;
import edu.hitsz.factory.*;
import edu.hitsz.prop.AbstractBaseProp;
import edu.hitsz.prop.BombProp;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class BaseGame extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractEnemyAircraft> enemyAircrafts;
    private final List<AbstractBaseBullet> heroBullets;
    private final List<AbstractBaseBullet> enemyBullets;
    private final List<AbstractBaseProp> props;

    /**
     * 工厂
     */
    protected MobEnemyFactory mobEnemyFactory;
    protected EliteEnemyFactory eliteEnemyFactory;
    protected BossEnemyFactory bossEnemyFactory;

    protected int enemyMaxNumber ;
    protected int propMaxNumber = 5;
    /**
     * 同时最大boss机数量
     */
    protected int bossMaxNumber ;
    /**
     * 敌机增强幅度
     */
    protected double rate = 1;
    /**
     * 精英机产生概率
     */
    protected double eliteRate = 0.2;

    /**
     * 每多少分生成一架boss机
     */
    private static int SCORETOCREATEBOSS = 200;
    /**
     * 曾经生成boss机数量
     */
    protected int bossHadCreate = 0;
    /**
     * 当前boss机数量
     */
    protected int bossCurrent = 0;

    private boolean gameOverFlag = false;
    private int score = 0;
    private int time = 0;
    private String degree;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration ;
    private int cycleTime = 0;

    public UserDaoImpl userDao = Main.userDao;
    private static Object locker = Main.locker;
    /**
     * 音频停止标志
     */
    public boolean isStop = false;
    public MusicThread bgm;

    public BaseGame() {

        heroAircraft = HeroAircraft.getHeroAircraft();
        heroAircraft.restart();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();


        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public final void action() {
        //重新开始播放bgm
        bgm = new MusicThread("src/videos/bgm.wav",isStop,true);
        bgm.start();

        //设置最大敌机数
        setEnemyMaxNumber();
        //设置是否产生Boss机
        setBossMaxNumber();
        //设置射击与敌机产生周期
        setCycleDuration();

        //实例化工厂
        bossEnemyFactory = new BossEnemyFactory(isStop);
        mobEnemyFactory = new MobEnemyFactory();
        eliteEnemyFactory = new EliteEnemyFactory();

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {
            synchronized (locker){
                try {

                    time += timeInterval;

                    // 周期性执行（控制频率）
                    if (timeCountAndNewCycleJudge()) {
                        System.out.println(time);
                        // 新敌机产生
                        if (enemyAircrafts.size() < enemyMaxNumber) {
                            // Boss机产生
                            if (score >= SCORETOCREATEBOSS * (bossHadCreate + 1) && bossCurrent < bossMaxNumber) {
                                //切换bgm
                                bgm.setStop(true);
                                //Boss机每次召唤增强
                                enemyAircrafts.add(bossPropertyBoost());
                                bossHadCreate++;
                                bossCurrent++;
                            } else if ( Math.random() > eliteRate ) {
                                enemyAircrafts.add(mobEnemyFactory.createEnemy());
                            } else {
                                enemyAircrafts.add(eliteEnemyFactory.createEnemy());
                            }
                        }
                        // 飞机射出子弹
                        shootAction();
                        //敌机随时间增强
                        enemyPropertyBoost(enemyAircrafts);
                    }

                    // 子弹移动
                    bulletsMoveAction();

                    // 飞机移动
                    aircraftsMoveAction();

                    // 道具移动
                    propsMoveAction();

                    // 撞击检测
                    crashCheckAction();

                    // 后处理
                    postProcessAction();

                    //每个时刻重绘界面
                    repaint();

                    // 游戏结束检查
                    if (heroAircraft.getHp() <= 0) {
                        // 游戏结束
                        executorService.shutdown();
                        gameOverFlag = true;
                        System.out.println("Game Over!");
                        bgm.setToEnd(true);
                        bgm.setRunning(false);
                        for(AbstractEnemyAircraft abstractEnemyAircraft : enemyAircrafts){
                            abstractEnemyAircraft.vanish();
                        }
                        new MusicThread("src/videos/game_over.wav",isStop,false).start();
                        locker.notify();
                        recode();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // TODO 敌机射击
        for(AbstractAircraft enemy : enemyAircrafts){
            if (enemy.notValid()) {
                continue;
            }
            enemyBullets.addAll(enemy.shoot());
            }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (AbstractBaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (AbstractBaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction(){
        for(AbstractBaseProp prop : props){
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for(AbstractBaseBullet enemyBullet : enemyBullets){
            if(heroAircraft.crash(enemyBullet)){
                heroAircraft.decreaseHp((enemyBullet.getPower()));
                enemyBullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (AbstractBaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    // 播放子弹击中敌机音效
                    enemyAircraft.decreaseHp(bullet.getPower());
                    new MusicThread("src/videos/bullet_hit.wav",isStop,false).start();
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        /**
                         * 精英或Boss敌机坠毁时随机生成道具
                         * @return
                         */
                        if(props.size()<propMaxNumber) {
                            if (enemyAircraft instanceof EliteEnemy){
                                props.addAll(((EliteEnemy) enemyAircraft).dropProp());
                            }
                            else if(enemyAircraft instanceof BossEnemy){
                                props.addAll(((BossEnemy) enemyAircraft).dropProp());
                            }
                        }
                        if(enemyAircraft instanceof BossEnemy){
                            bossCurrent--;
                            //重启bgm
                            bgm.setStop(false);
                        }
                        score += 10;
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for(AbstractBaseProp prop : props){
            if(prop.crash(heroAircraft)){
                if(prop instanceof BombProp){
                    for(Bomb bomb : enemyAircrafts){
                            ((BombProp) prop).addEnemyFlyingObject(bomb);
                            score +=10;
                    }
                    for (Bomb bomb : enemyBullets){
                        ((BombProp) prop).addEnemyFlyingObject(bomb);
                    }
                }
                prop.effectProp(isStop);
                prop.vanish();
            }
        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.getBackgroundImage(getDegree()), 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.getBackgroundImage(getDegree()), 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g,props);

        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

    public String getDegree(){
        return this.degree;
    }

    /**
     * 记录本次游戏信息
     */
    private void recode(){
        SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
        String systemTime =df.format(new Date());
        String username = "player";
        userDao.setDegree(this.getDegree());
        userDao.readFile();
        userDao.temp=new User(username,this.score,systemTime, userDao.degree);
    }



    //***********************
    //      难度调整 各部分
    //***********************
    /**
     *调整敌机数量最大值
     */
    public void setEnemyMaxNumber(){}

    /**
     *调整Boss机数量最大值
     */
    public void setBossMaxNumber(){}

    /**
     *调整敌机产生周期与射击周期
     */
    public void setCycleDuration(){}

    /**
     *Boss机每次召唤增强
     */
    public AbstractEnemyAircraft bossPropertyBoost() {
        return bossEnemyFactory.createEnemy(300);
    }

    /**
     * 敌机随时间增强
     */
    public void enemyPropertyBoost(List<AbstractEnemyAircraft> enemyAircrafts){

    }


}
