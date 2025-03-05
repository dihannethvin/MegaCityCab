package servlets.admin;

import dao.CustomerDAO;
import models.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin/manage-customers")
public class ManageCustomersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CustomerDAO customerDAO = new CustomerDAO();
    private static final Logger LOGGER = Logger.getLogger(ManageCustomersServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Customer> customerList = customerDAO.getAllCustomers();
            request.setAttribute("customerList", customerList);
            request.getRequestDispatcher("manage-customers.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching customer list", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error while retrieving customers.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.getWriter().write("Invalid action.");
            return;
        }

        try {
            switch (action) {
                case "add" -> addCustomer(request, response);
                case "update" -> updateCustomer(request, response);
                case "delete" -> deleteCustomer(request, response);
                default -> response.getWriter().write("Invalid action.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error in processing request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error while processing request.");
        }
    }

    // Add Customer
    private void addCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String nic = request.getParameter("nic");
        String phone = request.getParameter("phone");

        Customer customer = new Customer(0, name, email, username, password, address, nic, phone);

        try {
            boolean isAdded = customerDAO.register(customer);
            if (isAdded) {
                response.sendRedirect("manage-customers.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding customer.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding customer", e);
            throw e;
        }
    }

    // Update Customer
    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String address = request.getParameter("address");
        String nic = request.getParameter("nic");
        String phone = request.getParameter("phone");

        // Password is optional, update only if provided
        String password = request.getParameter("password");
        if (password == null || password.trim().isEmpty()) {
            password = null;  // Avoid updating password if not provided
        }

        Customer customer = new Customer(id, name, email, username, password, address, nic, phone);

        try {
            boolean isUpdated = customerDAO.updateCustomer(customer);
            if (isUpdated) {
                response.sendRedirect("manage-customers.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating customer.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating customer", e);
            throw e;
        }
    }

    // Delete Customer
    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            if (customerDAO.deleteCustomer(id)) {
                response.sendRedirect("manage-customers.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting customer.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting customer", e);
            throw e;
        }
    }
}
