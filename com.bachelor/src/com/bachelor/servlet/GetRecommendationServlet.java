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
import com.bachelor.object.recommendedItem;
import com.bachelor.usersimilarity.UserSimilarity;

@WebServlet("/GetRecommendationServlet")
public class GetRecommendationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetRecommendationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		ResultSet rs;
	
		try {
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	Statement stmt = conn.createStatement();
        	int user_id = Integer.parseInt(request.getParameter("user-id"));
        	
			UserSimilarity.ratingSimilarity(user_id);
			UserSimilarity.rating_prediction(user_id);
    		// RECOMMENDED CAMERAS BASED ON SIMILAR USERS
//    		rs = stmt.executeQuery("SELECT view_rated_match_item.id, product_name,  AVG(rating) FROM view_rated_match_item,\r\n" + 
//    				"(SELECT * FROM public.similar_user ORDER BY similarity DESC LIMIT 20) AS t1\r\n" + 
//    				"WHERE view_rated_match_item.user_id = t1.user_id\r\n" + 
//    				"GROUP BY product_name, view_rated_match_item.id\r\n" + 
//    				"ORDER BY AVG(rating) DESC");
//			List<recommendedItem> list_recommended_item = new ArrayList<>();
//			rs = stmt.executeQuery("SELECT DISTINCT view.id, product_name, avg_online_price, pixel_number, img_src FROM\r\n" + 
//					"(SELECT item.*, rated_match.amazon_item_id, rated_match.user_id, rated_match.rating, rated_match.timestamp\r\n" + 
//					"FROM item, (SELECT match_item.item_id, user_item_rating.* FROM match_item, user_item_rating WHERE match_item.amazon_item_id = user_item_rating.amazon_item_id) AS rated_match \r\n" + 
//					"WHERE item.id = rated_match.item_id) AS view,\r\n" + 
//					"(SELECT * FROM public.similar_user ORDER BY similarity DESC LIMIT 20) AS t1 WHERE view.user_id = t1.user_id LIMIT 8\r\n" + 
//					"");
//    		while (rs.next()) {
//    			String id = rs.getString("id");
//    			String product_name = rs.getString("product_name");
//    			int price = rs.getInt("avg_online_price");
//    			int pixel_number = rs.getInt("pixel_number");
//    			String img_src = rs.getString("img_src");
//    			recommendedItem item = new recommendedItem();
//    			item.setId(id);
//    			item.setImg_src(img_src);
//    			item.setProduct_name(product_name);
//    			item.setPrice(price);
//    			item.setPixel_number(pixel_number);
//    			list_recommended_item.add(item);
//    		}	
//		
//    	request.setAttribute("active-user-id", user_id);	
//    	request.setAttribute("listRecommendedItem", list_recommended_item);	
    	response.sendRedirect(getServletContext().getContextPath()+"/home");	
//    	request.getRequestDispatcher("recommendedItem.jsp").forward(request, response);	
    	conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
