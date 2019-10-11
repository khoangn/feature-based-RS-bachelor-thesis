package com.bachelor.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.object.appUser;
import com.bachelor.object.userFeatureValues;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet("/user_profile")
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserProfileServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		try {
    		PreparedStatement pstm;
    		ResultSet rs;
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	Statement stmt = conn.createStatement();
       
            int active_user_id = (int) request.getSession().getAttribute("auid");
            
			// USER PROFILE / RATING HISTORY
			List<userFeatureValues> list_active_user_feature_value = new ArrayList<>();
			pstm = conn.prepareStatement("SELECT user_feature_values.*, features.* FROM user_feature_values, features "
					+ "WHERE active_user_id = ? AND user_feature_values.feature_id = features.id "
					+ "ORDER BY user_feature_values.id DESC");
			pstm.setInt(1, active_user_id);
			rs = pstm.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int userid = rs.getInt("active_user_id");
				String value = rs.getString("value");
				String name = rs.getString("name");
				int min = rs.getInt("min");
				int max = rs.getInt("max");
				int rating = rs.getInt("rating");
				userFeatureValues val = new userFeatureValues();
				val.setId(id);
				val.setUser_id(userid);
				val.setValue(value);
				val.setName(name);
				val.setMin(min);
				val.setMax(max);
				val.setRating(rating);
				list_active_user_feature_value.add(val);
			}
			
			request.setAttribute("userprofileSize", list_active_user_feature_value.size());
			request.setAttribute("userprofile", list_active_user_feature_value);
            
            
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
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
