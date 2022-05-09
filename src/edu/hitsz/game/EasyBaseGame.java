package edu.hitsz.game;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.application.BaseGame;

import java.util.List;

public class EasyBaseGame extends BaseGame {
    private String degree = "Easy";

    public EasyBaseGame(){
        super();
    }

    /**
     *调整敌机数量最大值
     */
    @Override
    public void setEnemyMaxNumber()
    {
        this.enemyMaxNumber = 4;
    }

    /**
     *调整Boss机数量最大值
     */
    @Override
    public void setBossMaxNumber(){
        this.bossMaxNumber=0;
    }

    /**
     *调整敌机产生周期与射击周期
     */
    @Override
    public void setCycleDuration()
    {
        this.cycleDuration = 600;
    }

    /**
     * 敌机随时间增强
     */
    @Override
    public void enemyPropertyBoost(List<AbstractEnemyAircraft> enemyAircrafts){

    }

    @Override
    public String getDegree(){
        return this.degree;
    }
}
