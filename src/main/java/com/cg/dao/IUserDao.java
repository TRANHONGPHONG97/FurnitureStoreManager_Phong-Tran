package com.cg.dao;

import com.cg.model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDao {
    public boolean insertUser(User user) throws SQLException;
    public User selectUser(int id);
    public List<User> selectAllUsers();
    public boolean updateUser(User user) throws SQLException;
boolean deleteUser (int id) throws SQLException;

    boolean existByPassWord1(String password);

    boolean existsByUser(String userName);
}
