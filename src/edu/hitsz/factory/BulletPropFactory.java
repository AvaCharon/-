package edu.hitsz.factory;

import edu.hitsz.prop.AbstractBaseProp;
import edu.hitsz.prop.BulletProp;

public class BulletPropFactory implements PropFactory{
    @Override
    public AbstractBaseProp createProp(int locationX , int locationY, int speedY){
        return new BulletProp(locationX,locationY,speedY);
    }
}
