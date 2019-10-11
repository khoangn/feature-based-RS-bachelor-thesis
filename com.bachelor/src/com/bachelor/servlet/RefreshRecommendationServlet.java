package com.bachelor.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bachelor.itembased.ItemSimilarity;

@WebServlet("/RefreshRecommendationServlet")
public class RefreshRecommendationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RefreshRecommendationServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int active_user_id = (int) request.getSession().getAttribute("auid");
		try {
			ItemSimilarity.itemPrediction(active_user_id);
			response.sendRedirect(getServletContext().getContextPath()+"/home_item_based");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
