package servlets;

import dao.AdminDAO;
import models.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        AdminDAO adminDAO = new AdminDAO();
        try {
            Admin admin = adminDAO.login(username, password);
            if (admin != null) {
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);
                response.sendRedirect("./admin/admin-dashboard.jsp"); // Redirect to admin dashboard
            } else {
                request.setAttribute("error", "Invalid Username or Password!");
                request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            response.sendRedirect("error.jsp");
        }
    }
}
