package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.AbstractBaseBullet;

import java.util.List;

public interface Strategy {
    /**
     * 射出子弹
     * @param abstractAircraft 射出子弹的主体
     * @return 子弹集合
     */
    public abstract List<AbstractBaseBullet> shoot(AbstractAircraft abstractAircraft);
}
