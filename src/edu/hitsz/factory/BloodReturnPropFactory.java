package edu.hitsz.factory;

import edu.hitsz.prop.AbstractBaseProp;
import edu.hitsz.prop.BloodReturnProp;

public class BloodReturnPropFactory implements PropFactory{

    @Override
    public AbstractBaseProp createProp(int locationX , int locationY,int speedY){
        return new BloodReturnProp(locationX,locationY,speedY);
    }

}
