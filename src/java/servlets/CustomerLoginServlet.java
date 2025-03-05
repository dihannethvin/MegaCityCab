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
import jakarta.servlet.http.HttpSession;

@WebServlet("/customerLogin")
public class CustomerLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Customer customer = customerDAO.login(username, password);

            if (customer != null) {
                HttpSession session = request.getSession();
                session.setAttribute("customer", customer);
                session.setMaxInactiveInterval(30 * 60); // 30 minutes session timeout
                
                response.sendRedirect("customer-dashboard.jsp");
            } else {
                request.setAttribute("errorMessage", "Invalid username or password!");
                request.getRequestDispatcher("customer-login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            request.getRequestDispatcher("customer-login.jsp").forward(request, response);
        }
    }
}
