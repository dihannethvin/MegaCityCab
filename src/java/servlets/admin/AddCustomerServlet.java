package servlets.admin;

import dao.CustomerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import models.Customer;

@WebServlet("/admin/AddCustomerServlet")
public class AddCustomerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String nic = request.getParameter("nic");
        String phone = request.getParameter("phone");

        // Create a new Customer object from the request parameters
        Customer customer = new Customer(0, name, email, username, password, address, nic, phone);

        // Initialize CustomerDAO to register the customer
        CustomerDAO customerDAO = new CustomerDAO();
        try {
            boolean success = customerDAO.register(customer);
            if (success) {
                response.sendRedirect("manage-customers.jsp"); // Redirect to the manage customers page
            } else {
                request.setAttribute("error", "Failed to add customer");
                request.getRequestDispatcher("add-customer.jsp").forward(request, response); // Forward back if error
            }
        } catch (SQLException e) {
            request.setAttribute("error", "An error occurred while adding the customer");
            request.getRequestDispatcher("add-customer.jsp").forward(request, response); // Forward back if exception occurs
        }
    }
}
