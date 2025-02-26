package servlets;

import dao.AdminDAO;
import models.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/adminSignup")
public class AdminSignupServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Admin admin = new Admin(0, name, username, password); // ID is auto-generated
        AdminDAO adminDAO = new AdminDAO();

        try {
            boolean success = adminDAO.addAdmin(admin);
            if (success) {
                response.sendRedirect("manageAdmins.jsp?success=Admin added successfully");
            } else {
                request.setAttribute("error", "Failed to add admin!");
                request.getRequestDispatcher("manageAdmins.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
