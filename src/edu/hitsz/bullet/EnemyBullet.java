package edu.hitsz.bullet;

import edu.hitsz.basic.Bomb;

/**
 * @Author hitsz
 */
public class EnemyBullet extends AbstractBaseBullet implements Bomb {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void update(){
        vanish();
    }

}
