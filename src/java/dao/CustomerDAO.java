package dao;

import models.Customer;
import DatabaseConnection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // Authenticate Customer
    public static Customer authenticateCustomer(String username, String password) {
        String query = "SELECT * FROM customers WHERE username = ? AND password = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("address"),
                    rs.getString("nic"),
                    rs.getString("phone")
                );
            }
        } catch (Exception e) {
        }
        return null; // Customer not found
    }

    // Get All Customers
    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                customers.add(new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("address"),
                    rs.getString("nic"),
                    rs.getString("phone")
                ));
            }
        } catch (Exception e) {
        }
        return customers;
    }

    // Add New Customer
    public static boolean addCustomer(Customer customer) {
        String query = "INSERT INTO customers (name, email, username, password, address, nic, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getUsername());
            ps.setString(4, customer.getPassword());
            ps.setString(5, customer.getAddress());
            ps.setString(6, customer.getNic());
            ps.setString(7, customer.getPhone());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
        }
        return false;
    }

    // Delete Customer
    public static boolean deleteCustomer(int id) {
        String query = "DELETE FROM customers WHERE id=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
        }
        return false;
    }
}
