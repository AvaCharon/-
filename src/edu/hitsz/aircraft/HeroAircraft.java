package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.strategy.Collineation;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = -1;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private volatile static HeroAircraft heroAircraft;

    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp ,int power) {
        super(locationX, locationY, speedX, speedY, hp, power);
    }


    public static HeroAircraft getHeroAircraft() {
        if (heroAircraft == null) {
            synchronized (HeroAircraft.class) {
                if (heroAircraft == null) {
                    heroAircraft = new HeroAircraft(Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 100, 30);
                }
            }
        }
        return heroAircraft;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<AbstractBaseBullet> shoot() {
        return this.shootMode.shoot(this);
    }

    @Override
    public int getDirection(){
        return this.direction;
    }

    @Override
    public int getShootNum(){
        return this.shootNum;
    }

    @Override
    public void setShootNum(int shootNum){
        this.shootNum=shootNum;
    }

    public void restart(){
        this.shootNum=1;
        this.hp = 100;
        this.locationX = Main.WINDOW_WIDTH / 2;
        this.locationY = Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight();
        this.speedX=0;
        this.speedY=0;
        this.shootMode = new Collineation();
    }
}
