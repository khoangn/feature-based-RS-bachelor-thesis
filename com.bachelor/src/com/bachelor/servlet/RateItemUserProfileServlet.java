package com.bachelor.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.object.appUser;

/**
 * Servlet implementation class RateItemUserProfileServlet
 */
@WebServlet("/RateItemUserProfileServlet")
public class RateItemUserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RateItemUserProfileServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement pstm;
        try {
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
          
            appUser app_user = (appUser) request.getSession().getAttribute("activeAppUser");
            int dbId = app_user.getId();
 		   	String item_id = request.getParameter("selected-item");
 		   	
 		   	if(item_id != null) {
				int item_rating = 0;
				if (request.getParameter("rating-value") != null) {
					item_rating = Integer.parseInt(request.getParameter("rating-value"));
				}
				System.out.println(dbId + " : " + item_id + " : " + item_rating);
				
				pstm = conn.prepareStatement("DELETE FROM user_item WHERE active_user_id = ? AND item_id = ?;"
						+ "INSERT INTO user_item (active_user_id, item_id, rating) VALUES (?, ?, ?);");
				pstm.setInt(1, dbId);
				pstm.setString(2, item_id);
				pstm.setInt(3, dbId);
				pstm.setString(4, item_id);
				pstm.setInt(5, item_rating);
				pstm.executeUpdate();
 		   	}
            response.sendRedirect(getServletContext().getContextPath()+"/user_profile_item_based");
            
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
