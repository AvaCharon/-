package edu.hitsz.prop;

import edu.hitsz.application.MusicThread;
import edu.hitsz.basic.AbstractFlyingObject;

public abstract class AbstractBaseProp extends AbstractFlyingObject {

    public AbstractBaseProp(int locationX, int locationY ,int speedY){
        this.locationX=locationX;
        this.locationY=locationY;
        this.speedY = speedY;
    }

    public void effectProp(boolean isStop){
        new MusicThread("src/videos/get_supply.wav",isStop,false).start();
    }
}
