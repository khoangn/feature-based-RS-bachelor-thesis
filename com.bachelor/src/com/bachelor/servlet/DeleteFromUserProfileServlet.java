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

@WebServlet("/DeleteFromUserProfileServlet")
public class DeleteFromUserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteFromUserProfileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DatabaseConnector.getConnection();
			String string_feature_to_delete = request.getParameter("feature-to-delete");
			System.out.println("delete " + string_feature_to_delete);
			int feature_to_delete = 0;
			if (string_feature_to_delete != null) {
				feature_to_delete = Integer.parseInt(string_feature_to_delete);
				if (feature_to_delete != 0) {
					PreparedStatement pstmt_delete_feature = conn.prepareStatement("DELETE FROM user_feature_values WHERE id = ?;"
							+ "DELETE FROM recently_added WHERE user_feature_values_id = ?");
					pstmt_delete_feature.setInt(1, feature_to_delete);
					pstmt_delete_feature.setInt(2, feature_to_delete);
					pstmt_delete_feature.executeUpdate();
				}
			}
			response.sendRedirect(getServletContext().getContextPath()+"/user_profile");
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}