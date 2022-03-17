package edu.hitsz.prop;

public class BombProp extends BaseProp{

    public BombProp(int locationX,int locationY){
        super(locationX, locationY);
    }

    @Override
    public void SendNote(){
        System.out.println("BombSupply active!");
        return;
    }
}
