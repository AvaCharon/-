package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.basic.Bomb;
import edu.hitsz.bullet.AbstractBaseBullet;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractEnemyAircraft extends AbstractAircraft implements Bomb {
        protected int maxHp;
        protected int hp;
        protected int power;

        private int direction = 1;

        public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int power) {
            super(locationX, locationY, speedX, speedY, hp, power);
            this.direction = 1;
        }

        @Override
        public void forward() {
            super.forward();
            // 判定 y 轴向下飞行出界
            if (locationY >= Main.WINDOW_HEIGHT ) {
                vanish();
            }
        }

        @Override
        public List<AbstractBaseBullet> shoot() {
            return new LinkedList<>();
        }

        @Override
        public int getDirection() {
            return direction;
        }

        @Override
        public void update(){}

        public void strengthen(double rate){
            this.maxHp*=rate;
            this.hp*=rate;
            this.power*=rate;
        }
}
