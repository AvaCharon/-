package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class MobEnemyFactory implements EnemyFactory{

    @Override
    public MobEnemy createEnemy()
    {
        return new MobEnemy(
            (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1,
            (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
            0,
            10,
            30,
            0
        );
    }
}
