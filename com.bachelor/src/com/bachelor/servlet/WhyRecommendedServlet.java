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
import com.bachelor.object.recommendedItem;

/**
 * Servlet implementation class WhyRecommendedServlet
 */
@WebServlet("/WhyRecommendedServlet")
public class WhyRecommendedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public WhyRecommendedServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;

		try {
			Class.forName("org.postgresql.Driver");
			conn = DatabaseConnector.getConnection();
			Statement stmt = conn.createStatement();
			
			int auid = (int) request.getSession().getAttribute("auid");
//			Integer.parseInt(request.getParameter("auid"));
			
			List<recommendedItem> list_item = new ArrayList<>();
			String query_rated_item = "SELECT user_item.*, item.product_name, item.img_src FROM user_item, item WHERE item_id = item.id::CHARACTER VARYING AND active_user_id = ?";
			PreparedStatement pstm = conn.prepareStatement(query_rated_item);
			pstm.setInt(1, auid);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				String item_id = rs.getString("item_id");
				int rating = rs.getInt("rating");
				String item_name = rs.getString("product_name");
				String img_src = rs.getString("img_src");
				
				recommendedItem item = new recommendedItem();
				item.setId(item_id);
				item.setImg_src(img_src);
				item.setProduct_name(item_name);
				item.setRating(rating);
				list_item.add(item);
			}
			
			request.setAttribute("listrateditem", list_item);
			request.getRequestDispatcher("whyRecommended.jsp").forward(request, response);
			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
