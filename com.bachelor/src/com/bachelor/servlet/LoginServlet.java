package com.bachelor.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.object.appUser;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		appUser app_user = new appUser();
		
        try {
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
    		String password = request.getParameter("password");
            String username = request.getParameter("username");
			if (username != null && password != null) {
				String dbPassword = null;
				String dbUsername = null;
				int dbId = 0;

				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM active_user WHERE password = ? AND username = ?");
				pstmt.setString(1, password);
				pstmt.setString(2, username);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					dbPassword = rs.getString("password");
					dbUsername = rs.getString("username");
					dbId = rs.getInt("id");
					app_user.setUsername(dbUsername);
					app_user.setId(dbId);
				}

				response.setContentType("text/html");
				PrintWriter out = response.getWriter();

				request.setAttribute("username", username);
				request.setAttribute("password", password);

				if (username.equals(dbUsername) && password.equals(dbPassword)) {
					System.out.println("CORRECT PASSWORD AND USERNAME.");
					request.getSession().setAttribute("activeAppUser", app_user);

					// FEATURE-BASED
					getServletContext().getRequestDispatcher("/AddValueServlet").forward(request, response);

					// ITEM-BASED
//            		getServletContext().getRequestDispatcher("/RateItemServlet").forward(request,response);
					
				} else {
					System.out.println("PLEASE CHECK YOUR PASSWORD AND USERNAME.");

					response.setContentType("text/html");
					PrintWriter pw = response.getWriter();
					pw.println("<script type=\"text/javascript\">");
					pw.println("alert('Invalid username or password');");
					pw.println("</script>");

					RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
					rd.include(request, response);
				}

			} else {
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
