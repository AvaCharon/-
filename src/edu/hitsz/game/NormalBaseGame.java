package edu.hitsz.game;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.application.BaseGame;

import java.util.List;

public class NormalBaseGame extends BaseGame {
    private String degree = "Normal";

    public NormalBaseGame(){
        super();
    }

    /**
     *调整敌机数量最大值
     */
    @Override
    public void setEnemyMaxNumber()
    {
        this.enemyMaxNumber = 5;
    }

    /**
     *调整Boss机数量最大值
     */
    @Override
    public void setBossMaxNumber(){
        this.bossMaxNumber=1;
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
     *Boss机每次召唤增强
     */
    @Override
    public AbstractEnemyAircraft bossPropertyBoost(){
        return bossEnemyFactory.createEnemy(150);
    }

    /**
     * 敌机随时间增强
     */
    @Override
    public void enemyPropertyBoost(List<AbstractEnemyAircraft> enemyAircrafts){
        if(rate<10){
            rate += 0.02;
            if(eliteRate < 0.8){
                eliteRate += 0.01;
                if(cycleDuration > 300){
                    cycleDuration -= 5;
                    System.out.printf("提高难度！精英机概率：%.2f,射击与敌机产生周期缩短为: %d ms,敌机属性提升倍率: %.2f\n",eliteRate,cycleDuration,rate);
                }
                else{
                    System.out.printf("提高难度！精英机概率：%.2f,射击与敌机产生周期已达最小: %d ms,敌机属性提升倍率: %.2f\n",eliteRate,cycleDuration,rate);
                }
            }
            else {
                System.out.printf("提高难度！精英机概率已达最大：%.2f,射击与敌机产生周期已达最小: %d ms,敌机属性提升倍率: %.2f\n",eliteRate,cycleDuration,rate);
            }
        }
        else {
            System.out.printf("提高难度！精英机概率已达最大：%.2f,射击与敌机产生周期已达最小: %d ms,敌机属性提升倍率已达最大: %.2f\n",eliteRate,cycleDuration,rate);
        }
        for(AbstractEnemyAircraft enemyAircraft : enemyAircrafts){
            enemyAircraft.strengthen(rate);
        }
    }

    @Override
    public String getDegree(){
        return this.degree;
    }
}
