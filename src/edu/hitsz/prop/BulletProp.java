package edu.hitsz.prop;

public class BulletProp extends BaseProp {

    public BulletProp(int locationX,int locationY){
        super(locationX, locationY);
    }


    @Override
    public void SendNote(){
        System.out.println("FireSupply active!");
        return;
    }
}
