package edu.hitsz.dao;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UserDaoImpl implements UserDao{

    private List<User> users;
    public User temp;
    /**
     * 游戏难度
     */
    public String degree;

    public void setDegree(String degree){
        this.degree=degree;
    }

    /**
     * 读取数据
     */
    public void readFile(){

        users = new LinkedList<User>();
        BufferedReader reader;
        try {
            FileInputStream fileInputStream = new FileInputStream("RankList/"+degree+"RankList.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                String[] tempStringSpace = tempString.split("\\$");
                User user = new User(tempStringSpace[0],Integer.parseInt(tempStringSpace[1]),tempStringSpace[2],tempStringSpace[3]);
                users.add(user);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public List<User> getAllUsers(){
        return this.users;
    }


    @Override
    public void doAdd(User user) {
        for (User user1 : users) {
            //同一用户名
            if (user1.getUserName().equals(user.getUserName()) && user1.getDegree().equals(user.getDegree())) {
                //更新最高分
                if(user1.getScore() < user.getScore()){
                    //去除原分数
                    users.remove(user1);
                }
                //如果低于最高分则不更新
                else{
                    return;
                }
                break;
            }
        }
        //若列表中无任何游戏记录则直接添加
        if(users.size()==0){
            users.add(user);
        }
        else {
            for (int i = 0; i < users.size(); i++) {
                User user1 = users.get(i);
                //插入在第一个比当前分数低的游戏记录前面
                if (user1.getScore() < user.getScore()) {
                    users.add(i, user);
                    break;
                }
                //如果比所有游戏记录都低分则在最后面添加
                if(i+1==users.size()){
                    users.add(user);
                    break;
                }
            }
        }
        writeFile(this.users);
    }

    @Override
    public void doDelete(String userName){
        for(User user : users){
            if(user.getUserName() == userName){
                users.remove(user);
                System.out.println("Delete User: Name (" + userName +")");
                //重新写入更新
                writeFile(this.users);
                return;
            }
        }
    }

    /**
     * 写入文件
     * @param users
     */
    public void writeFile(List<User> users){
        // 写文件的地方
        File file = new File("RankList/"+degree+"RankList.txt");
        // 文件字节流写入的类
        FileOutputStream fos = null;
        try {
            // 使用字节流向目的地址写入文件
            // 如果加第二个参数true，表示每次在原来的文件上追加东西
            fos = new FileOutputStream(file, false);

            for(User user : users){
                String temp = user.getUserName()+"$"+Integer.toString(user.getScore())+"$"+user.getTime()+"$"+user.getDegree()+"\n";
                fos.write(temp.getBytes(StandardCharsets.UTF_8));
            }
            //一般要把存入的数据刷新缓冲进去
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
