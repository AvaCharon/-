package edu.hitsz;

import edu.hitsz.application.BaseGame;
import edu.hitsz.application.Main;
import edu.hitsz.game.EasyBaseGame;
import edu.hitsz.game.NormalBaseGame;
import edu.hitsz.game.HardBaseGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AircraftWarStartFrame {
    private JFrame frame;
    private JPanel startPanel;
    private JButton easyButton;
    private JButton hardButton;
    private JButton normalButton;
    private JComboBox musicComboBox;
    private JLabel musicLabel;
    private static Object locker = Main.locker;
    private String gameDegree;
    private boolean isStop = false;

    public AircraftWarStartFrame() {

        frame = new JFrame("AircraftWar");
        frame.setSize(400,600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(this.startPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (locker){
                    try{
                        gameDegree = "Easy";
                        locker.notify();

                        frame.dispose();
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            }
        });
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (locker){
                    try{
                        gameDegree = "Normal";
                        locker.notify();

                        frame.dispose();
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (locker){
                    try{
                        gameDegree = "Hard";
                        locker.notify();

                        frame.dispose();
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            }
        });
        musicComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("关".equals(musicComboBox.getSelectedItem())){
                    Main.bgmMusicThread.setStop(true);
                    isStop = true;
                }
                else{
                    Main.bgmMusicThread.setStop(false);
                    isStop = false;
                }
            }
        });
    }

    /**
     * 选择游戏难度
     * @return 游戏实例
     */
    public BaseGame startGame(){
        BaseGame baseGame;
        switch (gameDegree){
            case "Easy":
                baseGame =new EasyBaseGame();
                break;
            case "Normal":
                baseGame =new NormalBaseGame();
                break;
            case "Hard":
                baseGame =new HardBaseGame();
                break;
            default:
                baseGame =new EasyBaseGame();
        }
        baseGame.isStop=isStop;
        return baseGame;
    }

}
