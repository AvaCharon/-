package edu.hitsz.aircraft;

import edu.hitsz.bullet.AbstractBaseBullet;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;
import java.util.List;

class HeroAircraftTest {

    private HeroAircraft heroAircraft = HeroAircraft.getHeroAircraft();
    private List<AbstractBaseBullet> list;

    @DisplayName("Test decreaseHp method")
    @ParameterizedTest
    @ValueSource(ints={0,10,100,200})
    void decreaseHp(int num) {
        int numTest = heroAircraft.getHp();
        if(numTest>num)
        {
            numTest-=num;
            heroAircraft.decreaseHp(num);
            assertEquals(numTest,heroAircraft.getHp());
        }
        else
        {
            heroAircraft.decreaseHp(num);
            assertEquals(0,heroAircraft.getHp());
        }
    }

    @DisplayName("Test shoot method")
    @Test
    void shoot() {
        list = heroAircraft.shoot();
        for(AbstractBaseBullet it : list)
        {
            assertEquals(it.getLocationX(),heroAircraft.getLocationX());
            assertEquals(it.getLocationY(),(heroAircraft.getLocationY()+heroAircraft.getDirection()*2));
        }
    }
}