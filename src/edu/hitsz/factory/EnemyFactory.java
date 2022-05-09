package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;

public interface EnemyFactory {
    /**
     *生成敌机
     * @return 敌机实例
     */
    public AbstractEnemyAircraft createEnemy();
}
