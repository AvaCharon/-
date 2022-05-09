package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.AbstractBaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 直射策略
 */
public class Collineation implements Strategy{

    @Override
    public List<AbstractBaseBullet> shoot(AbstractAircraft abstractAircraft) {
        List<AbstractBaseBullet> res = new LinkedList<>();
        int direction = abstractAircraft.getDirection();
        int power = abstractAircraft.getPower();
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = abstractAircraft.getSpeedY() + direction*5;
        if(abstractAircraft instanceof HeroAircraft) {
            res.add(new HeroBullet(x, y, speedX, speedY, power));
            return res;
        }
        res.add(new EnemyBullet( x , y, speedX, speedY, power));
        return res;
    }
}
