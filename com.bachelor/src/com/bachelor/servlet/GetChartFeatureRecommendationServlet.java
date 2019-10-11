package com.bachelor.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.myUtils.Misc;
import com.google.gson.Gson;

@WebServlet("/GetChartFeatureRecommendationServlet")
public class GetChartFeatureRecommendationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetChartFeatureRecommendationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String field_name = request.getParameter("recommended-field-name");
			int active_user_id = (int) request.getSession().getAttribute("auid");
			
			Connection conn = null;
			ResultSet rs;
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	Statement stmt = conn.createStatement();
        	
        	Gson gsonObj = new Gson();
        	
        	String feature_type = null;
        	String feature_name = null;
        	rs = stmt.executeQuery("SELECT name, feature_type FROM features WHERE field_name = '" + field_name + "'");
        	while (rs.next()) {
        		feature_type = rs.getString("feature_type");
        		feature_name = rs.getString("name");
        	}
        	String feature_type_json = gsonObj.toJson(feature_type);
        	request.setAttribute("featuretype", feature_type_json);
        	
        	List<String> list_data = new ArrayList<>();
        	List<String> list_value = new ArrayList<>();
        	List<Integer> list_count = new ArrayList<>();
        	
        	String recommended_value = null;
        	ResultSet rs_recommended = stmt.executeQuery("SELECT recommended_feature.*, features.name, features.field_name\r\n" + 
					"FROM recommended_feature, features WHERE active_user_id = '" + active_user_id + "' AND features.id = recommended_feature.feature_id \r\n" + 
					"AND name = '" + feature_name + "' ORDER BY predicted_rating DESC LIMIT 8");
        	while (rs_recommended.next()) {
        		recommended_value = rs_recommended.getString("feature_value");
        	}
        	
        	request.setAttribute("recommendedvalue", recommended_value);
        	request.setAttribute("recommendedfeature", feature_name);
        	request.setAttribute("type", feature_type);
        	
        	if (feature_type.contentEquals("TEXT")) {
        		int all_count = 0;
				ResultSet rs_all_count = stmt.executeQuery("SELECT SUM(count) AS all_count "
														+ "FROM (SELECT COUNT(" + field_name + ") FROM view_rated_match_item "
														+ "WHERE user_id IN (SELECT user_id FROM similar_user WHERE active_user_id = '" + active_user_id + "' ORDER BY similarity DESC LIMIT 20) "
														+ "GROUP BY " + field_name + ") AS count_table");
        		while (rs_all_count.next()) {
        			all_count = rs_all_count.getInt("all_count");
        		}
				ResultSet rs_text = stmt.executeQuery(
						"SELECT " + field_name + ", COUNT(" + field_name + ") FROM view_rated_match_item \r\n"
								+ "WHERE user_id IN (SELECT user_id FROM similar_user WHERE active_user_id ='"
								+ active_user_id + "' AND " + field_name + " != '' ORDER BY similarity DESC LIMIT 20)\r\n" + "GROUP BY " + field_name
								+ " ORDER BY " + field_name + " ASC");
				while (rs_text.next()) {
					String value = rs_text.getString(1);
					int count = rs_text.getInt(2);
					
					double count_percent = (double)count / (double)all_count * 100;
					System.out.println(count + "/" + all_count + "*100=" + count_percent);
					String data = "{" + "name: '" + value + "', y: " + count_percent + "}";
					list_data.add(data);
					
					list_value.add(value);
					list_count.add(count);
				}				
				
				request.setAttribute("listdata", list_data);
				request.setAttribute("listvalue", "null");
				request.setAttribute("listcount", "null"); 
        	} else {
				rs = stmt.executeQuery(
						"SELECT " + field_name + ", COUNT(" + field_name + ") FROM view_rated_match_item \r\n"
								+ "WHERE user_id IN (SELECT user_id FROM similar_user WHERE active_user_id ='"
								+ active_user_id + "' AND " + field_name + " != 0 ORDER BY similarity DESC LIMIT 20)\r\n" + "GROUP BY " + field_name
								+ " ORDER BY " + field_name + " ASC");
				while (rs.next()) {
					String value = rs.getString(1);
            		int count = rs.getInt(2);
            		list_value.add(value);
            		list_count.add(count);
            	}
				
				request.setAttribute("listdata", "null");
				request.setAttribute("listvalue", gsonObj.toJson(list_value));
				request.setAttribute("listcount", gsonObj.toJson(list_count)); 
        	}
        	
			request.setAttribute("featurename", gsonObj.toJson(feature_name));
			
        	request.getRequestDispatcher("chartFeatureRecommendation.jsp").forward(request, response);
			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
