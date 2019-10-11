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
import com.bachelor.object.selectedFeatures;
import com.bachelor.object.similarUserCompare;

@WebServlet("/Get20SimilarUsersExplanationServlet")
public class Get20SimilarUsersExplanationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Get20SimilarUsersExplanationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		try {
			int active_user_id = (int) request.getSession().getAttribute("auid");
			
			Connection conn = null;
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	Statement stmt = conn.createStatement();
        	
        	//ACTIVE USER FEATURES
			List<selectedFeatures> list_selected = new ArrayList<>();
        	ResultSet rs = stmt.executeQuery("SELECT DISTINCT field_name, feature_id, name FROM user_feature_values, features "
        			+ "WHERE active_user_id = '" + active_user_id + "' AND feature_id = features.id");
        	while (rs.next()) {
        		String field_name = rs.getString("field_name");
        		int id = rs.getInt("feature_id");
        		String name = rs.getString("name");
        		selectedFeatures selected = new selectedFeatures();
        		selected.setField_name(field_name);
        		selected.setId(id);
        		selected.setName(name); 
        		list_selected.add(selected);
        	}
        	rs.close();
        	request.setAttribute("listselected", list_selected);

        	
        	//ACTIVE PREFERENCES
        	List<similarUserCompare> list_similar_user_compare = new ArrayList<>();       
			ResultSet rs_similar = stmt.executeQuery("SELECT * FROM user_feature_values, features WHERE active_user_id = '" + active_user_id
					+ "' AND feature_id = features.id");
			while (rs_similar.next()) {
				String feature_type = rs_similar.getString("feature_type");
				switch (feature_type) {
				case "TEXT":
					String value = rs_similar.getString("value");

					String name_text = rs_similar.getString("name");
					String field_name_text = rs_similar.getString("field_name");						
		        	
					similarUserCompare similar_user_compare = new similarUserCompare();
					Statement stmt_similar_user_text = conn.createStatement();;
					ResultSet rs_similar_user_text = stmt_similar_user_text.executeQuery("SELECT COUNT(" + field_name_text + "), " + field_name_text 
									+ " FROM view_rated_match_item "
									+ "WHERE user_id IN (SELECT user_id FROM similar_user WHERE active_user_id = '"
									+ active_user_id + "' ORDER BY similarity DESC LIMIT 20) " + "GROUP BY "
									+ field_name_text + " ORDER BY COUNT DESC");
					while (rs_similar_user_text.next()) {
						int count_text = rs_similar_user_text.getInt("count");
						String value_similar_user_text = rs_similar_user_text.getString(field_name_text);
						int all_count = 0;
						
						Statement stmt_all_count = conn.createStatement();
						ResultSet rs_all_count = stmt_all_count.executeQuery("SELECT SUM(count) AS all_count "
								+ "FROM (SELECT COUNT(" + field_name_text + ") FROM view_rated_match_item " 
								+ "WHERE user_id IN (SELECT user_id FROM similar_user WHERE active_user_id = '" + active_user_id + "' ORDER BY similarity DESC LIMIT 20) " 
								+ "GROUP BY " + field_name_text + ") AS count_table");
						while (rs_all_count.next()) {
							all_count = rs_all_count.getInt("all_count");
						}
						if (value.equals(value_similar_user_text)) {
							similar_user_compare.setFeature(name_text);
							similar_user_compare.setYour_choice(value);
							similar_user_compare.setSimilar_user_count(count_text);
							similar_user_compare.setSimilar_user_count_all(all_count);
						}
					}
					
					if(similar_user_compare.getSimilar_user_count() != 0) list_similar_user_compare.add(similar_user_compare);
					
					break;

				case "CUSTOM_RANGE":
					int min = rs_similar.getInt("min");
					int max = rs_similar.getInt("max");
					String min_max = min +  " - " + max;
					
					String name_range = rs_similar.getString("name");
					String field_name_range = rs_similar.getString("field_name");
					
					similarUserCompare similar_user_compare_range = new similarUserCompare();
					Statement stmt_similar_user_range = conn.createStatement();;
					ResultSet rs_similar_user_range = stmt_similar_user_range.executeQuery(
									  "SELECT COUNT(" + field_name_range + "), " + field_name_range + " FROM view_rated_match_item "
									+ "WHERE user_id IN (SELECT user_id FROM similar_user WHERE active_user_id = '"
									+ active_user_id + "' ORDER BY similarity DESC LIMIT 20) " + "GROUP BY "
									+ field_name_range + " ORDER BY COUNT DESC");
					int all_count = 0;
					int count_range = 0;
					while (rs_similar_user_range.next()) {
						
						int value_similar_user_range = rs_similar_user_range.getInt(field_name_range);
						
						Statement stmt_all_count = conn.createStatement();
						ResultSet rs_all_count = stmt_all_count.executeQuery("SELECT SUM(count) AS all_count "
								+ "FROM (SELECT COUNT(" + field_name_range + ") FROM view_rated_match_item " 
								+ "WHERE user_id IN (SELECT user_id FROM similar_user WHERE active_user_id = '" + active_user_id + "' ORDER BY similarity DESC LIMIT 20) " 
								+ "GROUP BY " + field_name_range + ") AS count_table");
						while (rs_all_count.next()) {
							all_count = rs_all_count.getInt("all_count");
						}
						if (value_similar_user_range <= max && value_similar_user_range >= min) {
							count_range = count_range + rs_similar_user_range.getInt("count");
							similar_user_compare_range.setFeature(name_range);
							similar_user_compare_range.setYour_choice(min_max);
							similar_user_compare_range.setSimilar_user_count_all(all_count);
							similar_user_compare_range.setSimilar_user_count(count_range);
						}
					}
					
					if(similar_user_compare_range.getSimilar_user_count() != 0) list_similar_user_compare.add(similar_user_compare_range);
					
					break;
				}
			}
			request.setAttribute("listSimilarUserCompare", list_similar_user_compare);
			rs_similar.close();
	
        	//LIST SIMILAR USERS
			rs = stmt.executeQuery("SELECT user_id FROM similar_user WHERE active_user_id = '" + active_user_id + "' ORDER BY similarity DESC LIMIT 20");
			List<String> list_similar_user = new ArrayList<>();
			while (rs.next()) {
				String similar_user = rs.getString("user_id");
				list_similar_user.add(similar_user);
			}
			rs.close();
			request.setAttribute("su", list_similar_user);		
			request.getRequestDispatcher("20SimilarUserExplanation.jsp").forward(request, response);	
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
