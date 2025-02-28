package servlets;

import dao.CustomerDAO;
import models.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/CustomerSignup") // Ensure the form action matches this
public class CustomerSignupServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String nic = request.getParameter("nic");
        String phone = request.getParameter("phone");

        Customer customer = new Customer(name, email, username, password, address, nic, phone);
        CustomerDAO CustomerDAO = new CustomerDAO();

        try {
            boolean success = CustomerDAO.register(customer);
            System.out.println("Customer registration success: " + success);
            if (success) {
                response.sendRedirect("customer-login.jsp?success=Account created successfully");
            } else {
                System.out.println("Redirecting to signup page due to failure.");
                request.setAttribute("error", "Signup failed! Try again.");
                request.getRequestDispatcher("customer-signup.jsp").forward(request, response);
            }
            } catch (SQLException e) { 
                e.printStackTrace(); // Print the full error in logs
                System.out.println("Exception occurred: " + e.getMessage()); // Print error in console
                request.setAttribute("error", "Database error: " + e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
    }
}

