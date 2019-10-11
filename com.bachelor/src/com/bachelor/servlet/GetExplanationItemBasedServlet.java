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
import com.google.gson.Gson;

@WebServlet("/GetExplanationItemBasedServlet")
public class GetExplanationItemBasedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetExplanationItemBasedServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int active_user_id = (int) request.getSession().getAttribute("auid");
			String item_id = (String) request.getParameter("item-id");
			
			Connection conn = null;
			ResultSet rs;
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	PreparedStatement pstm;
        	
        	Gson gsonObj = new Gson();
        	
        	// THIS ITEM RATINGS
        	pstm = conn.prepareStatement("SELECT product_name, rating, COUNT(rating) AS count_rating FROM view_rated_match_item \r\n" + 
										"WHERE view_rated_match_item.id::CHARACTER VARYING = ? " + 
										"AND user_id IN (SELECT DISTINCT user_id FROM match_rated_item_pair WHERE item_1 = ?) " +
										"GROUP BY rating, product_name ORDER BY rating ASC");
        	pstm.setString(1, item_id);
        	pstm.setString(2, item_id);
        	rs = pstm.executeQuery();
        	List<Integer> list_count_rating_this_item = new ArrayList<>();
        	list_count_rating_this_item.add(0);
        	list_count_rating_this_item.add(0);
        	list_count_rating_this_item.add(0);
        	list_count_rating_this_item.add(0);
        	list_count_rating_this_item.add(0);
        	String this_item = null;
        	while (rs.next()) {
        		int count_rating_this_item = rs.getInt("count_rating");
        		int rating = rs.getInt("rating");
        		switch (rating) {
        		case 1:
        			list_count_rating_this_item.set(0, count_rating_this_item);
            		break;
        		case 2:
        			list_count_rating_this_item.set(1, count_rating_this_item);
            		break;
        		case 3:
        			list_count_rating_this_item.set(2, count_rating_this_item);
            		break;
        		case 4:
        			list_count_rating_this_item.set(3, count_rating_this_item);
            		break;
        		case 5:
        			list_count_rating_this_item.set(4, count_rating_this_item);
            		break;
        		}
        		this_item = rs.getString("product_name");
        	}
        	rs.close();
        	String this_item_json = gsonObj.toJson(this_item);
        	String list_count_rating_this_item_json = gsonObj.toJson(list_count_rating_this_item);
        	System.out.println(this_item_json + " : " + list_count_rating_this_item_json);
        	
        	// ACTIVE USERS RATINGS
        	List<String> list_active_user_item_id = new ArrayList<>();
        	pstm = conn.prepareStatement("SELECT * FROM user_item WHERE active_user_id = ?");
        	pstm.setInt(1, active_user_id);
        	rs = pstm.executeQuery();
        	while (rs.next()) {
        		String active_user_item_id = rs.getString("item_id");
        		list_active_user_item_id.add(active_user_item_id);
        	}
        	rs.close();       	
        	
        	List<String> list_all_item_active_user_active_user_json = new ArrayList<>();
        	List<String> list_all_count_rating_active_user_json = new ArrayList<>();
        	for (int i = 0; i < list_active_user_item_id.size(); i++) {
        		List<String> list_item_active_user = new ArrayList<>();
            	List<Integer> list_count_rating_active_user = new ArrayList<>();
            	list_count_rating_active_user.add(0);
            	list_count_rating_active_user.add(0);
            	list_count_rating_active_user.add(0);
            	list_count_rating_active_user.add(0);
            	list_count_rating_active_user.add(0);
            	
            	String list_count_rating_active_user_json = null;
        		String item_active_user = null;
            	
        		pstm = conn.prepareStatement("SELECT product_name, rating, COUNT(rating) AS count_rating FROM view_rated_match_item \r\n" + 
											"WHERE view_rated_match_item.id::CHARACTER VARYING = ? " + 
											"AND user_id IN (SELECT DISTINCT user_id FROM match_rated_item_pair WHERE item_1 = ?) " +
											"GROUP BY rating, product_name ORDER BY rating ASC");
        		pstm.setString(1, list_active_user_item_id.get(i));
        		pstm.setString(2, list_active_user_item_id.get(i));
        		rs = pstm.executeQuery();

        		while (rs.next()) {
        			item_active_user = rs.getString("product_name");
            		int count_rating_active_user = rs.getInt("count_rating");
            		int rating = rs.getInt("rating");
            		switch (rating) {
            		case 1:
            			list_count_rating_active_user.set(0, count_rating_active_user);
                		break;
            		case 2:
            			list_count_rating_active_user.set(1, count_rating_active_user);
                		break;
            		case 3:
            			list_count_rating_active_user.set(2, count_rating_active_user);
                		break;
            		case 4:
            			list_count_rating_active_user.set(3, count_rating_active_user);
                		break;
            		case 5:
            			list_count_rating_active_user.set(4, count_rating_active_user);
                		break;
            		}
            		list_count_rating_active_user_json = gsonObj.toJson(list_count_rating_active_user);            		           		
        		}
        		String item_active_user_json = gsonObj.toJson(item_active_user);
        		list_all_count_rating_active_user_json.add(list_count_rating_active_user_json);
        		list_all_item_active_user_active_user_json.add(item_active_user_json);
        	}
    		
			request.setAttribute("thisItem", this_item_json);
			request.setAttribute("listItemActiveUser", list_all_item_active_user_active_user_json);
			request.setAttribute("listCountRatingActiveUser", list_all_count_rating_active_user_json);
			request.setAttribute("listCountRatingThisItem", list_count_rating_this_item_json);
			
			System.out.println(list_all_count_rating_active_user_json);
			System.out.println(list_all_item_active_user_active_user_json);
			
			request.getRequestDispatcher("chartRatings.jsp").forward(request, response);	
			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
