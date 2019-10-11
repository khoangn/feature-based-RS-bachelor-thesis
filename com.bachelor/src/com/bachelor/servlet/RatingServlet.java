package com.bachelor.servlet;

import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.myUtils.dbUtils;
import com.bachelor.object.userItemRating;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/rating")
public class RatingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RatingServlet() {
        super();
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*        Connection conn = DatabaseConnector.getConnection();
        Statement stmt = null;
        ResultSet rs;
        String userid = request.getParameter("userid");

        List<userItemRating> listUserItemRating = null;
        String sql = "SELECT * FROM user_item_rating WHERE user_id = '" + userid + "'";
        try {
            listUserItemRating = dbUtils.findUserItemRating(conn, userid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        response.sendRedirect(request.getContextPath() + "/ratings");*/
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //String userid = "A13NNS3WZLE7FK";
        //String userid = request.getParameter("userid");

        //List<userItemRating> list = null;
    	Connection conn = null;
        List<userItemRating> list = new ArrayList<userItemRating>();
        String userId = request.getParameter("userid");
        try {
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	String sql = "SELECT id, user_id, amazon_item_id, rating FROM user_item_rating WHERE user_id = ? ";
        	PreparedStatement pstm = conn.prepareStatement(sql);
        	pstm.setString(1, userId);
        	ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String amazonId = rs.getString("amazon_item_id");
                double rating = rs.getDouble("rating");

                userItemRating uir = new userItemRating();
                uir.setUser_id(userId);
                uir.setAmazon_item_id(amazonId);
                uir.setRating(rating);
                list.add(uir);
            }
        } catch (SQLException | ClassNotFoundException e) {
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

        //request.setAttribute("anzahl", "25");
        	request.getSession().setAttribute("listUserItemRating", list);
        	request.getRequestDispatcher("ratings.jsp").forward(request, response);
    }
}
