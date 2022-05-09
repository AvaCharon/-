package edu.hitsz.aircraft;

import edu.hitsz.basic.Bomb;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractEnemyAircraft implements Bomb {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int power) {
        super(locationX, locationY, speedX, speedY, hp, 0);
    }

    @Override
    public void update(){
        vanish();
    }
}
