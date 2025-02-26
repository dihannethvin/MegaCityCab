package servlets.admin;

import dao.AdminDAO;
import models.Admin;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/add-admin")
public class AddAdminServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Admin admin = new Admin(0, name, username, password);
        AdminDAO adminDAO = new AdminDAO();

        try {
            if (adminDAO.addAdmin(admin)) {
                response.sendRedirect("manage-admins");
            } else {
                response.getWriter().write("Error adding admin.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
