package edu.hitsz.prop;

import edu.hitsz.application.MusicThread;
import edu.hitsz.basic.Bomb;

import java.util.LinkedList;
import java.util.List;

public class BombProp extends AbstractBaseProp {

    /**
     * 订阅者列表
     */
    List<Bomb> bombList;

    public BombProp(int locationX,int locationY,int speedY){
        super(locationX, locationY, speedY);
        bombList = new LinkedList<>();
    }

    @Override
    public void effectProp(boolean isStop){
        new MusicThread("src/videos/get_supply.wav",isStop,false).start();
        new MusicThread("src/videos/bomb_explosion.wav",isStop,false).start();
        System.out.println("BombSupply active!");
        notifyAllSubscriber();
        return;
    }

    /**
     * 添加订阅者
     * @param bomb 要添加的订阅者
     */
    public void addEnemyFlyingObject(Bomb bomb){
        bombList.add(bomb);
    }

    /**
     * 去除订阅者
     * @param bomb 要去除的订阅者
     */
    public void removeEnemyFlyingObject(Bomb bomb){
        bombList.remove(bomb);
    }

    /**
     * 通知所有订阅者
     */
    public void notifyAllSubscriber(){
        for(Bomb bomb : bombList){
            bomb.update();
        }
    }
}
