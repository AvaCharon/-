package edu.hitsz;

import edu.hitsz.application.Main;

import javax.swing.*;

public class WelcomeFrame {
    private JPanel welcomePanel;
    private JLabel welcomeLabel;
    public static Object locker = Main.locker;

    public WelcomeFrame() {
        JFrame frame = new JFrame("WelcomeFrame");
        frame.setSize(400,600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(this.welcomePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Runnable runnableWelcomePage = () ->{
            synchronized (locker){
                try {
                    int cnt = 0;
                    while (cnt <= 5) {
                        if (welcomeLabel.getText() == null) {
                            welcomeLabel.setText("Welcome to AircraftWar");
                        }
                        else {
                            welcomeLabel.setText(null);
                        }
                        Thread.sleep(200);
                        cnt++;
                    }
                    locker.notify();
                    frame.dispose();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        new Thread(runnableWelcomePage).start();
    }
}
