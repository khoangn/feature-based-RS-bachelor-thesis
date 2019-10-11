package com.bachelor.servlet;

import java.io.IOException;
import java.sql.Connection;
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
import com.bachelor.myUtils.Misc;
import com.bachelor.object.selectedFeatures;
import com.google.gson.Gson;

@WebServlet("/GetChartUserURatingServlet")
public class GetChartUserURatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetChartUserURatingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {
			String user_u_id = request.getParameter("user-u-id");
			int active_user = (int) request.getSession().getAttribute("auid");
			request.getSession().setAttribute("uuid", user_u_id);
			System.out.println(active_user);
			System.out.println(user_u_id);
			Connection conn = null;
			ResultSet rs;
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	Statement stmt = conn.createStatement();
        	
        	//ACTIVE USER RATING
			rs = stmt.executeQuery("SELECT feature_id, field_name, name, AVG(rating) AS rating FROM user_feature_values, features\r\n" + 
					"WHERE user_id = '" + active_user + "' AND user_feature_values.feature_id = features.id GROUP BY name, field_name, feature_id ORDER BY name");
			List<String> list_feature_active_user = new ArrayList<>();
			List<Integer> list_rating_active_user = new ArrayList<>();
			List<String> list_field_name = new ArrayList<>();		
			List<selectedFeatures> list_selected_feature = new ArrayList<>();
			while (rs.next()) {
				String feature_name = rs.getString("name");
				int rating = rs.getInt("rating");
				String field_name = rs.getString("field_name");
				int feature_id = rs.getInt("feature_id");
				list_feature_active_user.add(feature_name);
				list_rating_active_user.add(rating);
				list_field_name.add(field_name);
				
				selectedFeatures selected = new selectedFeatures();
				selected.setId(feature_id);
				selected.setField_name(field_name);
				selected.setName(feature_name);
				list_selected_feature.add(selected);
			}
        	request.getSession().setAttribute("listselected", list_selected_feature);
        	
			rs = stmt.executeQuery("SELECT user_id FROM similar_user WHERE active_user_id = '" + active_user + "' ORDER BY similarity DESC LIMIT 6");
			List<String> list_similar_user = new ArrayList<>();
			while (rs.next()) {
				String similar_user = rs.getString("user_id");
				list_similar_user.add(similar_user);
			}
			request.setAttribute("su", list_similar_user);
			
			//USER U RATING
			List<Double> list_rating_user_u = new ArrayList<>();
			for (int i = 0; i < list_field_name.size(); i++) {			
				rs = stmt.executeQuery("SELECT * FROM feature_weight WHERE user_id = '" + user_u_id + "' ORDER BY user_id ASC");
				while (rs.next()) {
					double weight = rs.getDouble(list_field_name.get(i));
					double rating = weight * (5 - 1) + 1;
					list_rating_user_u.add(rating);
				}			
//				double avg_rating_user_u = Misc.round(Misc.mean(list_rating_user_u), 2);
//				list_avg_rating_user_u.add(avg_rating_user_u);
			}
			
			Gson gsonObj = new Gson();
			
			String list_feature_json = gsonObj.toJson(list_feature_active_user);
			String list_rating_active_user_json = gsonObj.toJson(list_rating_active_user);
			String list_rating_user_u_json = gsonObj.toJson(list_rating_user_u);
			String useru_id = gsonObj.toJson(user_u_id);
			
			
			request.getSession().setAttribute("useru", useru_id);
			request.getSession().setAttribute("feature", list_feature_json);
			request.getSession().setAttribute("rating", list_rating_active_user_json);
			request.getSession().setAttribute("urating", list_rating_user_u_json);
        	
        	
//        	response.sendRedirect(getServletContext().getContextPath()+"/GetChartAvgUserRatingServlet");
        	request.getRequestDispatcher("chartUserURating.jsp").forward(request, response);	
			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
