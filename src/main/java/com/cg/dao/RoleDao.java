package com.cg.dao;

import com.cg.model.Role;
import com.cg.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDao implements IRoleDao{

    private static final String INSERT_ROLE_SQL = "INSERT INTO role (name) VALUES (?);";
    private static final String SELECT_ROLE_BY_ID = "select * from role where id = ?;";
    private static final String SELECT_ALL_ROLE = "select * from role";
    private static final String DELETE_ROLE_SQL = "delete from role where id = ?;";
    private static final String UPDATE_ROLE_SQL = "update role set name = ? where id = ?;";
    private String jdbcURL = "jdbc:mysql://localhost:3306/user_manager?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "CHUcu123456";

    public RoleDao() {
    }
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void insertRole(Role role) throws SQLException {
        System.out.println(INSERT_ROLE_SQL);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROLE_SQL)) {
            preparedStatement.setString(1, role.getName());
            System.out.println(this.getClass() + " insertRole " + preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Role selectRole(int id) {
        Role country = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLE_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(this.getClass() + " selectRole: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                country = new Role(id, name);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return country;
    }

    @Override
    public List<Role> selectAllRole() {
        List<Role> listRole = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ROLE);) {
            System.out.println(this.getClass() + " selectAllRole: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                listRole.add(new Role(id, name));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return listRole;
    }

    @Override
    public boolean deleteRole(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_ROLE_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateRole(Role role) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ROLE_SQL);) {
            statement.setString(1, role.getName());
            statement.setInt(2, role.getId());
            System.out.println(this.getClass() + " updateRole: " + statement);
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
