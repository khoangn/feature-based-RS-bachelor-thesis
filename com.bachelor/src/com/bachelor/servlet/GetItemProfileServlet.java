package com.bachelor.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.object.itemProfile;

/**
 * Servlet implementation class GetItemProfile
 */
@WebServlet("/GetItemProfileServlet")
public class GetItemProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetItemProfileServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		ResultSet rs;

		try {
			Class.forName("org.postgresql.Driver");
			conn = DatabaseConnector.getConnection();
			Statement stmt = conn.createStatement();
			
			String id = request.getParameter("item-id");
			rs = stmt.executeQuery("SELECT * FROM features WHERE field_name NOT IN ('pixel_number','avg_online_price', 'exposure_program', 'zoom_factor') ORDER BY name");
			List<String> list_field_name = new ArrayList<>();
			List<String> list_feature_name = new ArrayList<>();
			while(rs.next()) {
				String field_name = rs.getString("field_name");
				String name = rs.getString("name");
				list_field_name.add(field_name);
				list_feature_name.add(name);
				System.out.println(field_name);
			}
			rs = stmt.executeQuery("SELECT * FROM item WHERE id ='" + id + "'");
			List<String> list_feature_value = new ArrayList<>();
			List<String> list_feature = new ArrayList<>();
			while (rs.next()) {				
				String product_name = rs.getString("product_name");
				String price = Integer.toString(rs.getInt("avg_online_price"));
				String pixel_number = Integer.toString(rs.getInt("pixel_number"));
				String exposure_program = rs.getString("exposure_program");
				String zoom_factor = Integer.toString(rs.getInt("zoom_factor"));
				String img_src = rs.getString("img_src");
				
				request.setAttribute("name", product_name);
				request.setAttribute("price", price);
				request.setAttribute("pixel", pixel_number);
				request.setAttribute("exposure", exposure_program);
				request.setAttribute("zoom", zoom_factor);
				request.setAttribute("img", img_src);

				for (int i = 0; i < list_field_name.size(); i++) {
					String value = rs.getString(list_field_name.get(i));
					System.out.println(list_field_name.get(i) + ": " + value);
					if (value.length() == 0 || value == null || value.equals("0")) {

					} else if (list_field_name.get(i).equals("online_publication")) {
						if (value.length() == 6) {
							value = value.substring(2, 6) + "0" + value.substring(1, 2) + "0" + value.substring(0, 1);
						} else if(value.length() == 7) {
							value = value.substring(3, 7) + value.substring(1, 3) + "0" + value.substring(0, 1);
						} else {
							value = value.substring(4, 8) + value.substring(2, 4) + value.substring(0, 2);
						}
						String feature_value = list_feature_name.get(i).concat(": " + LocalDate.parse(value, DateTimeFormatter.BASIC_ISO_DATE));
						list_feature_value.add(feature_value);
					} else {
						String feature_value = list_feature_name.get(i).concat(": " + value);
						list_feature_value.add(feature_value);
					}
				}
			}
			request.setAttribute("listFeatureValue", list_feature_value);
	    	request.getRequestDispatcher("itemProfile.jsp").forward(request, response);	

			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
