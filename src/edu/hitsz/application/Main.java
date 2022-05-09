package edu.hitsz.application;

import edu.hitsz.AircraftWarStartFrame;
import edu.hitsz.GameoverFrame;
import edu.hitsz.RankListFrame;
import edu.hitsz.WelcomeFrame;
import edu.hitsz.dao.UserDaoImpl;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static UserDaoImpl userDao = new UserDaoImpl();
    public static Object locker = new Object();
    //退出程序标志
    public static boolean isExit = true;
    /**
     *背景音乐
     */
    public static MusicThread bgmMusicThread = new MusicThread("src/videos/bgm.wav",false,true);

    public static void main(String[] args) {
        synchronized (locker){
            try {
                bgmMusicThread.start();
                //启动欢迎界面
                WelcomeFrame frameWelcome = new WelcomeFrame();
                locker.wait();
                do{
                    bgmMusicThread.setToEnd(false);
                    bgmMusicThread.setStop(false);
                    //启动选择界面
                    AircraftWarStartFrame frameSelect = new AircraftWarStartFrame();
                    locker.wait();

                    // 获得屏幕的分辨率，初始化 Frame
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    JFrame frame = new JFrame("Aircraft War");
                    frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                    frame.setResizable(false);
                    //设置窗口的大小和位置,居中放置
                    frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                            WINDOW_WIDTH, WINDOW_HEIGHT);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    //获取游戏实例
                    //停止播放背景音乐
                    //开始游戏
                    bgmMusicThread.setToEnd(true);
                    BaseGame baseGame = frameSelect.startGame();
                    frame.add(baseGame);
                    frame.setVisible(true);
                    baseGame.action();
                    locker.wait();

                    //启动游戏结束界面
                    frame.dispose();
                    GameoverFrame frameOver = new GameoverFrame();
                    locker.wait();

                    //启动排行榜界面
                    RankListFrame frameRank = new RankListFrame();
                    locker.wait();

                    //是否退出程序
                    isExit = frameRank.isExit();
                }while (isExit==false);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        bgmMusicThread.setRunning(false);
        System.out.println("退出游戏");
        return;
    }
}
