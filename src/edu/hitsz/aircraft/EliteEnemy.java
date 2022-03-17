package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends MobEnemy{

    /**
     * 子弹伤害
     */
    private int power = 10;


    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + 2;
        int speedX = 0;
        int speedY = this.getSpeedY() + 3;
        BaseBullet baseBullet;
        baseBullet = new EnemyBullet(x , y, speedX, speedY,power );
        res.add(baseBullet);
        return res;
    }

}
