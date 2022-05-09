package edu.hitsz.bullet;

import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EnemyBulletTest {



    @ParameterizedTest
    @DisplayName("Test forward method")
    @CsvSource({"1,0","0,1","0,0","1,1","10,10","10000,10000"})
    void forward(int locationX,int locationY) {
        EnemyBullet enemyBullet = new EnemyBullet(locationX,locationY,10,10,10);
        if(enemyBullet.getLocationX() < 0 || enemyBullet.getLocationX() > Main.WINDOW_WIDTH || (enemyBullet.getSpeedY() > 0 && enemyBullet.getLocationY() >= Main.WINDOW_HEIGHT) || enemyBullet.getLocationY()<0){
            enemyBullet.forward();
            assertEquals(false,enemyBullet.getIsValid());
        }
        else {
            enemyBullet.forward();
            assertEquals(true,enemyBullet.getIsValid());
        }
    }

    @DisplayName("Test crash method")
    @Test
    void crash() {
        HeroAircraft heroAircraft = HeroAircraft.getHeroAircraft();
        for(int i = 0 ;i < 20 ; i++) {
            EnemyBullet enemyBullet = new EnemyBullet((int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1, (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,0,10,30);
            int x = heroAircraft.getLocationX();
            int y = heroAircraft.getLocationY();
            int locationX = enemyBullet.getLocationX();
            int locationY = enemyBullet.getLocationY();
            assertEquals(x + (heroAircraft.getWidth() + enemyBullet.getWidth()) / 2 > locationX
                    && x - (heroAircraft.getWidth() + enemyBullet.getWidth()) / 2 < locationX
                    && y + (heroAircraft.getHeight() + enemyBullet.getHeight()) / 2 > locationY
                    && y - (heroAircraft.getHeight() + enemyBullet.getHeight()) / 2 < locationY, enemyBullet.crash(heroAircraft));
        }
    }
}