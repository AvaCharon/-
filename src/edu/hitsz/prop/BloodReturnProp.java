package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicThread;

public class BloodReturnProp extends AbstractBaseProp {

    /**
     * 生命值回复量
     */
    private int hpReturn = 20;

    public BloodReturnProp(int locationX,int locationY,int speedY){
        super(locationX, locationY, speedY);
    }

    public int getHpReturn(){
        return hpReturn;
    }

    @Override
    public void effectProp(boolean isStop){
        new MusicThread("src/videos/get_supply.wav",isStop,false).start();
        System.out.println("HP RECOVER!");
        HeroAircraft.getHeroAircraft().decreaseHp(-this.getHpReturn());
        return;
    }
}
