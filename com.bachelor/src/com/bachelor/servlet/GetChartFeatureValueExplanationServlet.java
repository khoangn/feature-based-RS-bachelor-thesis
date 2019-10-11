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

@WebServlet("/GetChartFeatureValueExplanationServlet")
public class GetChartFeatureValueExplanationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetChartFeatureValueExplanationServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String user_u_id = (String) request.getSession().getAttribute("uuid");
			int active_user = (int) request.getSession().getAttribute("auid");
			String selected_field_name = request.getParameter("selected-field-name");
			String selected_item_id = request.getParameter("selected-item-id");

			Connection conn = null;
			ResultSet rs;
			Class.forName("org.postgresql.Driver");
			conn = DatabaseConnector.getConnection();
			Statement stmt = conn.createStatement();

			String feature_type = null;
			String feature_name = null;
			rs = stmt.executeQuery(
					"SELECT name, feature_type FROM features WHERE field_name = '" + selected_field_name + "'");
			while (rs.next()) {
				feature_type = rs.getString("feature_type");
				feature_name = rs.getString("name");
			}
			System.out.println("TYPE: " + feature_type);
			
			List<String> list_data = new ArrayList<>();
			
			List<String> list_value = new ArrayList<>();
			List<String> list_value_range = new ArrayList<>();
			List<Double> list_value_number = new ArrayList<>();
			List<Integer> list_count = new ArrayList<>();
			String value_text = null;
			int value_min = 0;
			int value_max = 0;
			List<String> list_value_text = new ArrayList<>();
			int value_text_index = 0;
			List<Integer> list_value_text_index = new ArrayList<>();
			String value_this_cam = null;
			double value_this_cam_number = 0;
			String name_this_cam = null;
			int value_this_cam_index = 0;
			int value_min_index = 0;
			int value_max_index = 0;
			String list_value_json = null;
			String list_count_json = null;

			Gson gsonObj = new Gson();

			String feature_type_json = gsonObj.toJson(feature_type);
			request.setAttribute("featuretype", feature_type_json);

			if (feature_type.equals("TEXT")) {
        		int all_count = 0;
				ResultSet rs_all_count = stmt.executeQuery("SELECT SUM(count) AS all_count "
														+ "FROM (SELECT COUNT(" + selected_field_name + ") FROM view_rated_match_item "
														+ "WHERE user_id IN (SELECT user_id FROM similar_user WHERE active_user_id = '" + active_user + "' ORDER BY similarity DESC LIMIT 20) "
														+ "GROUP BY " + selected_field_name + ") AS count_table");
        		while (rs_all_count.next()) {
        			all_count = rs_all_count.getInt("all_count");
        		}
				ResultSet rs_text = stmt.executeQuery("SELECT " + selected_field_name + ", COUNT(" + selected_field_name
						+ ") FROM view_rated_match_item \r\n"
						+ "WHERE user_id IN (SELECT user_id FROM similar_user WHERE active_user_id ='" + active_user
						+ "' AND " + selected_field_name + " !='' ORDER BY similarity DESC LIMIT 20)\r\n" + "GROUP BY "
						+ selected_field_name + " ORDER BY " + selected_field_name + " ASC");
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
				// FEATURE VALUES OF ACTIVE USER
				ResultSet rs_active_user = stmt
						.executeQuery("SELECT * FROM user_feature_values WHERE active_user_id = '" + active_user + "' "
								+ "AND feature_id=(SELECT id FROM features WHERE field_name = '" + selected_field_name
								+ "')");
				while (rs_active_user.next()) {
					value_text = rs_active_user.getString("value");
					list_value_text.add(value_text);
				}
				for (int i = 0; i < list_value_text.size(); i++) {
					for (int j = 0; j < list_value.size(); j++) {
						if (list_value_text.get(i).equals(list_value.get(j))) {
							value_text_index = j;
							list_value_text_index.add(value_text_index);
						}
					}
				}
				System.out.println("YOUR CHOICES QUALI: " + list_value_text_index);

				// FEATURE VALUES OF THIS CAMERA
				ResultSet rs_this_cam = stmt.executeQuery("SELECT * FROM item WHERE id = '" + selected_item_id + "'");
				while (rs_this_cam.next()) {
					value_this_cam = rs_this_cam.getString(selected_field_name);
					name_this_cam = rs_this_cam.getString("product_name");
				}
				for (int i = 0; i < list_value.size(); i++) {
					if (list_value.get(i).equals(value_this_cam))
						value_this_cam_index = i;
				}

				list_value_json = gsonObj.toJson(list_value);
				list_count_json = gsonObj.toJson(list_count);
				request.setAttribute("listvaluetext", list_value_json);
				request.setAttribute("listcounttext", list_count_json);
				request.setAttribute("listvalueactiveuserindex", list_value_text_index);
				
//				request.setAttribute("listdata", gsonObj.toJson(list_data));
				request.setAttribute("listdata", list_data);
				
				request.setAttribute("listvaluenumber", "null");
				request.setAttribute("listcountnumber", "null");
				request.setAttribute("minindex", "null");
				request.setAttribute("maxindex", "null");
				
				System.out.println("YOUR QUALI: " + list_value_json);
				System.out.println("YOUR QUALI COUNT: " + list_count_json);

			} else {
				ResultSet rs_numeric = stmt.executeQuery("SELECT " + selected_field_name + ", COUNT("
						+ selected_field_name + ") FROM view_rated_match_item \r\n"
						+ "WHERE user_id IN (SELECT user_id FROM similar_user WHERE active_user_id ='" + active_user
						+ "' ORDER BY similarity DESC LIMIT 20)\r\n" + "GROUP BY " + selected_field_name + " ORDER BY "
						+ selected_field_name + " ASC");
				while (rs_numeric.next()) {
					double value = rs_numeric.getDouble(1);
					int count = rs_numeric.getInt(2);
					list_value_number.add(value);
					list_count.add(count);
				}
				double max = 0;
				double min = 0;
				rs = stmt.executeQuery("SELECT MAX(" + selected_field_name + "), MIN(" + selected_field_name
						+ ") FROM view_rated_match_item\r\n"
						+ "WHERE user_id IN (SELECT user_id FROM similar_user WHERE active_user_id ='" + active_user
						+ "' ORDER BY similarity DESC LIMIT 20)");
				while (rs.next()) {
					max = rs.getDouble(1);
					min = rs.getDouble(2);
				}
				Integer[] array_count_numer = { 0, 0, 0, 0, 0, 0 };
				List<Integer> list_count_number = Arrays.asList(array_count_numer);
				List<List<Double>> list_custom_range = Misc.createCustomRange(min, max);

				for (int i = 0; i < list_custom_range.size(); i++) {
					String value = list_custom_range.get(i).get(0) + " - " + list_custom_range.get(i).get(1);
					list_value_range.add(value);
				}

				for (int i = 0; i < list_value_number.size(); i++) {
					for (int j = 0; j < list_custom_range.size(); j++) {
						if (list_value_number.get(i) >= list_custom_range.get(j).get(0)
								&& list_value_number.get(i) < list_custom_range.get(j).get(1)) {
							list_count_number.set(j, list_count.get(i) + list_count_number.get(j));
						}
					}
				}

				// FEATURE VALUES OF THIS CAMERA
				ResultSet rs_this_cam = stmt.executeQuery("SELECT * FROM item WHERE id = '" + selected_item_id + "'");
				while (rs_this_cam.next()) {
					value_this_cam_number = rs_this_cam.getDouble(selected_field_name);
					name_this_cam = rs_this_cam.getString("product_name");
				}
				for (int i = 0; i < list_custom_range.size(); i++) {
					if (value_this_cam_number >= list_custom_range.get(i).get(0)
							&& value_this_cam_number < list_custom_range.get(i).get(1))
						value_this_cam_index = i;
				}

				// FEATURE VALUES OF ACTIVE USER
				ResultSet rs_active_user = stmt
						.executeQuery("SELECT * FROM user_feature_values WHERE active_user_id = '" + active_user + "' "
								+ "AND feature_id=(SELECT id FROM features WHERE field_name = '" + selected_field_name
								+ "')");
				while (rs_active_user.next()) {
					value_min = rs_active_user.getInt("min");
					value_max = rs_active_user.getInt("max");
				}

				for (int i = 0; i < list_custom_range.size(); i++) {
					if (value_min >= list_custom_range.get(i).get(0) && value_min < list_custom_range.get(i).get(1)) {
						value_min_index = i;
					}
				}
				for (int i = 0; i < list_custom_range.size(); i++) {
					if (value_max >= list_custom_range.get(i).get(0) && value_max < list_custom_range.get(i).get(1) || value_max > list_custom_range.get(5).get(1)) {					
						value_max_index = i;
					} 
				}
				if (value_max_index < value_min_index)
					value_max_index = list_custom_range.size() - 1;

				list_value_json = gsonObj.toJson(list_value_range);
				list_count_json = gsonObj.toJson(list_count_number);
				String value_min_index_json = gsonObj.toJson(value_min_index);
				String value_max_index_json = gsonObj.toJson(value_max_index);
				request.setAttribute("listvaluenumber", list_value_json);
				request.setAttribute("listcountnumber", list_count_json);

				request.setAttribute("minindex", value_min_index_json);
				request.setAttribute("maxindex", value_max_index_json);

				request.setAttribute("listdata", "null");
				request.setAttribute("listvaluetext", "null");
				request.setAttribute("listcounttext", "null");
				request.setAttribute("listvalueactiveuserindex", list_value_text_index);

				System.out.println("YOUR QUANTI: " + list_value_json);
				System.out.println("YOUR QUANTI COUNT: " + list_count_json);
				System.out.println("FROM: " + value_min_index_json);
				System.out.println("TO: " + value_max_index_json);
			}

			String value_this_cam_index_json = gsonObj.toJson(value_this_cam_index);
			String name_this_cam_json = gsonObj.toJson(name_this_cam);
			
			request.setAttribute("thiscam", name_this_cam);
			request.setAttribute("feature", feature_name);
			request.setAttribute("valuenumber", (int) value_this_cam_number);
			request.setAttribute("yourmin", (int) value_min);
			request.setAttribute("yourmax", (int) value_max);
			request.setAttribute("valuetext",  value_this_cam);
			request.setAttribute("yourtext",  value_text);
			request.setAttribute("type", feature_type);
			
			request.setAttribute("featurename", gsonObj.toJson(feature_name));

			request.setAttribute("valuethiscamindex", value_this_cam_index);
			request.setAttribute("namethiscam", name_this_cam_json);

			System.out.println("THIS CAMERA: " + value_this_cam_index_json);

			request.getRequestDispatcher("chartFeatureValueExplanation.jsp").forward(request, response);
			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
