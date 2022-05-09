package edu.hitsz.dao;

import java.util.LinkedList;
import java.util.List;

public interface UserDao {
    /**
     * 获取所有User对象
     * @return 所有User对象
     */
    public List<User> getAllUsers();

    /**
     * 添加游戏记录信息
     * @param user 要加入的User
     */
    public void doAdd(User user);

    /**
     * 删除游戏记录
     * @param userName 要删除的记录的用户名
     */
    public void doDelete(String userName);
}
