package dao;

import models.Admin;
import DatabaseConnection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    // Authenticate Admin
    public static Admin authenticateAdmin(String username, String password) {
        String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Admin(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } catch (Exception e) {
        }
        return null; // Admin not found
    }

    // Get All Admins
    public static List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM admins";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                admins.add(new Admin(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("password")
                ));
            }
        } catch (Exception e) {
        }
        return admins;
    }

    // Add New Admin
    public static boolean addAdmin(Admin admin) {
        String query = "INSERT INTO admins (name, username, password) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, admin.getName());
            ps.setString(2, admin.getUsername());
            ps.setString(3, admin.getPassword());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
        }
        return false;
    }

    // Delete Admin
    public static boolean deleteAdmin(int id) {
        String query = "DELETE FROM admins WHERE id=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
        }
        return false;
    }
}
