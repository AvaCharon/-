package edu.hitsz.aircraft;

import edu.hitsz.strategy.Collineation;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.bullet.AbstractBaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;
    protected int power;
    protected Strategy shootMode;
    protected int direction ;
    protected int shootNum  ;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int power) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.power = power;
        shootMode = new Collineation();
    }

    /**
     * 设置射击模式
     * @param strategy
     */
    public void setShootMode(Strategy strategy){
        this.shootMode = strategy;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
        if(hp >= maxHp){
            hp = maxHp;
        }
    }

    public int getHp() {
        return this.hp;
    }

    public int getDirection(){return this.direction; }

    public int getPower(){ return this.power;}

    public int getShootNum(){return this.shootNum; }

    public void setShootNum(int shootNum){this.shootNum=shootNum;}

    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */

    public abstract List<AbstractBaseBullet> shoot();

}


