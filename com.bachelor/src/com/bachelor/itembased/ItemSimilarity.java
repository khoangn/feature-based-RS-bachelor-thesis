package com.bachelor.itembased;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.myUtils.Misc;
import com.bachelor.object.itemRating;
import com.bachelor.object.itemToBePredicted;

public class ItemSimilarity {
	public static void pairItems() throws SQLException {
		Connection conn = DatabaseConnector.getConnection();
		Statement stmt = conn.createStatement();
		PreparedStatement pstm;
		ResultSet rs;
		//LIST OF ALL MATCH ITEMS
		rs = stmt.executeQuery("SELECT DISTINCT id FROM view_rated_match_item ");
		List<String> list_item = new ArrayList<>();
		while (rs.next()) {
			String item_id = rs.getString("id");
			list_item.add(item_id);
		}
		System.out.println(list_item.size());


//				rs = stmt.executeQuery("SELECT t3.user_id, t4.avg AS average_rating FROM\r\n" + 
//						"(SELECT t1.user_id FROM (SELECT user_id FROM view_rated_match_item WHERE id = '" + list_item_other.get(i) + "') AS t1\r\n" + 
//						"JOIN (SELECT user_id FROM view_rated_match_item WHERE id = '" + list_item_active_user.get(j) + "') AS t2\r\n" + 
//						"ON t1.user_id = t2.user_id) AS t3\r\n" + 
//						"JOIN (SELECT user_id, AVG(rating), COUNT(rating) FROM view_rated_match_item GROUP BY user_id HAVING COUNT(rating) >= 2 ORDER BY COUNT(rating) DESC) AS t4\r\n" + 
//						"ON t3.user_id = t4.user_id");
		
		// PAIR ITEMS
		// ITEMS THAT ARE RATED BY AT LEAST 2 USERS
		String query = "SELECT * FROM\r\n"
				+ "(SELECT user_id FROM view_rated_match_item WHERE id::CHARACTER VARYING = ? \r\n"
				+ "AND user_id IN (SELECT user_id FROM view_rated_match_item WHERE id::CHARACTER VARYING = ?)) AS t1\r\n"
				+ "JOIN (SELECT user_id, AVG(rating), COUNT(rating) FROM view_rated_match_item GROUP BY user_id HAVING COUNT(rating) >= 2 ORDER BY COUNT(rating) DESC) AS t2\r\n"
				+ "ON t1.user_id = t2.user_id";
		pstm = conn.prepareStatement(query);
		for (int i = 0; i < list_item.size(); i++) {
			for (int j = 0; j < list_item.size(); j++) {
				if (!list_item.get(i).equals(list_item.get(j))) {
					List<String> list_user_id = new ArrayList<>();
					List<Double> list_average_rating = new ArrayList<>();
					pstm.setString(1, list_item.get(i));
					pstm.setString(2, list_item.get(j));
					rs = pstm.executeQuery();
					while (rs.next()) {
						String user_id = rs.getString("user_id");
						double average_rating = rs.getDouble("avg");
						
						String insert_item_pair = "INSERT INTO match_rated_item_pair (item_1, item_2, user_id, average_rating) VALUES (?,?,?,?)";
						PreparedStatement pstm2 = conn.prepareStatement(insert_item_pair);
						pstm2.setString(1, list_item.get(i));
						pstm2.setString(2, list_item.get(j));
						pstm2.setString(3, user_id);
						pstm2.setDouble(4, average_rating);
						pstm2.executeUpdate();
						
						list_user_id.add(user_id);
						list_average_rating.add(average_rating);
					}
					if (list_user_id.size() > 0) {
						System.out.println("ITEM 1: " + list_item.get(i));
						System.out.println("ITEM 2: " + list_item.get(j));
						System.out.println("LIST USER " + list_user_id);
						System.out.println("LIST AVG RATING " + list_average_rating);
						System.out.println("-----------");
					}
				}
			}
		}
	}
	
	public static void itemSimilarity(int active_user_id) throws SQLException {
		Connection conn = DatabaseConnector.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs;
		
		// LIST OF ALL ITEMS 1
		rs = stmt.executeQuery("SELECT DISTINCT item_1 FROM match_rated_item_pair");
		List<String> list_all_item_1 = new ArrayList<>();
		while(rs.next()) {
			String item_id = rs.getString("item_1");
			list_all_item_1.add(item_id);
		}
		System.out.println("# ALL ITEM 1: " + list_all_item_1.size()); 
		
		// LIST OF ALL ITEMS 2
		rs = stmt.executeQuery("SELECT DISTINCT item_2 FROM match_rated_item_pair");
		List<String> list_all_item_2 = new ArrayList<>();
		while(rs.next()) {
			String item_id = rs.getString("item_2");
			list_all_item_2.add(item_id);
		}
		System.out.println("# ALL ITEM 2: " + list_all_item_2.size());
		

		
		// SIMILARITY OF ALL ITEMS, PAIRWISE
		int n = 0;
		for (int i = 0; i < list_all_item_1.size(); i++) {
//		for (int i = 0; i < list_item_other.size(); i++) {
//			for (int j = 0; j < list_item_active_user.size(); j++) {
			for (int j = 0; j < list_all_item_2.size(); j++) {
				String query = "SELECT * FROM match_rated_item_pair WHERE item_1 = ? AND item_2 = ?";
				PreparedStatement pstm = conn.prepareStatement(query);
//				pstm.setString(1, list_item_other.get(i));
//				pstm.setString(2, list_item_active_user.get(j));
				pstm.setString(1, list_all_item_1.get(i));
				pstm.setString(2, list_all_item_2.get(j));
				rs = pstm.executeQuery();
				
				List<String> list_user = new ArrayList<>();
				List<Double> list_avg_rating = new ArrayList<>();
				while (rs.next()) {
					String user_id = rs.getString("user_id");
					double avg_rating = rs.getDouble("average_rating");
					list_user.add(user_id);
					list_avg_rating.add(avg_rating);
				}
				
				if (list_user.size() > 0) {
					
					List<Double> list_rating_minus_avg = new ArrayList<>();
					List<Double> list_sqrt1 = new ArrayList<>();
					List<Double> list_sqrt2 = new ArrayList<>();
					for (int k = 0; k < list_user.size(); k++) {
						double rating_item_1 = 0, rating_item_2 = 0;
						
						//RATING FOR ITEM 1
						PreparedStatement pstm1 = conn.prepareStatement("SELECT id, rating, user_id FROM view_rated_match_item WHERE user_id = ? AND id::CHARACTER VARYING = ?");
						pstm1.setString(1, list_user.get(k));
//						pstm1.setString(2, list_item_other.get(i));
						pstm1.setString(2, list_all_item_1.get(i));
						ResultSet rs1 = pstm1.executeQuery();
						while (rs1.next()) {
							rating_item_1 = rs1.getDouble("rating");
						}
						
						//RATING FOR ITEM 2
						PreparedStatement pstm2 = conn.prepareStatement("SELECT id, rating, user_id FROM view_rated_match_item WHERE user_id = ? AND id::CHARACTER VARYING = ?");
						pstm2.setString(1, list_user.get(k));
//						pstm2.setString(2, list_item_active_user.get(j));
						pstm2.setString(2, list_all_item_2.get(j));
						ResultSet rs2 = pstm2.executeQuery();
						while (rs2.next()) {
							rating_item_2 = rs2.getDouble("rating");
						}
						
						// ZAEHLER
						double rating_minus_avg = (rating_item_1 - list_avg_rating.get(k)) * (rating_item_2 - list_avg_rating.get(k));
//						double rating_minus_avg = (rating_item_1) * (rating_item_2);
						list_rating_minus_avg.add(rating_minus_avg);
						
						// NENNER
						double sqrt1 = Math.pow((rating_item_1 - list_avg_rating.get(k)), 2);
//						double sqrt1 = Math.pow((rating_item_1), 2);
						list_sqrt1.add(sqrt1);
						double sqrt2 = Math.pow((rating_item_2 - list_avg_rating.get(k)), 2);
//						double sqrt2 = Math.pow((rating_item_2), 2);
						list_sqrt2.add(sqrt2);
						
					}
					double zaehler = Misc.sum(list_rating_minus_avg);
					double nenner = Math.sqrt(Misc.sum(list_sqrt1)) * Math.sqrt(Misc.sum(list_sqrt2));
					double similarity = zaehler / nenner;
					
					n++;
					System.out.println(n);
					System.out.println("ITEM 1: " + list_all_item_1.get(i));
					System.out.println("ITEM 2: " + list_all_item_2.get(j));
					System.out.println("LIST USER " + list_user);
					System.out.println("LIST AVG RATING " + list_avg_rating);
					System.out.println("ZAEHLER " + zaehler);
					System.out.println("NENNER " + nenner);
					System.out.println("SIMILARITY " + similarity);
					System.out.println("-----------");
					
					String update_item_pair_similarity = "UPDATE match_rated_item_pair SET similarity = ? WHERE item_1 = ? AND item_2 = ?";
					PreparedStatement pstm3 = conn.prepareStatement(update_item_pair_similarity);
					pstm3.setDouble(1, similarity);
					pstm3.setString(2, list_all_item_1.get(i));
					pstm3.setString(3, list_all_item_2.get(j));
					pstm3.executeUpdate();
				}
			}
		}
	}
	public static void itemPrediction (int active_user_id) throws SQLException {
		Connection conn = DatabaseConnector.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs;
		PreparedStatement pstm;
		
		// LIST OF ITEMS ACTIVE USER HAS RATED
		String query_item_active_user = "SELECT * FROM user_item WHERE active_user_id = ? ORDER BY id ASC";
		pstm = conn.prepareStatement(query_item_active_user);
		pstm.setInt(1, active_user_id);
		rs = pstm.executeQuery();
		
		List<itemRating> list_item_active_user = new ArrayList<>();
		while(rs.next()) {
			String item_id = rs.getString("item_id");
			int rating = rs.getInt("rating");
			
			itemRating item = new itemRating();
			item.setId(item_id);
			item.setRating(rating);
			
			list_item_active_user.add(item);
		}
//		System.out.println("# ITEM ACTIVE USER: " + list_item_active_user.size());
		
		String query_item_to_be_predicted = "SELECT DISTINCT item_1 FROM match_rated_item_pair "
										+ "WHERE item_1 NOT IN (SELECT item_id FROM user_item WHERE active_user_id = ?) "
										+ "AND item_2 IN (SELECT item_id FROM user_item WHERE active_user_id = ?) AND similarity != 'NaN'" ;
		pstm = conn.prepareStatement(query_item_to_be_predicted);
		pstm.setInt(1, active_user_id);
		pstm.setInt(2, active_user_id);
		rs = pstm.executeQuery();
		List<String> list_item_other = new ArrayList<>();
		while (rs.next()) {
			String item_to_be_predicted = rs.getString("item_1");
			list_item_other.add(item_to_be_predicted);
		}
		
		String query_renew_predictions = "DELETE FROM item_based_recommended_items WHERE active_user_id = ?";
		pstm = conn.prepareStatement(query_renew_predictions);
		pstm.setInt(1, active_user_id);
		pstm.executeUpdate();
		
		String query_item_similarity = "SELECT DISTINCT item_1, rating, item_2, similarity FROM match_rated_item_pair, user_item\r\n"
									+ "WHERE active_user_id = ? AND item_1 = item_id AND item_2 = ?  AND similarity != 'NaN' ORDER BY item_1 DESC";

		for (int i = 0; i < list_item_other.size(); i++) {
			pstm = conn.prepareStatement(query_item_similarity);
			pstm.setInt(1, active_user_id);
			pstm.setString(2, list_item_other.get(i));
			rs = pstm.executeQuery();
			List<itemToBePredicted> list_item_to_be_predicted = new ArrayList<>();
			List<Double> list_similarity = new ArrayList<>();
			List<Double> list_rating = new ArrayList<>();
			while (rs.next()) {
				String item_active_user = rs.getString("item_1");
				double similarity_active_user = rs.getDouble("similarity");
				int rating_active_user = rs.getInt("rating");
				
				itemToBePredicted item_to_be_predicted = new itemToBePredicted();
				item_to_be_predicted.setId(item_active_user);
				item_to_be_predicted.setSimilarity(similarity_active_user);
				item_to_be_predicted.setActive_user_rating(rating_active_user);
				list_item_to_be_predicted.add(item_to_be_predicted);
				list_similarity.add(similarity_active_user);
				list_rating.add((double) rating_active_user);
			}
			
			List<Double> list_zaehler = new ArrayList<>();
			for (int j = 0; j < list_rating.size(); j++) {
				double zaehler = list_similarity.get(j) * list_rating.get(j);
				list_zaehler.add(zaehler);
			}
			double prediction = 0;
			if (Misc.sum(list_similarity) == 0) prediction = 0;
			else prediction = Misc.sum(list_zaehler) / Misc.sum(list_similarity);
			if (prediction < 0) prediction = 0;
			
//			if (prediction != 0)
//			System.out.println(list_item_other.get(i) + ": " + Misc.sum(list_zaehler) + " / " + Misc.sum(list_similarity) + " = " + prediction);
			
			String query_insert_predictions = "INSERT INTO item_based_recommended_items (active_user_id, item_id, predicted_rating) VALUES (?, ?, ?)";
			PreparedStatement pstm1 = conn.prepareStatement(query_insert_predictions);
			pstm1.setInt(1, active_user_id);
			pstm1.setString(2, list_item_other.get(i));
			pstm1.setDouble(3, prediction);
			pstm1.executeUpdate();
			System.out.println(list_item_other.get(i) + ": " + prediction);
		}
	}
}
