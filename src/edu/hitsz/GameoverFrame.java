package edu.hitsz;

import edu.hitsz.application.Main;
import edu.hitsz.dao.UserDaoImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameoverFrame {
    private JFrame frame;
    private JPanel gameoverPanel;
    private JPanel topPanel;
    private JTextField playerNameTextField;
    private JLabel playerNameLabel;
    private JLabel topLabel;
    private JButton checkButton;
    private static Object locker = Main.locker;
    public static UserDaoImpl userDao = Main.userDao;

    public GameoverFrame() {

        frame = new JFrame("GameoverFrame");
        frame.setLocationRelativeTo(null);
        frame.setContentPane(this.gameoverPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (locker){
                    if(!"".equals(playerNameTextField.getText())){
                        userDao.temp.setUserName(playerNameTextField.getText());
                    }
                    userDao.doAdd(userDao.temp);
                    locker.notify();
                    frame.dispose();
                }
            }
        });
    }
}
