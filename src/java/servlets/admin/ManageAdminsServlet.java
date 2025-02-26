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
import java.util.List;

@WebServlet("/manage-admins")
public class ManageAdminsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminDAO adminDAO = new AdminDAO();
        try {
            List<Admin> adminList = adminDAO.getAllAdmins();
            request.setAttribute("adminList", adminList);
            request.getRequestDispatcher("admin/manage-admins.jsp").forward(request, response);
        } catch (SQLException e) {
        }
    }
}
