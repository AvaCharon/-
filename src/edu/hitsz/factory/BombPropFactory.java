package edu.hitsz.factory;

import edu.hitsz.prop.AbstractBaseProp;
import edu.hitsz.prop.BombProp;

public class BombPropFactory implements PropFactory{

    @Override
    public AbstractBaseProp createProp(int locationX , int locationY ,int speedY){
        return new BombProp(locationX,locationY,speedY);
    }
}
