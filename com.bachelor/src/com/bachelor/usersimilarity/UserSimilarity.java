package com.bachelor.usersimilarity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.myUtils.Misc;
import com.bachelor.object.activeUserProfile;
import com.bachelor.object.feature;
import com.bachelor.object.userU;
import com.google.gson.Gson;

public class UserSimilarity {
	public static List<String> getUserAmazonId() throws SQLException {
		List<String> list_user_amazon_id = new ArrayList<String>();

		Connection conn = DatabaseConnector.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs;
		rs = stmt.executeQuery("SELECT user_id FROM feature_weight ORDER BY id ASC");
		while (rs.next()) {
			String user_amazon_id = rs.getString("user_id");
			list_user_amazon_id.add(user_amazon_id);
		}
		return list_user_amazon_id;
	}

	public static List<userU> getUserU() throws SQLException {
		Connection conn = DatabaseConnector.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs;
		
		// all feature: 58 qualitative + 19 quantitative
		List<String> all_feature = new ArrayList<>();
		rs = stmt.executeQuery("SELECT field_name FROM features WHERE field_name != 'online_publication'");
		while (rs.next()) {
			String field_name = rs.getString("field_name");
			all_feature.add(field_name);
		}
		
		// FOR EACH USER U: ID, LIST OF FEATURES, LIST OF FEATURE WEIGHTS, LIST OF RATED ITEMS
		List<userU> all_user_u = new ArrayList<>();
		rs = stmt.executeQuery("SELECT * FROM feature_weight ORDER BY id ASC");
		while (rs.next()) {
			String user_amazon_id = rs.getString("user_id");
			List<Double> list_feature_weight = new ArrayList<>();
			userU user_u = new userU();
			for (int j = 0; j < all_feature.size(); j++) {
				double feature_weight = rs.getDouble(all_feature.get(j));
				list_feature_weight.add(feature_weight);
			}
			user_u.setAmazon_id(user_amazon_id);
			user_u.setAll_feature(all_feature);
			user_u.setAll_feature_weight(list_feature_weight);
			List<String> list_item_id = new ArrayList<>();
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery("SELECT id FROM view_rated_match_item WHERE user_id = '" + user_amazon_id + "' ORDER BY id ASC");
			while (rs1.next()) {
				String item_id = rs1.getString("id");
				list_item_id.add(item_id);
			}
			user_u.setAll_item_id(list_item_id);
			all_user_u.add(user_u);
		}
		conn.close();
		return all_user_u;
	}
	
	public static void ratingSimilarity(int user_id) throws SQLException {
		List<userU> all_user_u = getUserU();
		activeUserProfile active_user = new activeUserProfile();
		List<String> all_active_user_feature = new ArrayList<>();
		List<String> all_active_user_feature_type = new ArrayList<>();
		List<Double> all_active_user_rating = new ArrayList<>();
		
		List<List<Double>> all_user_u_weight = new ArrayList<>();
		
		Connection conn = DatabaseConnector.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs;
		
		rs = stmt.executeQuery("SELECT field_name, rating, feature_type FROM user_feature_values, features "
				+ "WHERE user_feature_values.feature_id = features.id AND active_user_id ='" + user_id + "'");
		while (rs.next()) {
			String field_name = rs.getString("field_name");
			String feature_type = rs.getString("feature_type");
			double rating = (double) rs.getInt("rating");
			all_active_user_feature.add(field_name);
			all_active_user_feature_type.add(feature_type);
			all_active_user_rating.add(rating);
		}		
		
		List<String> active_user_quantitative_features = new ArrayList<>();
		List<Double> active_user_quantitative_ratings = new ArrayList<>();
		List<String> active_user_qualitative_features = new ArrayList<>();
		List<Double> active_user_qualitative_ratings = new ArrayList<>();
		for (int i = 0; i < all_active_user_feature.size(); i++) {			
			if (all_active_user_feature_type.get(i).equals("TEXT")) {
				active_user_qualitative_features.add(all_active_user_feature.get(i));
				active_user_qualitative_ratings.add(all_active_user_rating.get(i));
			} else {
				active_user_quantitative_features.add(all_active_user_feature.get(i));
				active_user_quantitative_ratings.add(all_active_user_rating.get(i));
			}
		}
		System.out.println(active_user_qualitative_features);
		System.out.println(active_user_qualitative_ratings);
		System.out.println(active_user_quantitative_features);
		System.out.println(active_user_quantitative_ratings);
		
//		// FILTER DUPLICATE QUALITATIVE
//		List<String> distinct_active_user_qualitative_features = new ArrayList<>();
//		Map<String, List<Integer>> indexes_qualitative = new HashMap<>();
//		List<Integer> dup_indexes_qualitative = new ArrayList<>();
//	
//		for (int i = 0; i < active_user_qualitative_features.size(); i++) {			
//			if (Collections.frequency(active_user_qualitative_features, active_user_qualitative_features.get(i)) > 1) {			
////				List<Integer> indexList = indexes.get(all_active_user_feature.get(i));
////				if (indexList == null) indexes.put(all_active_user_feature.get(i), indexList = new ArrayList<>());
////				indexList.add(i);
//				indexes_qualitative.computeIfAbsent(active_user_qualitative_features.get(i), c -> new ArrayList<>()).add(i);	
//				dup_indexes_qualitative = indexes_qualitative.get(active_user_qualitative_features.get(i));
//			}			
//		}
////		System.out.println(indexes_qualitative);
//
//		List<Double> dup_rating_qualitative = new ArrayList<>();
//		for (int i = 0; i < dup_indexes_qualitative.size(); i++) {
//			dup_rating_qualitative.add(active_user_qualitative_ratings.get(dup_indexes_qualitative.get(i)));
//		}
//		
//		distinct_active_user_qualitative_features =  active_user_qualitative_features.stream().distinct().collect(Collectors.toList());
//		if (distinct_active_user_qualitative_features.size() < active_user_qualitative_features.size()) {
//			active_user_qualitative_ratings.set(Collections.min(dup_indexes_qualitative), Misc.mean(dup_rating_qualitative));
//			for (int i = 0; i < dup_indexes_qualitative.size(); i++) {
//				int dup_in = dup_indexes_qualitative.get(i);
//				if (dup_in != Collections.min(dup_indexes_qualitative)) active_user_qualitative_ratings.remove(dup_in);
//			}		
//		}
//		
//		
//		
//		
//		Map<String, List<Integer>> indexes_quantitative = new HashMap<>();
//		List<Integer> dup_indexes_quantitative = new ArrayList<>();
//	
//		for (int i = 0; i < active_user_quantitative_features.size(); i++) {			
//			if (Collections.frequency(active_user_quantitative_features, active_user_quantitative_features.get(i)) > 1) {			
////				List<Integer> indexList = indexes.get(all_active_user_feature.get(i));
////				if (indexList == null) indexes.put(all_active_user_feature.get(i), indexList = new ArrayList<>());
////				indexList.add(i);
//				indexes_quantitative.computeIfAbsent(active_user_quantitative_features.get(i), c -> new ArrayList<>()).add(i);	
//				dup_indexes_quantitative = indexes_quantitative.get(active_user_quantitative_features.get(i));
//			}			
//		}
////		System.out.println(indexes_qualitative);
//
//		List<Double> dup_rating_quantitative = new ArrayList<>();
//		for (int i = 0; i < dup_indexes_quantitative.size(); i++) {
//			dup_rating_quantitative.add(active_user_quantitative_ratings.get(dup_indexes_quantitative.get(i)));
//		}
//		
//		if (new_active_user_quantitative_features.size() < active_user_quantitative_features.size()) {
//			active_user_quantitative_ratings.set(Collections.min(dup_indexes_quantitative), Misc.mean(dup_rating_quantitative));
//			for (int i = 0; i < dup_indexes_quantitative.size(); i++) {
//				int dup_in = dup_indexes_quantitative.get(i);
//				if (dup_in != Collections.min(dup_indexes_quantitative)) active_user_quantitative_ratings.remove(dup_in);
//			}		
//		}
		
		
		
		//SET ACTIVE USER
		active_user.setAll_feature_quantitative(active_user_quantitative_features);
		active_user.setAll_rating_quantitative(active_user_quantitative_ratings);
		active_user.setAll_feature_qualitative(active_user_qualitative_features);
		active_user.setAll_rating_qualitative(active_user_qualitative_ratings);
		
		
		System.out.println("ACTIVE USER QUALI: " + active_user.getAll_feature_qualitative());
		System.out.println("ACTIVE USER QUALI: " + active_user.getAll_rating_qualitative());
		System.out.println("ACTIVE USER QUANTI: " + active_user.getAll_feature_quantitative());
		System.out.println("ACTIVE USER QUANTI: " + active_user.getAll_rating_quantitative());
		
		List<String> new_all_active_user_feature = new ArrayList<>();
		new_all_active_user_feature.addAll(active_user_quantitative_features);
		new_all_active_user_feature.addAll(active_user_qualitative_features);
		
		List<Double> new_all_active_user_rating = new ArrayList<>();
		new_all_active_user_rating.addAll(active_user_quantitative_ratings);
		new_all_active_user_rating.addAll(active_user_qualitative_ratings);
		
		System.out.println("ALL FEATURES FINAL " + new_all_active_user_feature);
		System.out.println("ALL RATINGS FINAL " + new_all_active_user_rating);
		
//		active_user.setAll_feature(new_all_active_user_feature);
//		active_user.setAll_feature_type(new_all_active_user_feature_type);
//		active_user.setAll_feature_rating(all_active_user_rating);

		
		for (int i = 0; i < all_user_u.size(); i++ ) {
			List<Double> list_user_u_weight = new ArrayList<>();
			for (int j = 0; j < new_all_active_user_feature.size(); j++) { //A12DQZKRKTNF5E
				rs = stmt.executeQuery("SELECT " + new_all_active_user_feature.get(j) + " FROM feature_weight WHERE user_id = '" + all_user_u.get(i).getAmazon_id() + "'");
//				rs = stmt.executeQuery("SELECT " + new_all_active_user_feature.get(j) + " FROM feature_weight WHERE user_id = 'A12DQZKRKTNF5E'");

				while (rs.next()) {
					double user_u_weight = rs.getDouble(1);
					list_user_u_weight.add(user_u_weight);
				}
				
				
//				for (int k = 0; k < new_all_active_user_feature.size(); k++) {
//					if (new_all_active_user_feature.get(k).equals(all_user_u.get(i).getAll_feature().get(j))) {
//						user_u_weight.add(all_user_u.get(i).getAll_feature_weight().get(j));
//					}
//				}
			}
//			System.out.println("USER U WEIGHT: " + list_user_u_weight);
			all_user_u_weight.add(list_user_u_weight);
		}
		//System.out.println(all_active_user_feature);
		
		List<Double> standardization_active_user_rating = Misc.standardization(new_all_active_user_rating);
//		List<Double> standardization_active_user_rating = new_all_active_user_rating;
		
//		System.out.println("MEAN: " + Misc.mean(new_all_active_user_rating));
//		System.out.println("STANDARDIZED RATINGS: " + standardization_active_user_rating);
		List<Double> all_rating_similarity = new ArrayList<>();
		for (int i = 0; i < all_user_u.size(); i++) {
			List<Double> all_user_u_rating = new ArrayList<>();
			for(int j = 0; j < new_all_active_user_rating.size(); j++) {
				double user_u_rating = all_user_u_weight.get(i).get(j) * (5-1)+1;
				all_user_u_rating.add(user_u_rating);
			}
			
			List<Double> standardization_weight = Misc.standardization(all_user_u_weight.get(i));
//			List<Double> standardization_weight = all_user_u_weight.get(i);
			
//			double rating_similarity = Misc.eucldSimilarity(standardization_active_user_rating, standardization_weight);
//			double rating_similarity = Misc.pearson_correlation(standardization_active_user_rating, standardization_weight, new_all_active_user_rating.size());

			
			double rating_similarity = Misc.eucldSimilarity(Misc.normalize(new_all_active_user_rating), all_user_u_weight.get(i));
//			double rating_similarity = Misc.eucldSimilarity(new_all_active_user_rating, Misc.convertToFiveStar(all_user_u_weight.get(i)));
			
			
//			all_sim_distance.add(sim_distance);
			
			all_rating_similarity.add(rating_similarity);
			all_user_u.get(i).setRating_similarity(rating_similarity);
			//System.out.println(standardization_weight);
		}
//ALL RATING SIMILARITY: [0.08339420023236203, 0.08369713910892902, 0.08387495196456225, 0.08306570274785652, 0.08362312744943995, 0.08121030314161228, 0.08344190362806946, 0.08348251196189796, 0.0841169426125426, 0.08401048509338181, 0.08376702505661598, 0.08121030314161228, 0.0832617087275441, 0.08121030314161228, 0.08121030314161228, 0.0829348315466476, 0.08331431561974159, 0.08415943510868344, 0.08375864624463798, 0.08342549653925299, 0.0836862419500405, 0.08340792370915387, 0.08350830172394866, 0.08326769900287173, 0.08383398222049557, 0.08121030314161228, 0.08290383469308626, 0.08121030314161228, 0.0834917396786665, 0.0830499381473637, 0.08374770141685613, 0.08346656639502816, 0.08329677736052851, 0.08311413501037147, 0.08121030314161228, 0.08322586575348233, 0.08121030314161228, 0.08121030314161228, 0.0845279353511493, 0.08341077817423134, 0.08393534325107697, 0.08371143047708772, 0.0838040926162694, 0.08366594464938366, 0.08349268985297068, 0.08121030314161228, 0.08358973018335794, 0.08121030314161228, 0.08347840811744314, 0.08121030314161228, 0.08381845383023687, 0.08121030314161228, 0.08407061386900092]

		System.out.println("ALL RATING SIMILARITY: " + all_rating_similarity);
		
//		List<Double> normalized = new ArrayList<>();
//		for (int i = 0; i < all_sim_distance.size(); i++) {
//			double x = (all_sim_distance.get(i) - Collections.min(all_sim_distance)) / (Collections.max(all_sim_distance) - Collections.min(all_sim_distance));
//			normalized.add(x);
//		}
//		System.out.println("1: " + normalized);
//		System.out.println("2: " + Misc.normalize(all_sim_distance));
		
		
////////QUANTITATIVE SIMILARITES //////////////////////////////////////		
		List<Double> list_avg = new ArrayList<>();
		List<Double> list_min = new ArrayList<>();
		List<Double> list_max = new ArrayList<>();
		for (int i = 0; i < active_user_quantitative_features.size(); i++) {
			rs = stmt.executeQuery("SELECT min, max FROM user_feature_values WHERE active_user_id = '" + user_id +  "' "
					+ "AND feature_id = (SELECT id FROM features WHERE field_name = '" + active_user_quantitative_features.get(i) + "')");
			while (rs.next()) {
				double min = (double) rs.getInt("min");
				double max = (double) rs.getInt("max");
				double avg = (min + max) / 2;
				
				list_min.add(min);
				list_max.add(max);
				list_avg.add(avg);
			}
		}
//		System.out.println("LIST AVG: " + list_avg);
//		
//		System.out.println(active_user_quantitative_features);
//		System.out.println(active_user_qualitative_features);	
		for (int i = 0; i < all_user_u.size(); i++) {
			all_user_u.get(i).setList_quantitative(active_user_quantitative_features);
			all_user_u.get(i).setList_qualitative(active_user.getAll_feature_qualitative());
			
			List<List<Double>> list_all_user_u_quantitative_value = new ArrayList<>();
			List<List<String>> list_all_user_u_qualitative_value = new ArrayList<>();
			List<Double> list_similarity_quantitative = new ArrayList<>();
			List<Double> list_user_u_similarity_quantitative = new ArrayList<>();
			
			
			rs = stmt.executeQuery("SELECT * FROM view_rated_match_item WHERE user_id ='" + all_user_u.get(i).getAmazon_id() + "' ORDER BY id ASC");
			while (rs.next()) {
				List<Double> list_user_u_quantitative_value = new ArrayList<>();
				List<String> list_user_u_qualitative_value = new ArrayList<>();
				
				for (int j = 0; j < active_user_quantitative_features.size(); j++) {
					double user_u_quantitative = rs.getDouble(active_user_quantitative_features.get(j));
					list_user_u_quantitative_value.add(user_u_quantitative);
				}
				list_all_user_u_quantitative_value.add(list_user_u_quantitative_value);
//				System.out.println(all_user_u.get(i).getAmazon_id() + " UU QUANTI: " + list_all_user_u_quantitative_value);
				
				
				list_similarity_quantitative = Misc.quantitativeSimilarity(list_min, list_max, list_user_u_quantitative_value);
				double similarity_quantitative = Misc.mean(list_similarity_quantitative);
				list_user_u_similarity_quantitative.add(similarity_quantitative);
				
//				double similarity_quantitative = Misc.eucldSimilarity(Misc.standardization(list_avg), Misc.standardization(list_user_u_quantitative_value));
//				list_similarity_quantitative.add(similarity_quantitative);
//				System.out.println(all_user_u.get(i).getAmazon_id() + " QUALI EACH " + list_similarity_quantitative);
//				System.out.println("QUANTI SIMILARITY: " + list_similarity_quantitative);
				
				for (int k = 0; k < active_user.getAll_feature_qualitative().size(); k++) {
					String user_u_qualitative = rs.getString(all_user_u.get(i).getList_qualitative().get(k));
					list_user_u_qualitative_value.add(user_u_qualitative);
				}
				list_all_user_u_qualitative_value.add(list_user_u_qualitative_value);				
			}
			all_user_u.get(i).setList_quantitative_value(list_all_user_u_quantitative_value);
			all_user_u.get(i).setList_similarity_quantitative(list_user_u_similarity_quantitative);			
			all_user_u.get(i).setList_qualitative_value(list_all_user_u_qualitative_value);
			
//			System.out.println(all_user_u.get(i).getAmazon_id() + " | " + all_user_u.get(i).getList_qualitative()  + " | " + all_user_u.get(i).getList_qualitative_value());
		}

		
//////// QUALITATIVE SIMILARITIES ////////////////////////////////////////////
		for (int i = 0; i < all_user_u.size(); i++) {
			Statement stmt1 = conn.createStatement();
			stmt1.executeUpdate("UPDATE user_feature_values SET user_u_qualitative = NULL, user_u_id = NULL ;");
			List<Double> list_similarity_qualitative = new ArrayList<>();
			for (int j = 0; j < all_user_u.get(i).getList_qualitative_value().size(); j++) {
				for (int k = 0; k < all_user_u.get(i).getList_qualitative_value().get(j).size(); k++) {
//					System.out.println(all_user_u.get(i).getList_qualitative_value().get(j).get(k));
					Statement stmt2 = conn.createStatement();
					stmt2.executeUpdate("UPDATE user_feature_values SET user_u_qualitative = '" + all_user_u.get(i).getList_qualitative_value().get(j).get(k) + "', user_u_id = '" + all_user_u.get(i).getAmazon_id() + "' "
							+ "WHERE active_user_id = '" + user_id + "' "
							+ "AND feature_id = (SELECT id FROM features WHERE field_name = '" + all_user_u.get(i).getList_qualitative().get(k) + "')");
				}
				
				// CO OCCURRENCE AU UU
				List<Double> count_coocc_AU_UU = new ArrayList<>();				
				rs = stmt.executeQuery("SELECT t1.user_u_qualitative, t2.value, COUNT(t3.user_u_qualitative)\r\n" + 
						"FROM (SELECT DISTINCT user_u_qualitative FROM user_feature_values WHERE NOT user_u_qualitative = '') AS t1\r\n" + 
						"CROSS JOIN (SELECT DISTINCT value FROM user_feature_values WHERE NOT value = '' AND NOT user_u_qualitative = '') AS t2\r\n" + 
						"LEFT JOIN user_feature_values AS t3 ON t3.user_u_qualitative = t1.user_u_qualitative AND t3.value = t2.value\r\n" + 
						"GROUP BY t1.user_u_qualitative, t2.value ORDER BY t1.user_u_qualitative ASC ,t2.value ASC");
				while (rs.next()) {
					count_coocc_AU_UU.add(rs.getDouble("count"));
				}
				
				// DISTINCT FEATURE VALUES OF AU & FREQUENCY OF EACH --- Y ---
		        rs = stmt.executeQuery("SELECT value, COUNT(*) FROM user_feature_values WHERE NOT value = '' AND NOT user_u_qualitative = '' GROUP BY value ORDER BY value ASC");
		        List<String> distinct_featureValue_AU = new ArrayList<>();
		        List<Double> count_distinct_featureValue_AU = new ArrayList<>();
		        while (rs.next()) {
		            distinct_featureValue_AU.add(rs.getString("value"));
		            count_distinct_featureValue_AU.add(rs.getDouble(2));
		        }
				
				// DISTINCT FEATURE VALUES OF UU & FREQUENCY OF EACH --- X ---
		        rs = stmt.executeQuery("SELECT user_u_qualitative, COUNT(*) FROM user_feature_values WHERE NOT user_u_qualitative = '' GROUP BY user_u_qualitative ORDER BY user_u_qualitative ASC");
		        List<String> distinct_featureValue_UU = new ArrayList<>();
		        List<Double> count_distinct_featureValue_UU = new ArrayList<>();
		        while (rs.next()) {
		            distinct_featureValue_UU.add(rs.getString("user_u_qualitative"));
		            count_distinct_featureValue_UU.add(rs.getDouble(2));
		        }
		        
		        // NUMBER OF CONSIDERED VALUES
		        double n = 0;
		        rs = stmt.executeQuery("SELECT COUNT(user_u_qualitative) FROM user_feature_values WHERE NOT user_u_qualitative = ''");
		        while (rs.next()) {
		        	n = rs.getDouble(1);
		        }
		        
		        // NUMBER OF DISTINCT VALUE OF AU
		        double number_distinct_AU = 0;
		        rs = stmt.executeQuery("SELECT COUNT(DISTINCT value) FROM user_feature_values WHERE NOT value = '' AND NOT user_u_qualitative = ''");
		        while (rs.next()) {
		        	number_distinct_AU = rs.getDouble(1);
		        }
		        
		        // NUMBER OF DISTINCT VALUE OF UU
		        double number_distinct_UU = 0;
		        rs = stmt.executeQuery("SELECT COUNT(DISTINCT user_u_qualitative) FROM user_feature_values WHERE NOT user_u_qualitative = ''");
		        while (rs.next()) {
		        	number_distinct_UU = rs.getDouble(1);
		        }
		        
		        double similarity_qualitative = 0;
		        if (count_distinct_featureValue_AU.size() == 0) {
		        	similarity_qualitative = 0;
//		        	System.out.println("FALL 1");
		        } else if (count_distinct_featureValue_AU.size() == 1) {
//		        	System.out.println("FALL 2");
		        	if (count_distinct_featureValue_AU.get(0).equals(count_distinct_featureValue_UU.get(0))) similarity_qualitative = 1;
		        	else similarity_qualitative = 0;
		        } else {
//		        	System.out.println("FALL 3");
			        similarity_qualitative = Misc.cramer_contingency(count_distinct_featureValue_UU, count_distinct_featureValue_AU, count_coocc_AU_UU, n, number_distinct_UU, number_distinct_AU);
		        }
		        if (Double.isNaN(similarity_qualitative)) similarity_qualitative = 0;
//		        else similarity_qualitative = Misc.cramer_contingency(count_distinct_featureValue_UU, count_distinct_featureValue_AU, count_coocc_AU_UU, n, number_distinct_UU, number_distinct_AU);
		        list_similarity_qualitative.add(similarity_qualitative);
//		        System.out.println("Cramer's V: " + list_similarity_qualitative);
			}
//			System.out.println("---------" + all_user_u.get(i).getAmazon_id() + " QUALI SIMILARITY: " + list_similarity_qualitative);
			all_user_u.get(i).setList_similarity_qualitative(list_similarity_qualitative);
		}
//		System.out.println(all_user_u.get(0).getList_similarity_qualitative());
		List <Double> list_overall_similarity = new ArrayList<>();
		List <Double> similarity = new ArrayList<>();
		List <String> similar_user = new ArrayList<>();
//		stmt.executeUpdate("TRUNCATE TABLE similar_user; ALTER SEQUENCE similar_user_id_seq RESTART;");
		stmt.executeUpdate("DELETE FROM similar_user WHERE active_user_id = '" + user_id + "'");
		for (int i = 0; i < all_user_u.size(); i++) {
			List<Double> list_overall = new ArrayList<>();
			double overall_similarity = 0;
			
			double qualitative_similarity = Misc.mean(all_user_u.get(i).getList_similarity_qualitative());
			double quantitative_similarity = Misc.mean(all_user_u.get(i).getList_similarity_quantitative());
			double rating_similarity = all_user_u.get(i).getRating_similarity();
			
					
			if (active_user.getAll_feature_qualitative().size() == 0) { 
				overall_similarity = (quantitative_similarity + rating_similarity) / 2;
			} else if (active_user.getAll_feature_quantitative().size() == 0) {
				overall_similarity = (qualitative_similarity + rating_similarity) / 2;
			} else {
//				overall_similarity = ((qualitative_similarity + quantitative_similarity) / 2 + rating_similarity) / 2;
				overall_similarity = ((qualitative_similarity + quantitative_similarity) / 2) * rating_similarity;
			}
			
//			System.out.println(all_user_u.get(i).getAmazon_id() + " - RATING SIMILARITY : " + rating_similarity);
//			System.out.println(all_user_u.get(i).getAmazon_id() + " - QUALI SIMILARITY : " + qualitative_similarity);
//			System.out.println(all_user_u.get(i).getAmazon_id() + " - QUANTI SIMILARITY : " + quantitative_similarity);
			System.out.println(all_user_u.get(i).getAmazon_id() + " - OVERALL SIMILARITY: " + overall_similarity);
			
//			System.out.println("---QUALI " + qualitative_similarity);		
//			System.out.println("---QUANTI " + quantitative_similarity);
//			System.out.println("---RATING " + all_user_u.get(i).getRating_similarity());
//			System.out.println("---OVERALL " + overall_similarity);		
			
			
//			for (int j = 0; j < all_user_u.get(i).getList_similarity_qualitative().size(); j++) {
//				double overall_similarity = (all_user_u.get(i).getRating_similarity() + all_user_u.get(i).getList_similarity_qualitative().get(j) + all_user_u.get(i).getList_similarity_quantitative().get(j)) / 3 ;
////				double overall_similarity = all_user_u.get(i).getList_similarity_qualitative().get(j) + all_user_u.get(i).getList_similarity_quantitative().get(j);
//				System.out.println("---QUANTI :" + all_user_u.get(i).getList_similarity_quantitative().get(j));
////				System.out.println("---QUALI :" + all_user_u.get(i).getList_similarity_qualitative().get(j));
////				System.out.println("OVERALL :" + overall_similarity);
//				list_overall.add(overall_similarity);
//			}
			
			//A12DQZKRKTNF5E: 0.538805606120486
			//A15G70V9OBTOVO: 0.6632943479037855
			//A19KS6J6X4FJEQ: 0.5444332616945734
//			double avg_similarity_per_user = Misc.mean(list_overall);			
//			double avg_similarity_per_user = overall_similarity;
			
			
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO similar_user (user_id, similarity, active_user_id) VALUES (?, ?, ?)");
			pstmt.setString(1, all_user_u.get(i).getAmazon_id());
			pstmt.setDouble(2, overall_similarity);
			pstmt.setInt(3, user_id);
			pstmt.executeUpdate();
			
			list_overall_similarity.add(overall_similarity);
			similarity.add(overall_similarity);
			similar_user.add(all_user_u.get(i).getAmazon_id());
			all_user_u.get(i).setOverall_similarity(overall_similarity);
			
		}
		
		
		
		//RECOMMENDED ITEMS
		PreparedStatement pstmt =conn.prepareStatement("DELETE FROM recommended_items WHERE active_user_id = ?;\r\n" + 
				"INSERT INTO recommended_items (active_user_id, item_id)\r\n" + 
				"SELECT t1.active_user_id, t1.item_id FROM (SELECT similaruser.active_user_id, view_rated_match_item.id AS item_id, COUNT(view_rated_match_item.id)\r\n" + 
				"FROM view_rated_match_item, (SELECT * FROM similar_user WHERE active_user_id=? ORDER BY similarity DESC LIMIT 20) AS similaruser\r\n" + 
				"WHERE similaruser.user_id = view_rated_match_item.user_id\r\n" + 
				"GROUP BY view_rated_match_item.id, similaruser.active_user_id\r\n" + 
				"ORDER BY count DESC) AS t1;");
		pstmt.setInt(1, user_id);
		pstmt.setInt(2, user_id);
		pstmt.executeUpdate();
	}

	public static void rating_prediction(int user_id) throws SQLException {
		Connection conn = DatabaseConnector.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs;
		activeUserProfile active_user = new activeUserProfile();
		rs = stmt.executeQuery("SELECT field_name, AVG(rating) AS rating, feature_id FROM user_feature_values \r\n" + 
				"JOIN features ON (features.id = user_feature_values.feature_id) WHERE active_user_id = '" + user_id + "' GROUP BY field_name, feature_id");
		
		//ACTIVE USER FEATURES AND RATINGS
		List<String> list_field_name = new ArrayList<>();
		List<Double> list_rating = new ArrayList<>();
		while (rs.next()) {
			String field_name = rs.getString("field_name");
			double rating = (double) rs.getInt("rating");
			list_field_name.add(field_name);
			list_rating.add(rating);
		}
		active_user.setId(user_id);
		active_user.setAll_feature(list_field_name);
		active_user.setAll_rating(list_rating);
		
		//AVERAGE ACTIVE USER FEATURE RATING
		double avg_active_user_rating = Misc.mean(list_rating);
		
		List<String> list_other_feature = new ArrayList<>();
		List<Integer> list_other_feature_id = new ArrayList<>();
		List<String> list_other_feature_type = new ArrayList<>();
		
		rs = stmt.executeQuery("SELECT field_name, id, feature_type FROM features WHERE id NOT IN (SELECT feature_id FROM user_feature_values\r\n" + 
				"WHERE active_user_id = '" + user_id + "') AND NOT field_name = 'online_publication' ORDER BY id ASC");
		while (rs.next()) {
			String field_name = rs.getString("field_name");
			int feature_id = rs.getInt("id");
			String feature_type = rs.getString("feature_type");
			
			list_other_feature.add(field_name);
			list_other_feature_id.add(feature_id);
			list_other_feature_type.add(feature_type);
		}
		
		List<String> list_all_feature = new ArrayList<>();
		rs = stmt.executeQuery("SELECT field_name, id FROM features WHERE NOT field_name = 'online_publication' ORDER BY id ASC");
		while (rs.next()) {
			String field_name = rs.getString("field_name");
			list_all_feature.add(field_name);
		}
		
		List<String> list_user_u_id = new ArrayList<>();
		List<Double> list_similarity = new ArrayList<>();
		rs= stmt.executeQuery("SELECT * FROM similar_user WHERE active_user_id = '" + user_id + "' ORDER BY similarity DESC LIMIT 20");
		while (rs.next()) {
			String user_u_id = rs.getString("user_id");
			double similarity = rs.getDouble("similarity");
			list_user_u_id.add(user_u_id);
			list_similarity.add(similarity);
		}
		System.out.println(list_user_u_id);
		List<userU> list_user_u = new ArrayList<>();
		for (int i = 0; i < list_user_u_id.size(); i++) {
			userU user_u = new userU();
			
			//USER U ID
			user_u.setAmazon_id(list_user_u_id.get(i));
			//USER U SIMILARITY TO ACTIVE USER
			user_u.setOverall_similarity(list_similarity.get(i));
			
			List<Double> all_user_u_rating = new ArrayList<>();
			rs = stmt.executeQuery("SELECT * FROM feature_weight WHERE user_id = '" + list_user_u_id.get(i) + "'");
			while (rs.next()) {
				for (int j = 0; j < list_all_feature.size(); j++) {
					double weight = rs.getDouble(list_all_feature.get(j));
					double rating = weight * (5 - 1) + 1;
					all_user_u_rating.add(rating);
				}
			}
			//USER U AVERAGE FEATURE RATINGS
			double avg_user_u_rating = Misc.mean(all_user_u_rating);
			user_u.setRating_similarity(avg_user_u_rating);
			
			List<Double> other_user_u_rating = new ArrayList<>();
			rs = stmt.executeQuery("SELECT * FROM feature_weight WHERE user_id = '" + list_user_u_id.get(i) + "'");
			while (rs.next()) {
				for (int j = 0; j < list_other_feature.size(); j++) {
					double weight = rs.getDouble(list_other_feature.get(j));
					double rating = weight * (5 - 1) + 1;
					other_user_u_rating.add(rating);
				}
			}
			//USER U FEATURE (RATINGS)
			user_u.setOther_feature_rating(other_user_u_rating);
			user_u.setOther_feature(list_other_feature);			
			list_user_u.add(user_u);
		}
		
		stmt.executeUpdate("DELETE FROM recommended_feature WHERE active_user_id = '" + user_id + "'");
		
		//FEATURE PREDICTIONS CALCULATION
		for (int i = 0; i < list_other_feature.size(); i++) {						
			List<Double> list_pred_top = new ArrayList<>();
			for (int j = 0; j < list_user_u.size(); j++) {
				double pred_top = list_user_u.get(j).getOverall_similarity() * (list_user_u.get(j).getOther_feature_rating().get(i) - list_user_u.get(j).getRating_similarity());
				list_pred_top.add(pred_top);
			}
			double feature_prediction = avg_active_user_rating + (Misc.sum(list_pred_top) / Misc.sum(list_rating));
			System.out.println(list_other_feature.get(i) + " : " + feature_prediction);
			
			PreparedStatement pstm = conn.prepareStatement("INSERT INTO recommended_feature (active_user_id, feature_id, predicted_rating) VALUES (?, ?, ?)");
			pstm.setInt(1, user_id);
			pstm.setInt(2, list_other_feature_id.get(i));
			pstm.setDouble(3, feature_prediction);
			pstm.executeUpdate();
		}
		
		//FEATURE VALUES RECOMMENDATIONS
		List<feature> list_recommended_features = new ArrayList<>();
		rs = stmt.executeQuery("SELECT features.*, recommended_feature.predicted_rating FROM recommended_feature, features\r\n" + 
				"WHERE features.id = recommended_feature.feature_id AND active_user_id = '" + user_id + "' " + 
				"ORDER BY predicted_rating DESC LIMIT 8");
		while (rs.next()) {
			String feature_name = rs.getString("name");
			String feature_type = rs.getString("feature_type");
			int feature_id = rs.getInt("id");
			String field_name = rs.getString("field_name");
			feature recommended_feature = new feature();
			recommended_feature.setFeature_name(feature_name);
			recommended_feature.setFeature_type(feature_type);
			recommended_feature.setField_name(field_name);
			recommended_feature.setId(feature_id);
			list_recommended_features.add(recommended_feature);
			System.out.println(feature_name);
		}
		System.out.println("-----");
		stmt.executeUpdate("UPDATE recommended_feature SET feature_value = NULL WHERE active_user_id = '" + user_id + "'; ");
		for (int i = 0; i < list_recommended_features.size(); i++) {
			List<String> list_value = new ArrayList<>();
			List<Double> list_value_number = new ArrayList<>();
			List<Double> list_count = new ArrayList<>();
			String feature_value = null;
			if (list_recommended_features.get(i).getFeature_type().equals("TEXT")) {
				rs = stmt.executeQuery("SELECT " + list_recommended_features.get(i).getField_name() + ", COUNT(" + list_recommended_features.get(i).getField_name() + ") "
						+ "FROM view_rated_match_item \r\n"
						+ "WHERE user_id IN (SELECT user_id FROM similar_user WHERE active_user_id ='"
						+ user_id + "' AND " + list_recommended_features.get(i).getField_name() + " !='' ORDER BY similarity DESC LIMIT 20)\r\n"
						+ "GROUP BY " + list_recommended_features.get(i).getField_name() + " ORDER BY count DESC");
				while (rs.next()) {
					String value = rs.getString(list_recommended_features.get(i).getField_name());
					double count = rs.getDouble(2);
					list_value.add(value);
					list_count.add(count);
				}
				feature_value = list_value.get(0);
				System.out.println(list_recommended_features.get(i).getFeature_name() + ": " + feature_value);
			} else {
				rs = stmt.executeQuery("SELECT " + list_recommended_features.get(i).getField_name() + ", COUNT(" + list_recommended_features.get(i).getField_name() + ") AS count_selection "
						+ "FROM view_rated_match_item \r\n"
						+ "WHERE user_id IN (SELECT user_id FROM similar_user WHERE active_user_id ='"
						+ user_id + "' AND " + list_recommended_features.get(i).getField_name() + " !=0 ORDER BY similarity DESC LIMIT 20)\r\n"
						+ "GROUP BY " + list_recommended_features.get(i).getField_name() + " ORDER BY " + list_recommended_features.get(i).getField_name() + " ASC");
				while (rs.next()) {
					double value = rs.getDouble(1);
					double count = rs.getDouble("count_selection");
					list_value_number.add(value);
					list_count.add(count);
				}
				
				System.out.println(list_recommended_features.get(i).getField_name() + ": " + list_count);
				double max_count = Collections.max(list_count);
				List<String> list_feature_value = new ArrayList<>();
				for (int j = 0; j < list_count.size(); j++) {
					List<Double> list_value_glock = new ArrayList<>();
					String one_feature_value = null;
					if (list_count.get(j).equals(max_count)) {
						System.out.println(list_recommended_features.get(i).getFeature_name() + ": " + list_value_number.get(j));
						double sigma = 0;
						if (j > 0 && j < list_count.size() - 1) {
							sigma = list_value_number.get(j);
							list_value_glock.add(list_value_number.get(j));
							list_value_glock.add(list_value_number.get(j - 1));
							list_value_glock.add(list_value_number.get(j + 1));
						} else if (j == 0) {
							sigma = list_value_number.get(j);
							list_value_glock.add(list_value_number.get(j));
							list_value_glock.add(list_value_number.get(j + 1));
						} else if (j == list_count.size() - 1) {
							sigma = list_value_number.get(j);
							list_value_glock.add(list_value_number.get(j));
							list_value_glock.add(list_value_number.get(j - 1));
						}
//						System.out.println("GLOCK:  " + list_value_glock);
						double min = Misc.round(sigma - Misc.standardDeviation(list_value_glock), 0);
						double max = Misc.round(sigma + Misc.standardDeviation(list_value_glock), 0);
						if (min < 0 || Double.isNaN(min) || Double.isInfinite(min)) one_feature_value =  Double.toString(sigma);
						else one_feature_value = " " + min + " - " + max + " ";
						list_feature_value.add(one_feature_value);
					} 
				}
				for (int j = 0; j < list_count.size(); j++) {
					List<Double> list_value_glock = new ArrayList<>();
					String one_feature_value = null;
					if (list_count.get(j).equals(max_count - 1) && list_feature_value.size() < 2) {
						double sigma = 0;
						if (j > 0 && j < list_count.size() - 1) {
							sigma = list_value_number.get(j);
							if (list_count.get(j) > list_count.get(j-1) && list_count.get(j) > list_count.get(j+1)) {
								list_value_glock.add(list_value_number.get(j));
								list_value_glock.add(list_value_number.get(j - 1));
								list_value_glock.add(list_value_number.get(j + 1));
							}
						} else if (j == 0) {
							sigma = list_value_number.get(j);
							list_value_glock.add(list_value_number.get(j));
							list_value_glock.add(list_value_number.get(j + 1));
						} else if (j == list_count.size() - 1) {
							sigma = list_value_number.get(j);
							list_value_glock.add(list_value_number.get(j));
							list_value_glock.add(list_value_number.get(j - 1));
						}
//						System.out.println("GLOCK:  " + list_value_glock);
						if (!Double.isNaN(Misc.standardDeviation(list_value_glock))) {
							double min = Misc.round(sigma - Misc.standardDeviation(list_value_glock), 0);
							double max = Misc.round(sigma + Misc.standardDeviation(list_value_glock), 0);
							if (min < 0) one_feature_value =  Double.toString(sigma);
							else one_feature_value = " " + min + " - " + max + " ";
							list_feature_value.add(one_feature_value);
						}	
					}
				}
				if (list_feature_value.size() > 1) feature_value = list_feature_value.get(0) + "  ||  " + list_feature_value.get(1);
				else feature_value = list_feature_value.get(0);
//				feature_value = list_feature_value.toString().substring(1, list_feature_value.toString().length() - 1);
//				System.out.println(feature_value);
//				System.out.println(list_recommended_features.get(i).getFeature_name() +  ": " + feature_value);
			}
			list_recommended_features.get(i).setFeature_value(feature_value);
			stmt.executeUpdate("UPDATE recommended_feature SET feature_value = '" + feature_value + "' WHERE feature_id = '" + list_recommended_features.get(i).getId() + "' AND active_user_id = '" + user_id + "'");
		}			
	}
}
