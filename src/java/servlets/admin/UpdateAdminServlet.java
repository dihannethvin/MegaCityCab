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

@WebServlet("/update-admin")
public class UpdateAdminServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Admin admin = new Admin(id, name, username, password);
        AdminDAO adminDAO = new AdminDAO();

        try {
            if (adminDAO.updateAdmin(admin)) {
                response.sendRedirect("manage-admins");
            } else {
                response.getWriter().write("Error updating admin.");
            }
        } catch (SQLException e) {
        }
    }
}
