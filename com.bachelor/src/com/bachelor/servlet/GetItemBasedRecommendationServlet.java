package com.bachelor.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bachelor.itembased.ItemSimilarity;
import com.bachelor.myUtils.DatabaseConnector;

/**
 * Servlet implementation class GetItemBasedRecommendationServlet
 */
@WebServlet("/GetItemBasedRecommendationServlet")
public class GetItemBasedRecommendationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetItemBasedRecommendationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		try {
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	int active_user_id = Integer.parseInt(request.getParameter("user-id"));
        	ItemSimilarity.itemPrediction(active_user_id);
        	
        	response.sendRedirect(getServletContext().getContextPath()+"/home_item_based");	
        	conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
