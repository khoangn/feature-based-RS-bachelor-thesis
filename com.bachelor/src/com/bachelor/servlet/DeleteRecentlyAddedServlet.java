package com.bachelor.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bachelor.myUtils.DatabaseConnector;

@WebServlet("/DeleteRecentlyAddedServlet")
public class DeleteRecentlyAddedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteRecentlyAddedServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DatabaseConnector.getConnection();
			int active_user = (int) request.getSession().getAttribute("auid");		
			PreparedStatement pstmt_delete_feature = conn.prepareStatement("DELETE FROM recently_added WHERE active_user_id = ?");
			pstmt_delete_feature.setInt(1, active_user);
			pstmt_delete_feature.executeUpdate();
			response.sendRedirect(getServletContext().getContextPath()+"/login");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
