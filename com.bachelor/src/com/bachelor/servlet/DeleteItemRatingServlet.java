package com.bachelor.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import com.bachelor.object.appUser;

@WebServlet("/DeleteItemRatingServlet")
public class DeleteItemRatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteItemRatingServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		try {
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	
        	String item_to_delete = request.getParameter("item-to-delete");
            appUser app_user = (appUser) request.getSession().getAttribute("activeAppUser");
            int dbId = app_user.getId();
        	
        	PreparedStatement pstm = conn.prepareStatement("DELETE FROM user_item WHERE active_user_id = ? AND item_id = ?");
        	pstm.setInt(1, dbId);
        	pstm.setString(2, item_to_delete);
        	pstm.executeUpdate();
        	
        	ItemSimilarity.itemPrediction(dbId);
        	
        	response.sendRedirect(getServletContext().getContextPath()+"/home_item_based");	
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
