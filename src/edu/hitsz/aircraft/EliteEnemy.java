package edu.hitsz.aircraft;

import edu.hitsz.strategy.Collineation;
import edu.hitsz.basic.Bomb;
import edu.hitsz.bullet.AbstractBaseBullet;
import edu.hitsz.factory.BloodReturnPropFactory;
import edu.hitsz.factory.BombPropFactory;
import edu.hitsz.factory.BulletPropFactory;
import edu.hitsz.factory.PropFactory;
import edu.hitsz.prop.AbstractBaseProp;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EliteEnemy extends AbstractEnemyAircraft implements Bomb {

    /**
     * 子弹伤害
     */
    private int power = 10;


    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int power) {
        super(locationX, locationY, speedX, speedY, hp, power);
        shootMode = new Collineation();
    }

    @Override
    public List<AbstractBaseBullet> shoot() {
        return this.shootMode.shoot(this);
    }


    public List<AbstractBaseProp> dropProp(){
        List<AbstractBaseProp> res = new LinkedList<>();
        AbstractBaseProp prop;
        int temp = new Random().nextInt(10);
        if (temp<=2) {
            PropFactory propFactory = new BloodReturnPropFactory();
            prop=propFactory.createProp(this.getLocationX(), this.getLocationY(), 5);
            res.add(prop);
        }
        else if(temp<=4){
            PropFactory propFactory = new BombPropFactory();
            prop=propFactory.createProp(this.getLocationX(),this.getLocationY(), 5);
            res.add(prop);
        }
        else if(temp<=6){
            PropFactory propFactory = new BulletPropFactory();
            prop = propFactory.createProp(this.getLocationX(), this.getLocationY(), 5);
            res.add(prop);
        }
        return res;
    }

    @Override
    public void update(){
        vanish();
    }
}
