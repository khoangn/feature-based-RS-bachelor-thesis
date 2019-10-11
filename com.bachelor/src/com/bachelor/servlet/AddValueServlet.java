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

@WebServlet("/AddValueServlet")
public class AddValueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddValueServlet() {
        super();
    }

    appUser app_user = new appUser();
     
    public void addTextValue(HttpServletRequest request, HttpServletResponse response, int dbId, String feature, String string_value, String string_rating) throws SQLException {
		Connection conn = null;
		ResultSet rs;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DatabaseConnector.getConnection();
			Statement stmt = conn.createStatement();
			String selected_value = (String) request.getParameter(string_value);
			String string_rating_value = (String) request.getParameter(string_rating);
			rs = stmt.executeQuery("SELECT DISTINCT("+ feature + ") FROM item");
			List<String> list_distinct = new ArrayList<>();
			while(rs.next()) {
				String distinct_value = rs.getString(1);
				list_distinct.add(distinct_value);
			}
			boolean is_binary;
			if (list_distinct.contains("yes") || list_distinct.contains("no") || list_distinct.contains("optional") || list_distinct.contains("eingeschränkt")) {
				is_binary = true;
			} else {
				is_binary = false;
			}
			int rating = 0;
			if (string_rating_value != null) {
				rating = Integer.parseInt(string_rating_value);
			}
			
			if (is_binary == true) {
				if (selected_value != null && rating != 0) {
					PreparedStatement pstmt_value = conn.prepareStatement(
							"DELETE FROM recently_added WHERE user_feature_values_id=(SELECT id FROM user_feature_values WHERE feature_id = (SELECT id FROM features WHERE field_name='" + feature + "') AND active_user_id='" + dbId + "');"
							+ "DELETE FROM user_feature_values WHERE feature_id=(SELECT id FROM features WHERE field_name='" + feature + "') AND active_user_id='" + dbId + "';"
							+ "INSERT INTO user_feature_values (active_user_id, feature_id, value, rating) VALUES ((SELECT id FROM active_user WHERE id = ?), (SELECT id FROM features WHERE field_name='"
							+ feature + "'), ?, ?);"
							+ "INSERT INTO recently_added (user_feature_values_id, active_user_id) "
							+ "VALUES ((SELECT id FROM user_feature_values WHERE feature_id = (SELECT id FROM features WHERE field_name='" + feature + "') AND value = ? AND rating = ? AND active_user_id = ?), ?)");
					pstmt_value.setInt(1, dbId);
					pstmt_value.setString(2, selected_value);
					pstmt_value.setInt(3, rating);
					pstmt_value.setString(4, selected_value);
					pstmt_value.setInt(5, rating);
					pstmt_value.setInt(6, dbId);
					pstmt_value.setInt(7, dbId);
					pstmt_value.executeUpdate();
				}	
			} else if (is_binary == false) {				
				if (selected_value != null && rating != 0) {
					PreparedStatement pstmt_value = conn.prepareStatement(
							"INSERT INTO user_feature_values (active_user_id, feature_id, value, rating) VALUES ((SELECT id FROM active_user WHERE id = ?), (SELECT id FROM features WHERE field_name='"
							+ feature + "'), ?, ?);"
							+ "INSERT INTO recently_added (user_feature_values_id, active_user_id) "
							+ "VALUES ((SELECT id FROM user_feature_values WHERE feature_id = (SELECT id FROM features WHERE field_name='" + feature + "') AND value = ? AND rating = ? AND active_user_id = ?), ?)");
					pstmt_value.setInt(1, dbId);
					pstmt_value.setString(2, selected_value);
					pstmt_value.setInt(3, rating);
					pstmt_value.setString(4, selected_value);
					pstmt_value.setInt(5, rating);
					pstmt_value.setInt(6, dbId);
					pstmt_value.setInt(7, dbId);
					pstmt_value.executeUpdate();
			}
			}
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
    
    public void addNumberValue(HttpServletRequest request, HttpServletResponse response, int dbId, String feature, String string_min, String string_max, String string_rating) throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DatabaseConnector.getConnection();
			String selected_min = (String) request.getParameter(string_min);
			String selected_max = (String) request.getParameter(string_max);
			String string_rating_minMax = (String) request.getParameter(string_rating);
			int rating = 0;
			int min = 0;
			int max = 0;
			if (string_rating_minMax != null && selected_min != null && selected_max != null) {
				try {
					min = Integer.parseInt(selected_min);
					max = Integer.parseInt(selected_max);
					rating = Integer.parseInt(string_rating_minMax);
				}catch(NumberFormatException e){}
			}
			if (min >= 0 && rating != 0) {
				PreparedStatement pstmt_value = conn.prepareStatement(
						"DELETE FROM recently_added WHERE user_feature_values_id=(SELECT id FROM user_feature_values WHERE feature_id = (SELECT id FROM features WHERE field_name='" + feature + "') AND active_user_id='" + dbId + "');"
						+ "DELETE FROM user_feature_values WHERE feature_id=(SELECT id FROM features WHERE field_name='" + feature + "') AND active_user_id='" + dbId + "';"
						+ "INSERT INTO user_feature_values (active_user_id, feature_id, min, max, rating) VALUES ((SELECT id FROM active_user WHERE id = ?), (SELECT id FROM features WHERE field_name='" + feature + "'), ?, ?, ?);" 
						+ "INSERT INTO recently_added (user_feature_values_id, active_user_id) "
						+ "VALUES ((SELECT id FROM user_feature_values WHERE feature_id = (SELECT id FROM features WHERE field_name='" + feature + "') AND min = ? AND max = ? AND rating = ? AND active_user_id = ?), ?)");
				pstmt_value.setInt(1, dbId);
				pstmt_value.setInt(2, min);
				pstmt_value.setInt(3, max);
				pstmt_value.setInt(4, rating);
				pstmt_value.setInt(5, min);
				pstmt_value.setInt(6, max);
				pstmt_value.setInt(7, rating);
				pstmt_value.setInt(8, dbId);
				pstmt_value.setInt(9, dbId);
				pstmt_value.executeUpdate();
			}
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
    
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
        try {
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
          
            appUser app_user = (appUser) request.getSession().getAttribute("activeAppUser");
            int dbId = app_user.getId();
//            System.out.println(request.getParameter("rating-value"));
 		   		
            String text_value = request.getParameter("selected-text-field-name");
            addTextValue(request, response, dbId, text_value, "selected-text-value", "rating-value");
            
            String number_value = request.getParameter("selected-number-field-name");
            addNumberValue(request, response, dbId, number_value, "min", "max", "rating-value");		
						
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
