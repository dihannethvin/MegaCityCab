package servlets;

import DatabaseConnection.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/servlets.admin.AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM admins WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Create session for admin
                HttpSession session = request.getSession();
                session.setAttribute("admin", username); 
                session.setMaxInactiveInterval(30 * 60); // 30-minute session timeout

                // Redirect instead of forward to prevent back button issue
                response.sendRedirect("admin/admin-dashboard.jsp");
            } else {
                request.setAttribute("error", "Invalid username or password.");
                request.getRequestDispatcher("admin-login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("admin-login.jsp?error=Database error.");
        }
    }
}
