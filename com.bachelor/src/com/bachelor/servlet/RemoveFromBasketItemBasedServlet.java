package com.bachelor.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bachelor.myUtils.DatabaseConnector;

@WebServlet("/RemoveFromBasketItemBasedServlet")
public class RemoveFromBasketItemBasedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RemoveFromBasketItemBasedServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DatabaseConnector.getConnection();
			String item_to_remove = request.getParameter("item-id");
			int active_user_id = (int) request.getSession().getAttribute("auid");
			
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM shopping_basket_item_based WHERE active_user_id = ? AND item_id = ?");
			pstmt.setInt(1, active_user_id);
			pstmt.setString(2, item_to_remove);
			pstmt.executeUpdate();
			
			response.sendRedirect(getServletContext().getContextPath()+"/home_item_based");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
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
