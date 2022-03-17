package edu.hitsz.prop;

import edu.hitsz.basic.AbstractFlyingObject;

public class BaseProp extends AbstractFlyingObject {

    private int speedX = 0;
    private int speedY = 0;

    public BaseProp(int locationX,int locationY){
        this.locationX=locationX;
        this.locationY=locationY;
    }

    public void SendNote(){
    }
}
