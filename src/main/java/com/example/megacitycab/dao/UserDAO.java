package com.example.megacitycab.dao;

import com.example.megacitycab.db.DBConnection;
import com.example.megacitycab.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static User validateUser(String username, String password) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return null;

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
