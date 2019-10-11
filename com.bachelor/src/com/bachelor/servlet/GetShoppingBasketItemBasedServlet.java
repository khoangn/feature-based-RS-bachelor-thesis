package com.bachelor.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
@WebServlet("/shopping_basket_item_based")
public class GetShoppingBasketItemBasedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetShoppingBasketItemBasedServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;

		try {
			Class.forName("org.postgresql.Driver");
			conn = DatabaseConnector.getConnection();
			Statement stmt = conn.createStatement();
			
			int active_user_id = (int) request.getSession().getAttribute("auid");
			
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM shopping_basket_item_based, item WHERE shopping_basket_item_based.item_id = item.id "
							+ "AND shopping_basket_item_based.active_user_id = ?");
			pstmt.setInt(1, active_user_id);

			ResultSet rs = pstmt.executeQuery();
			
			List<itemProfile> list_item_basket = new ArrayList<>();
			
			while (rs.next()) {
				String product_name = rs.getString("product_name");
				String img_src = rs.getString("img_src");
				int price = rs.getInt("avg_online_price");
				String id = rs.getString("item_id");
				
				itemProfile item = new itemProfile();
				item.setImg_src(img_src);
				item.setPrice(price);
				item.setProduct_name(product_name);
				item.setId(id);
				
				list_item_basket.add(item);
			}

			request.setAttribute("basketSize", list_item_basket.size());
			request.setAttribute("shoppingBasketItemBased", list_item_basket);
	    	request.getRequestDispatcher("shoppingBasketItemBased.jsp").forward(request, response);	

			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
