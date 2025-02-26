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

@WebServlet("/customerSignup")
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

        Customer customer = new Customer(0, name, email, username, password, address, nic, phone);
        CustomerDAO customerDAO = new CustomerDAO();

        try {
            boolean success = customerDAO.addCustomer(customer);
            if (success) {
                response.sendRedirect("customerLogin.jsp?success=Account created successfully");
            } else {
                request.setAttribute("error", "Signup failed!");
                request.getRequestDispatcher("signup.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            response.sendRedirect("error.jsp");
        }
    }
}
