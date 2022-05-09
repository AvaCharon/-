package edu.hitsz.aircraft;

import edu.hitsz.strategy.Scattering;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.application.MusicThread;
import edu.hitsz.basic.Bomb;
import edu.hitsz.bullet.AbstractBaseBullet;
import edu.hitsz.factory.BloodReturnPropFactory;
import edu.hitsz.factory.BombPropFactory;
import edu.hitsz.factory.BulletPropFactory;
import edu.hitsz.prop.AbstractBaseProp;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BossEnemy extends AbstractEnemyAircraft implements Bomb {

    private Strategy shootMode;
    private int shootNum = 5;
    private int power = 10;
    private boolean isStop = false;
    private MusicThread bossBGM;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power ,int shootNum , boolean isStop) {
        super(locationX, locationY, speedX, speedY, hp, power);
        this.shootMode = new Scattering();
        this.shootNum = shootNum;
        this.isStop = isStop;
        //播放boss机bgm
        bossBGM = new MusicThread("src/videos/bgm_boss.wav",isStop,true);
        bossBGM.start();
    }

    public List<AbstractBaseProp> dropProp(){
        List<AbstractBaseProp> res = new LinkedList<>();
        AbstractBaseProp prop;
        BloodReturnPropFactory bloodReturnPropFactory = new BloodReturnPropFactory();
        BombPropFactory bombPropFactory = new BombPropFactory();
        BulletPropFactory bulletPropFactory = new BulletPropFactory();
        for(int i=0;i<3;i++){
            int temp = new Random().nextInt(6);
            if (temp <= 2) {
                prop = bloodReturnPropFactory.createProp(this.getLocationX()-30+i*30, this.getLocationY(),5);
                res.add(prop);
            }
            else if (temp <= 4) {
                prop = bombPropFactory.createProp(this.getLocationX()-30+i*30, this.getLocationY(), 5);
                res.add(prop);
            }
            else{
                prop = bulletPropFactory.createProp(this.getLocationX() - 30 + i * 30, this.getLocationY(), 5);
                res.add(prop);
            }
        }
        return res;
    }

    @Override
    public List<AbstractBaseBullet> shoot() {
        return this.shootMode.shoot(this);
    }

    @Override
    public void vanish() {
        //结束播放bgm
        bossBGM.setToEnd(true);
        bossBGM.setRunning(false);
        isValid = false;
    }

    @Override
    public int getShootNum(){return this.shootNum; }

    @Override
    public void update(){}


}
