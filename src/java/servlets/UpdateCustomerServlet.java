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

@WebServlet("/UpdateCustomerServlet")
public class UpdateCustomerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = new Customer(
            Integer.parseInt(request.getParameter("id")), request.getParameter("name"), request.getParameter("email"),
            request.getParameter("username"), request.getParameter("password"),
            request.getParameter("address"), request.getParameter("nic"), request.getParameter("phone")
        );

        try {
            new CustomerDAO().updateCustomer(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("manage-customers.jsp");
    }
}
