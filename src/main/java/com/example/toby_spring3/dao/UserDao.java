package com.example.toby_spring3.dao;

import com.example.toby_spring3.domain.User;

import java.sql.*;
import java.util.Map;

import static java.lang.System.getenv;


public class UserDao {

    ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
        pstmt.setString(1,user.getId());
        pstmt.setString(2,user.getName());
        pstmt.setString(3,user.getPassword());
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }
    public User get(String id) throws ClassNotFoundException, SQLException {

        Connection conn = connectionMaker.makeConnection();
        PreparedStatement ps = conn.prepareStatement("select id,name,password from users where id = ?;");
        ps.setString(1,id);
        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        conn.close();

        return user;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConnectionMaker cm = new DConnectionMaker();
        UserDao userDao = new UserDao(cm);
        User user = new User();
        user.setId("6");
        user.setName("mimi");
        user.setPassword("0000");
        userDao.add(user);

        User selectedUser = userDao.get("6");
        System.out.println(selectedUser.getId());
        System.out.println(selectedUser.getName());
        System.out.println(selectedUser.getPassword());
    }
}
