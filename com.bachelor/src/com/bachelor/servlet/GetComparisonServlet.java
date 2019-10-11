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
import com.bachelor.object.thisItem;
import com.bachelor.object.userFeatureValues;

@WebServlet("/GetComparisonServlet")
public class GetComparisonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetComparisonServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int active_user = (int) request.getSession().getAttribute("auid");
			String item_id = request.getParameter("item-id");
			
			Connection conn = null;
			ResultSet rs;
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	Statement stmt = conn.createStatement();
        	
        	List<userFeatureValues> list_text = new ArrayList<>();
        	List<userFeatureValues> list_range = new ArrayList<>();
        	List<String> list_field_name_text = new ArrayList<>();
        	List<String> list_field_name_range = new ArrayList<>();
        	List<String> list_name_text = new ArrayList<>();
        	List<String> list_name_range = new ArrayList<>();
        	rs = stmt.executeQuery("SELECT * FROM user_feature_values, features WHERE user_id = '" + active_user + "' AND feature_id = features.id");
        	while (rs.next()) {
        		String feature_type = rs.getString("feature_type");
        		switch (feature_type) {
				case "TEXT":
					String value = rs.getString("value");
					int rating_text = rs.getInt("rating");
					String name_text = rs.getString("name");
					String field_name_text = rs.getString("field_name");
					list_field_name_text.add(field_name_text);
					list_name_text.add(name_text);
//					System.out.println("TEXT: " + name_text);
					
					userFeatureValues text = new userFeatureValues();
					text.setField_name(field_name_text);
					text.setName(name_text);
					text.setValue(value);
					list_text.add(text);
					break;

				case "CUSTOM_RANGE":
					int min = rs.getInt("min");
					int max = rs.getInt("max");
					int rating_range = rs.getInt("rating");
					String name_range = rs.getString("name");
					String field_name_range = rs.getString("field_name");
					list_field_name_range.add(field_name_range);
					list_name_range.add(name_range);
//					System.out.println("RANGE: " + name_range);
					
					userFeatureValues range = new userFeatureValues();
					range.setField_name(field_name_range);
					range.setName(name_range);
					range.setMin(min);
					range.setMax(max);
					list_range.add(range);
					break;
				}
        	}
        	System.out.println(list_field_name_text);
        	System.out.println(list_field_name_range);
        	
        	List<thisItem> list_this_item_text = new ArrayList<>();
        	List<thisItem> list_this_item_number = new ArrayList<>();
        	rs = stmt.executeQuery("SELECT * FROM item WHERE id = '" + item_id + "'");
        	while (rs.next()) {
        		for (int i = 0; i < list_field_name_text.size(); i++) {
        			String value = rs.getString(list_field_name_text.get(i));
        			thisItem this_item_text = new thisItem();
        			this_item_text.setField_name(list_field_name_text.get(i));
        			this_item_text.setText_value(value);
        			list_this_item_text.add(this_item_text);
//        			System.out.println(list_field_name_text.get(i) + ": " + value);
        		}
        		for (int i = 0; i < list_field_name_range.size(); i++) {
        			int value = rs.getInt(list_field_name_range.get(i));
        			thisItem this_item_number = new thisItem();
        			this_item_number.setField_name(list_field_name_range.get(i));
        			this_item_number.setNumber_value(value);
        			list_this_item_number.add(this_item_number);
//        			System.out.println(list_field_name_range.get(i) + ": " + value);
        		}
        	}
        	
        	List<thisItem> list_pro = new ArrayList<>();
        	List<thisItem> list_con = new ArrayList<>();
        	for (int i = 0; i < list_this_item_text.size(); i++) {
        		System.out.println(list_text.get(i).getValue() + " -- " + list_this_item_text.get(i).getText_value());
        		if (list_this_item_text.get(i).getText_value().equals(list_text.get(i).getValue())) {
        			thisItem pro_item = new thisItem();
        			pro_item.setName(list_text.get(i).getName());
        			pro_item.setActive_user_value(list_text.get(i).getValue());
        			pro_item.setText_value(list_this_item_text.get(i).getText_value());
        			list_pro.add(pro_item);
        		} else if (list_text.get(i).getValue().equals("optional") && !list_this_item_text.get(i).getText_value().equals("")) {
        			thisItem pro_item = new thisItem();
        			pro_item.setName(list_text.get(i).getName());
        			pro_item.setActive_user_value(list_text.get(i).getValue());
        			pro_item.setText_value(list_this_item_text.get(i).getText_value());
        			list_pro.add(pro_item);
        		} else if (list_this_item_text.get(i).getText_value().equals("")) {
        			thisItem con_item = new thisItem();
        			con_item.setName(list_text.get(i).getName());
        			con_item.setActive_user_value(list_text.get(i).getValue());
        			con_item.setText_value(" - ");
        			list_con.add(con_item);
        		}else {
        			thisItem con_item = new thisItem();
        			con_item.setName(list_text.get(i).getName());
        			con_item.setActive_user_value(list_text.get(i).getValue());
        			con_item.setText_value(list_this_item_text.get(i).getText_value());
        			list_con.add(con_item);
        		}
        	}
        	
        	for (int i = 0; i < list_this_item_number.size(); i++) {
        		if (list_this_item_number.get(i).getNumber_value() >= list_range.get(i).getMin() && list_this_item_number.get(i).getNumber_value() <= list_range.get(i).getMax()) {
        			thisItem pro_item = new thisItem();
        			pro_item.setName(list_range.get(i).getName());
        			pro_item.setActive_user_value(list_range.get(i).getMin() + " - " + list_range.get(i).getMax());
        			pro_item.setText_value(Integer.toString(list_this_item_number.get(i).getNumber_value()));
        			list_pro.add(pro_item);
        		} else {
        			thisItem con_item = new thisItem();
        			con_item.setName(list_range.get(i).getName());
        			con_item.setActive_user_value(list_range.get(i).getMin() + " - " + list_range.get(i).getMax());
        			con_item.setText_value(Integer.toString(list_this_item_number.get(i).getNumber_value()));
        			list_con.add(con_item);
        		}
        	}
        	for (int i = 0; i < list_pro.size(); i++) {
        		System.out.println(list_pro.get(i).getName() + ": " + list_pro.get(i).getText_value() + " Your choice: " + list_pro.get(i).getActive_user_value());
        	}
        	System.out.println("------------------------");
        	for (int i = 0; i < list_con.size(); i++) {
        		System.out.println(list_con.get(i).getName() + ": " + list_con.get(i).getText_value() + " Your choice: " + list_con.get(i).getActive_user_value());
        	}
        	
        	request.getSession().setAttribute("listpro", list_pro);
        	request.getSession().setAttribute("listcon", list_con);
        	response.sendRedirect(getServletContext().getContextPath()+"/home");
//        	request.getRequestDispatcher("home.jsp").forward(request, response);
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
