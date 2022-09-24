package com.cg.dao;

import com.cg.model.Role;

import java.sql.SQLException;
import java.util.List;

public interface IRoleDao {
    public void insertRole(Role role) throws SQLException;
    public Role selectRole(int id);
    public List<Role> selectAllRole();
    public boolean deleteRole(int id) throws SQLException;
    public boolean updateRole(Role role) throws SQLException;
}
