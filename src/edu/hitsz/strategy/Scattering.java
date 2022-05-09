package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.AbstractBaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 散射策略
 */
public class Scattering implements Strategy{

    @Override
    public List<AbstractBaseBullet> shoot(AbstractAircraft abstractAircraft) {
        List<AbstractBaseBullet> res = new LinkedList<>();
        int power = abstractAircraft.getPower();
        int direction = abstractAircraft.getDirection();
        int shootNum = abstractAircraft.getShootNum();
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = abstractAircraft.getSpeedY() + direction*5;
        AbstractBaseBullet baseBullet;
        if(abstractAircraft instanceof HeroAircraft) {
            for (int i = 0; i < shootNum; i++) {
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX+(i * 2 * shootNum / (shootNum - 1) -shootNum) * 1, speedY, power);
                res.add(baseBullet);
            }
            return res;
        }
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            baseBullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX+(i * 2 * shootNum / (shootNum - 1) -shootNum) * 1, speedY, power);
            res.add(baseBullet);
        }
        return res;
    }
}
