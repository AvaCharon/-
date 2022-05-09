package edu.hitsz.prop;

import edu.hitsz.strategy.Collineation;
import edu.hitsz.strategy.Scattering;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicThread;

public class BulletProp extends AbstractBaseProp {

    public BulletProp(int locationX,int locationY,int speedY){
        super(locationX, locationY, speedY);
    }


    @Override
    public void effectProp(boolean isStop){
        new MusicThread("src/videos/get_supply.wav",isStop,false).start();
        System.out.println("FireSupply active!");

        Runnable runnableBulletEffect = () -> {
            try {
                HeroAircraft.getHeroAircraft().setShootNum(3);
                HeroAircraft.getHeroAircraft().setShootMode(new Scattering());
                Thread.sleep(5000);//持续5秒
                HeroAircraft.getHeroAircraft().setShootNum(1);
                HeroAircraft.getHeroAircraft().setShootMode(new Collineation());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        };

        new Thread(runnableBulletEffect).start();

        return;
    }

}
