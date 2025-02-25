package Classes;

import DatabaseConnection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public static User login(String username, String password) {
        User user = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password); // We will hash this later for security

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (Exception e) {
            System.out.println("❌ Login Error: " + e.getMessage());
        }
        return user;
    }
    
    public static int getTotalCustomers() {
        int totalCustomers = 0;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM Users WHERE role = 'customer'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalCustomers = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("❌ Error fetching total customers: " + e.getMessage());
        }
        return totalCustomers;
    }


}
