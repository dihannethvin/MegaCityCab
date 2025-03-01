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

@WebServlet("/admin/manage-admins")
public class ManageAdminsServlet extends HttpServlet {

    private final AdminDAO adminDAO = new AdminDAO();

    private static final long serialVersionUID = 1L;

    @Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Admin> adminList = AdminDAO.getAllAdmins();
        request.setAttribute("adminList", adminList);
        
        request.getRequestDispatcher("manage-admins.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.getWriter().write("Invalid action.");
            return;
        }

        try {
            switch (action) {
                case "add" -> addAdmin(request, response);
                case "update" -> updateAdmin(request, response);
                case "delete" -> deleteAdmin(request, response);
                default -> response.getWriter().write("Invalid action.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error while processing request.");
        }
    }

    // Method to add an admin
    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Admin admin = new Admin(0, name, username, password);

        if (adminDAO.addAdmin(admin)) {
            response.sendRedirect("manage-admins");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding admin.");
        }
    }

    // Method to update an admin
    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Admin admin = new Admin(id, name, username, password);

        if (adminDAO.updateAdmin(admin)) {
            response.sendRedirect("manage-admins");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating admin.");
        }
    }

    // Method to delete an admin
    private void deleteAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));

        if (adminDAO.deleteAdmin(id)) {
            response.sendRedirect("manage-admins");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting admin.");
        }
    }
}
