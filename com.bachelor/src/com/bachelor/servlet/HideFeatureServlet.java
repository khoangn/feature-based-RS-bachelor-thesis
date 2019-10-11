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

@WebServlet("/HideFeatureServlet")
public class HideFeatureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HideFeatureServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement pstm;
        try {
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	String feature_to_hide = request.getParameter("feature-to-hide");
        	int user_id = Integer.parseInt(request.getParameter("user-id-feature-to-hide"));
        	System.out.println(feature_to_hide);
        	pstm = conn.prepareStatement("DELETE FROM user_features WHERE active_user_id = ? AND feature_id = (SELECT id FROM features WHERE field_name = ?)");
        	pstm.setInt(1, user_id);
        	pstm.setString(2, feature_to_hide);
        	pstm.executeUpdate();
        	response.sendRedirect(getServletContext().getContextPath()+"/home");
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
		doGet(request, response);
	}

}
