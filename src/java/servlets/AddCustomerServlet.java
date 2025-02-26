package servlets;

import dao.CustomerDAO;
import models.Customer;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddCustomerServlet")
public class AddCustomerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = new Customer(
            0, request.getParameter("name"), request.getParameter("email"),
            request.getParameter("username"), request.getParameter("password"),
            request.getParameter("address"), request.getParameter("nic"), request.getParameter("phone")
        );

        try {
            new CustomerDAO().addCustomer(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("manage-customers.jsp");
    }
}
