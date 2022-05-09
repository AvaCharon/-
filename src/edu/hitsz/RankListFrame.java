package edu.hitsz;

import edu.hitsz.application.Main;
import edu.hitsz.dao.User;
import edu.hitsz.dao.UserDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class RankListFrame{
    private JTable scoreTable;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JScrollPane tableScrollPane;
    private JPanel rankPanel;
    private JLabel degreeLabel;
    private JButton deleteButton;
    private JLabel rankLabel;
    private JButton restartButton;
    private JButton exitButton;
    public UserDaoImpl userDao = Main.userDao;
    private static Object locker = Main.locker;
    //退出程序标志
    private boolean isExit = true;

    public RankListFrame(){

        setTable();

        JFrame frame = new JFrame("RankList");
        frame.setSize(400,600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(this.rankPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (locker){
                    {
                        //获取鼠标选取行
                        if (scoreTable.getSelectedRow() != -1) {
                            int result = JOptionPane.showConfirmDialog(
                                    frame,
                                    "确认删除？",
                                    "提示",
                                    JOptionPane.YES_NO_CANCEL_OPTION
                            );
                            if(result == 0){
                                userDao.doDelete(scoreTable.getValueAt(scoreTable.getSelectedRow(), 1).toString());

                                //重新设置表格达到刷新目的
                                setTable();
                            }
                        }
                    }
                }
            }
        });
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (locker){
                    {
                        try {
                            isExit = false;
                            locker.notify();
                            frame.dispose();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (locker){
                    {
                        try {
                            isExit = true;
                            locker.notify();
                            frame.dispose();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * 设置表格
     */
    public void setTable(){
        //显示难度
        degreeLabel.setText("难度:"+userDao.degree);

        DefaultTableModel model = new DefaultTableModel();
        String[] columnName = {"排 名","名 称","分 数","达成时间"};
        model.setColumnIdentifiers(columnName);
        List<User> users = userDao.getAllUsers();
        int index = 1;
        for(User user : users){
            Vector row = new Vector(4);
            row.add(0,Integer.toString(index));
            row.add(1,user.getUserName());
            row.add(2,Integer.toString(user.getScore()));
            row.add(3,user.getTime());
            index++;
            model.addRow(row);
        }

        scoreTable.setModel(model);
        tableScrollPane.setViewportView(scoreTable);
    }

    /**
     * 是否退出
     */
    public boolean isExit(){
        return isExit;
    }
}
