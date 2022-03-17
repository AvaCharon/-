package edu.hitsz.prop;

public class BloodReturnProp extends BaseProp {

    /**
     * 生命值回复量
     */
    private int hpReturn = 20;

    public BloodReturnProp(int locationX,int locationY){
        super(locationX, locationY);
    }

    public int getHpReturn(){
        return hpReturn;
    }

    @Override
    public void SendNote(){
        System.out.println("HP RECOVER!");
        return;
    }
}
