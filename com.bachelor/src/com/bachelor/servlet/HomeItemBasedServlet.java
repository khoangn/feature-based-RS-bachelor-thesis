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
import com.bachelor.myUtils.Misc;
import com.bachelor.object.appUser;
import com.bachelor.object.itemAverageRating;
import com.bachelor.object.itemRating;
import com.bachelor.object.ratingCount;
import com.bachelor.object.recommendedItem;
import com.bachelor.object.topRatedCamera;

/**
 * Servlet implementation class HomeItemBasedServlet
 */
@WebServlet("/home_item_based")
public class HomeItemBasedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeItemBasedServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		ResultSet rs;
        try {
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	Statement stmt = conn.createStatement();
       
            appUser app_user = (appUser) request.getSession().getAttribute("activeAppUser");
            int dbId = app_user.getId();
            String username = app_user.getUsername();
            
            request.getSession().setAttribute("auid", dbId);
            request.getSession().setAttribute("username", username);
            
//         // TOP RATED CAMERAS
//    		List<topRatedCamera> list_top_rated_camera = new ArrayList<>();
//    		rs = stmt.executeQuery("SELECT id, product_name, img_src, AVG(rating) AS avg_rating FROM\r\n" + 
//    				"(SELECT item.*, rated_match.amazon_item_id, rated_match.user_id, rated_match.rating, rated_match.timestamp\r\n" + 
//    				"FROM item, (SELECT match_item.item_id, user_item_rating.* FROM match_item, user_item_rating WHERE match_item.amazon_item_id = user_item_rating.amazon_item_id) AS rated_match\r\n" + 
//    				"WHERE item.id = rated_match.item_id) AS view\r\n" + 
//    				"GROUP BY id, product_name, img_src ORDER BY avg_rating DESC LIMIT 12");
//    		while (rs.next()) {
//    			String id = rs.getString("id");
//    			String product_name = rs.getString("product_name");
//    			int rating = rs.getInt("avg_rating");
//    			String img_src = rs.getString("img_src");
//    			topRatedCamera camera = new topRatedCamera();
//    			camera.setId(id);
//    			camera.setProduct_name(product_name);
//    			camera.setRating(rating);
//    			camera.setImg_src(img_src);
//    			list_top_rated_camera.add(camera);
//    		}
//    		request.setAttribute("listTopRatedCamera", list_top_rated_camera);
            
            //ITEM RATED COUNT -- TO ENABLE SHOW RECOMMENDATION BUTTON
            rs = stmt.executeQuery("SELECT COUNT (item_id) FROM user_item WHERE active_user_id = " + dbId);
        	int item_rated_count = 0;
            while (rs.next()) {
            	item_rated_count = rs.getInt(1);
            }
            rs.close();
            request.getSession().setAttribute("item_rated_count", item_rated_count);
            
            //ITEM TO RATE
            List<itemRating> list_item = new ArrayList<>();
            rs = stmt.executeQuery("SELECT item.product_name, item.id, item.img_src, item_rank.item_rank FROM item, item_rank " + 
            		"WHERE item.id::CHARACTER VARYING = item_rank.item_id " + 
            		"ORDER BY item_rank.item_rank DESC LIMIT 30");
            while(rs.next()) {
            	String item_id = rs.getString("id");
            	String item_name = rs.getString("product_name");
            	String item_img = rs.getString("img_src");
            	double item_rank = rs.getDouble("item_rank");
            	
            	itemRating item = new itemRating();
            	Statement stmt1 = conn.createStatement();
            	ResultSet rs_with_rating = stmt1.executeQuery("SELECT * FROM user_item WHERE active_user_id = '" + dbId + "' AND item_id = '" + item_id + "'");
            	int rating = 0;
            	while (rs_with_rating.next()) {
            		rating = rs_with_rating.getInt("rating");
            	}
            	rs_with_rating.close();
            	item.setId(item_id);
            	item.setItem_name(item_name);
            	item.setItem_img(item_img);
            	item.setItem_rank(item_rank);
        		item.setRating(rating);
            	
            	list_item.add(item);
            	System.out.println(item_name + ": " + Misc.round(item_rank, 4));
            }
            rs.close();
            request.setAttribute("listItemToRate", list_item);
            
            //LIST RECOMMENDED ITEMS
            List<recommendedItem> list_recommended_item = new ArrayList<>();
			String query_get_recommended_items = "SELECT item.*, item_based_recommended_items.*, AVG(view_rated_match_item.rating) AS avg_rating, COUNT(rating) AS count_rating "
					+ "FROM item_based_recommended_items, item, view_rated_match_item\r\n"
					+ "WHERE active_user_id = ? AND item_based_recommended_items.item_id = item.id AND predicted_rating != 0\r\n"
					+ "AND item_id NOT IN (SELECT item_rank.item_id FROM item, item_rank\r\n"
					+ "WHERE item.id = item_rank.item_id\r\n"
					+ "ORDER BY item_rank.item_rank DESC LIMIT 10) AND view_rated_match_item.id = item.id\r\n"
					+ "GROUP BY view_rated_match_item.id, item.id, item_based_recommended_items.id\r\n"
					+ "ORDER BY predicted_rating DESC LIMIT 6 ";
            PreparedStatement pstm = conn.prepareStatement(query_get_recommended_items);
            pstm.setInt(1, dbId);
            rs = pstm.executeQuery();
            while (rs.next()) {
            	String item_id = rs.getString("item_id");
            	String item_name = rs.getString("product_name");
            	int price = rs.getInt("avg_online_price");
            	int pixel_number = rs.getInt("pixel_number");
            	String img_src = rs.getString("img_src");
            	double avg_rating = rs.getDouble("avg_rating");
            	int count_rating = rs.getInt("count_rating");
            	
    			recommendedItem item = new recommendedItem();
    			item.setId(item_id);
    			item.setImg_src(img_src);
    			item.setProduct_name(item_name);
    			item.setPrice(price);
    			item.setPixel_number(pixel_number);
    			item.setAvg_rating(avg_rating);
    			item.setCount_rating(count_rating);
    			
    			list_recommended_item.add(item);
            }          
            rs.close();
            
            for (int i = 0; i < list_recommended_item.size(); i++) {
            	String item_id = list_recommended_item.get(i).getId();
    			String get_average_rating_query = "SELECT t1.*, t2.average_rating, t2.total_count_rating, item.img_src, item.product_name \r\n"
    					+ "FROM (SELECT id, rating, COUNT(rating) AS count_rating FROM view_rated_match_item WHERE id=? GROUP BY id, rating) AS t1\r\n"
    					+ "JOIN\r\n"
    					+ "(SELECT id, AVG(rating) AS average_rating, COUNT(rating) AS total_count_rating FROM view_rated_match_item WHERE id=? GROUP BY id) t2\r\n"
    					+ "ON t1.id = t2.id\r\n" + "JOIN item\r\n" + "ON t1.id = item.id ORDER BY rating DESC";
            	PreparedStatement pstm_avg = conn.prepareStatement(get_average_rating_query);
            	pstm_avg.setString(1, item_id);
            	pstm_avg.setString(2, item_id);
                ResultSet rs_avg = pstm_avg.executeQuery();
                
                List<ratingCount> list_each_rating = new ArrayList<>();
                itemAverageRating item_average_rating = new itemAverageRating();
                int rating = 5;
                while (rating > 0) {
            		ratingCount rating_count = new ratingCount();
            		rating_count.setRating(rating);
            		rating_count.setCount_rating_percent(0.0);
            		rating--;
            		list_each_rating.add(rating_count);
                }
                           
                while (rs_avg.next()) {
                	String id = rs_avg.getString("id");
                	double average_rating = Misc.round(rs_avg.getDouble("average_rating"), 1);
                	int real_rating = rs_avg.getInt("rating");
                	int count_rating = rs_avg.getInt("count_rating");
                	int total_count_rating = rs_avg.getInt("total_count_rating");    
                	double count_rating_percent = Misc.round((double) count_rating / (double) total_count_rating * 100, 0);
                	
                	for (int j = 0; j < list_each_rating.size(); j++) {
                		if (real_rating == list_each_rating.get(j).getRating()) {
                			list_each_rating.get(j).setCount_rating_percent(count_rating_percent);
                		}
                	}          	
                	item_average_rating.setTotal_count_rating(total_count_rating);
                	item_average_rating.setId(id);
                	item_average_rating.setAverage_rating(average_rating);
                }
                item_average_rating.setList_each_rating(list_each_rating);
                
                list_recommended_item.get(i).setAll_avg_rating(item_average_rating);
            }
            
            request.setAttribute("listRecommendedItem", list_recommended_item);
            
        	request.getRequestDispatcher("homeItemBased.jsp").forward(request, response);
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
