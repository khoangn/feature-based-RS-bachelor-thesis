package com.bachelor.itembased;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.myUtils.Misc;
import com.bachelor.object.itemRating;

public class ItemToDisplay {
	public static void selectItemToDisplay() throws SQLException {
		Connection conn = DatabaseConnector.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs;
		
		List<itemRating> list_item = new ArrayList<>();
		//ITEM POPULARITY
		rs = stmt.executeQuery("SELECT id, product_name, COUNT(rating) AS count_rating FROM view_rated_match_item GROUP BY id, product_name ORDER BY count_rating DESC");
		while (rs.next()) {
			String item_id = rs.getString("id");
			int count_rating = rs.getInt("count_rating");
			itemRating item = new itemRating();
			item.setId(item_id);
			item.setCount_rating(count_rating);
			list_item.add(item);
		}
		
		//ITEM ENTROPY
		for (int i = 0; i < list_item.size(); i++) {
			List<Double> list_count_rating = new ArrayList<>();
			rs = stmt.executeQuery("SELECT COUNT(product_name) AS count_each_rating, rating FROM view_rated_match_item "
					+ "WHERE id ='" + list_item.get(i).getId() +"' GROUP BY rating ORDER BY rating ASC");
			while(rs.next()) {
				double count_each_rating = rs.getInt("count_each_rating");
				list_count_rating.add(count_each_rating);
				
			}
			list_item.get(i).setList_count_rating(list_count_rating);
		}
		
		//ITEM RANKING
		stmt.executeUpdate("TRUNCATE TABLE item_rank");
		for (int i = 0; i < list_item.size(); i++) {
			double item_rank;
			if (Misc.entropy(list_item.get(i).getList_count_rating(), list_item.get(i).getCount_rating()) == 0.0) item_rank = 0;
			else item_rank = Math.log(list_item.get(i).getCount_rating()) * Misc.entropy(list_item.get(i).getList_count_rating(), list_item.get(i).getCount_rating());
			list_item.get(i).setItem_rank(item_rank);
			System.out.println(list_item.get(i).getId() + ": " + list_item.get(i).getCount_rating() + " || " + Math.log(list_item.get(i).getCount_rating()) + " || " + Misc.entropy(list_item.get(i).getList_count_rating(), list_item.get(i).getCount_rating()) + " || " + list_item.get(i).getItem_rank());
			//INSERT TO DB
			PreparedStatement pstm = conn.prepareStatement("INSERT INTO item_rank (item_id, item_rank) VALUES (?, ?)");
			pstm.setString(1, list_item.get(i).getId());
			pstm.setDouble(2, item_rank);
			pstm.execute();
		}
	}
}
