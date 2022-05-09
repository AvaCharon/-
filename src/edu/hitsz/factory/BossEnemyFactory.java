package edu.hitsz.factory;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;

public class BossEnemyFactory implements EnemyFactory{

    public boolean isStop;

    public BossEnemyFactory(boolean isStop){
        this.isStop=isStop;
    }

    @Override
    public AbstractEnemyAircraft createEnemy() {
        System.out.println("Boss has come");
        return new BossEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                10,
                0,
                300,
                10,
                5,
                isStop);
    }

    public AbstractEnemyAircraft createEnemy(int hp){
        System.out.println("Boss has come");
        return new BossEnemy(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                10,
                0,
                hp,
                10,
                5,
                isStop);
    }
}
