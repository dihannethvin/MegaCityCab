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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Admin> adminList = adminDAO.getAllAdmins();
            if (adminList != null && !adminList.isEmpty()) {
                request.setAttribute("adminList", adminList);
                request.getRequestDispatcher("/admin/manage-admins.jsp").forward(request, response);
            } else {
                response.getWriter().write("No admins found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error while fetching admins.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (null == action) {
            response.getWriter().write("Invalid action.");
        } else switch (action) {
            case "add" -> addAdmin(request, response);
            case "update" -> updateAdmin(request, response);
            case "delete" -> deleteAdmin(request, response);
            default -> response.getWriter().write("Invalid action.");
        }
    }

    // Method to add an admin
    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Admin admin = new Admin(0, name, username, password);

        try {
            if (adminDAO.addAdmin(admin)) {
                response.sendRedirect("manage-admins");
            } else {
                response.getWriter().write("Error adding admin.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error while adding admin.");
        }
    }

    // Method to update an admin
    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Admin admin = new Admin(id, name, username, password);

        try {
            if (adminDAO.updateAdmin(admin)) {
                response.sendRedirect("manage-admins");
            } else {
                response.getWriter().write("Error updating admin.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error while updating admin.");
        }
    }

    // Method to delete an admin
    private void deleteAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            if (adminDAO.deleteAdmin(id)) {
                response.sendRedirect("manage-admins");
            } else {
                response.getWriter().write("Error deleting admin.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error while deleting admin.");
        }
    }
}
