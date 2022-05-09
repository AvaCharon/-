package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.AbstractBaseBullet;
import edu.hitsz.bullet.HeroBullet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EliteEnemyTest {


    @DisplayName("Test crash method")
    @Test
    void crash() {
        EliteEnemy eliteEnemy = new EliteEnemy((int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1, (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,0,10,30,10);
        AbstractFlyingObject flyingObject;
        for(int i = 0 ;i < 20 ; i++) {
            flyingObject = new HeroBullet((int)(Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1, (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,0, 0, 15);
            int x = flyingObject.getLocationX();
            int y = flyingObject.getLocationY();
            int locationX = eliteEnemy.getLocationX();
            int locationY = eliteEnemy.getLocationY();
            assertEquals(x + (flyingObject.getWidth() + eliteEnemy.getWidth()) / 2 > locationX
                    && x - (flyingObject.getWidth() + eliteEnemy.getWidth()) / 2 < locationX
                    && y + (flyingObject.getHeight() + eliteEnemy.getHeight()) / 4 > locationY
                    && y - (flyingObject.getHeight() + eliteEnemy.getHeight()) / 4 < locationY, eliteEnemy.crash(flyingObject));
        }
    }


    @DisplayName("Test shoot method")
    @Test
    void shoot() {
        EliteEnemy eliteEnemy = new EliteEnemy((int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1, (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,0,10,30, 10);
        List<AbstractBaseBullet> list = eliteEnemy.shoot();
        for(AbstractBaseBullet it : list)
        {
            assertEquals(it.getLocationX(),eliteEnemy.getLocationX());
            assertEquals(it.getLocationY(),(eliteEnemy.getLocationY()+2));
        }
    }
}
