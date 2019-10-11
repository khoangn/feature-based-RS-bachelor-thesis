package com.bachelor.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bachelor.myUtils.DatabaseConnector;

/**
 * Servlet implementation class ShowFeatureServlet
 */
@WebServlet("/ShowFeatureServlet")
public class ShowFeatureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowFeatureServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement pstm;
        try {
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	int id_feature_to_show = Integer.parseInt(request.getParameter("id-feature-to-show"));
        	int user_id = Integer.parseInt(request.getParameter("user-id-feature-to-show"));
        	System.out.println(id_feature_to_show);
        	pstm = conn.prepareStatement("DELETE FROM user_features WHERE active_user_id = ? AND feature_id = ?; "
        			+ "INSERT INTO user_features (active_user_id, feature_id) VALUES (?, ?);");
//        			+ "DELETE FROM recommended_feature WHERE active_user_id = ? AND feature_id = ? ");
        	pstm.setInt(1, user_id);
        	pstm.setInt(2, id_feature_to_show);
        	pstm.setInt(3, user_id);
        	pstm.setInt(4, id_feature_to_show);
        	pstm.executeUpdate();
        	response.sendRedirect(getServletContext().getContextPath()+"/home");
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
