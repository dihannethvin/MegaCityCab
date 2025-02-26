package dao;

import DatabaseConnection.DatabaseConnection;
import models.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // Authenticate Customer Login
    public Customer login(String username, String password) throws SQLException {
        String query = "SELECT * FROM customers WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

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
        }
        return null;
    }
    
    // Get All Customers
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                        rs.getString("username"), rs.getString("password"),
                        rs.getString("address"), rs.getString("nic"), rs.getString("phone")
                ));
            }
        }
        return customers;
    }

    // Add New Customer
    public boolean addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (name, email, username, password, address, nic, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getUsername());
            stmt.setString(4, customer.getPassword()); // TODO: Hash password before storing
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getNic());
            stmt.setString(7, customer.getPhone());
            return stmt.executeUpdate() > 0;
        }
    }

    // Update Customer
    public boolean updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE customers SET name = ?, email = ?, username = ?, password = ?, address = ?, nic = ?, phone = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getUsername());
            stmt.setString(4, customer.getPassword()); // TODO: Hash password before storing
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getNic());
            stmt.setString(7, customer.getPhone());
            stmt.setInt(8, customer.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Delete Customer
    public boolean deleteCustomer(int id) throws SQLException {
        String query = "DELETE FROM customers WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
