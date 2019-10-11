package com.bachelor.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bachelor.myUtils.DatabaseConnector;

@WebServlet("/RefreshRatingServlet")
public class RefreshRatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RefreshRatingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		try {
    		PreparedStatement pstm;
    		ResultSet rs;
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	Statement stmt = conn.createStatement();
        	
        	int refreshed_id = Integer.parseInt(request.getParameter("rating-to-refresh"));
        	int refreshed_rating = Integer.parseInt(request.getParameter("rating-value"));
        	
        	stmt.executeUpdate("UPDATE user_feature_values SET rating = '" + refreshed_rating + "' WHERE id = '" + refreshed_id + "';");
        	response.sendRedirect(getServletContext().getContextPath()+"/user_profile");	
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
