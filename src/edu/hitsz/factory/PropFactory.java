package edu.hitsz.factory;

import edu.hitsz.prop.AbstractBaseProp;

public interface PropFactory {
    /**
     *生成道具
     * @return 道具实例
     * @param locationX 道具生成位置横坐标
     * @param locationY 道具生成位置纵坐标
     * @param speedY 道具纵向速度
     */
    public AbstractBaseProp createProp(int locationX , int locationY , int speedY);
}
