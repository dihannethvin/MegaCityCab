package dao;

import DatabaseConnection.DatabaseConnection;
import models.Admin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    // Authenticate Admin Login
    public Admin login(String username, String password) throws SQLException {
        String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Admin(rs.getInt("id"), rs.getString("name"), rs.getString("username"), rs.getString("password"));
            }
        }
        return null;
    }

    // Add New Admin
    public boolean addAdmin(Admin admin) throws SQLException {
        String query = "INSERT INTO admins (name, username, password) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, admin.getName());
            stmt.setString(2, admin.getUsername());
            stmt.setString(3, admin.getPassword());
            return stmt.executeUpdate() > 0;
        }
    }

    // Fetch All Admins
    public List<Admin> getAllAdmins() throws SQLException {
        List<Admin> adminList = new ArrayList<>();
        String query = "SELECT * FROM admins";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                adminList.add(new Admin(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }
        }
        return adminList;
    }

    // Update Admin
    public boolean updateAdmin(Admin admin) throws SQLException {
        String query = "UPDATE admins SET name = ?, username = ?, password = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, admin.getName());
            stmt.setString(2, admin.getUsername());
            stmt.setString(3, admin.getPassword());
            stmt.setInt(4, admin.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Delete Admin
    public boolean deleteAdmin(int id) throws SQLException {
        String query = "DELETE FROM admins WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
