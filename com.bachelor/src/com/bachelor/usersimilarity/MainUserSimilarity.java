package com.bachelor.usersimilarity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bachelor.*;
import com.bachelor.featureweighting.DependencyCoefficientQualitative;
import com.bachelor.featureweighting.DependencyCoefficientQuantitative;
import com.bachelor.featureweighting.FeatureEntropy;
import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.myUtils.Misc;
import com.bachelor.object.userU;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MainUserSimilarity { 

	public static void main(String[] args) throws SQLException {
//		List<userU> all_user_u = UserSimilarity.getUserU();
//		System.out.println(all_user_u.get(0).getAmazon_id());
//		System.out.println(all_user_u.get(0).getAll_item_id());
//		System.out.println(all_user_u.get(0).getAll_feature_weight());
//		System.out.println(all_user_u.get(0).getAll_feature());

		
        long startTime = System.currentTimeMillis();
        UserSimilarity.ratingSimilarity(6);
        UserSimilarity.rating_prediction(6);        
//        List<Double> list1 = new ArrayList<>(); list1.add(1.0); list1.add(3.0); list1.add(4.0); list1.add(3.0); list1.add(4.0); list1.add(4.0);
//        List<Double> list2 = new ArrayList<>(); list2.add(0.013); list2.add(0.001); list2.add(0.001); list2.add(0.028); list2.add(0.135); list2.add(0.003);
//        System.out.println("NORMALIZED RATING: " + Misc.normalize(list1));
//        System.out.println("WEIGHTS: " + list2);
//        System.out.println(Misc.eucldSimilarity(Misc.normalize(list1), list2));
//        
//        List<String> list3 = new ArrayList<>(); list3.add("Fujifilm"); list3.add("Canon"); list3.add("yes"); 
//        List<String> list4 = new ArrayList<>(); list4.add("Fujifilm"); list4.add("Fujifilm"); list4.add("yes");
//        List<String> list5 = new ArrayList<>(); list4.add("Canon"); list4.add("Canon"); list4.add("no");
//        List<String> list6 = new ArrayList<>(); list4.add("Fujifilm"); list4.add("Fujifilm"); list4.add("yes"); 
        

        
//        Misc.createCustomRange(1150);
//		  Integer[] array_count_numer = {0,0,0,0,0,0};
//		  List<Integer> list_count_number = Arrays.asList(array_count_numer);
//		  System.out.println(list_count_number);
        
//        Double[] test = {1.0,4.0,5.0,7.0,11.0,15.0,20.0,21.0,24.0,26.0,30.0,42.0,50.0,60.0};
//        Double[] test = {4.0, 5.0, 3.5};
//		  List<Double> list_test = Arrays.asList(test);
//		  System.out.println(list_test);
//        double standard_deviation = Misc.standardDeviation(list_test);
        //System.out.println(Misc.standardDeviation(list_test));

//        UserSimilarity.ratingSimilarity(19);
//        UserSimilarity.rating_prediction(19);
//        UserSimilarity.rating_prediction(6);        
//        double n = Misc.round(4.6666666666666667, 2);
//        System.out.println(n);
//        Gson gsonObj = new Gson();
//        UserSimilarity.rating_prediction(6);
//        Connection conn = null;
//		ResultSet rs;
//		int active_user_id = 6;
//		try {
//        	Class.forName("org.postgresql.Driver");
//        	conn = DatabaseConnector.getConnection();
//        	Statement stmt = conn.createStatement();
//        	
//			rs = stmt.executeQuery("SELECT field_name, name, AVG(rating) AS rating FROM user_feature_values, features\r\n" + 
//					"WHERE user_id = '" + active_user_id + "' AND user_feature_values.feature_id = features.id GROUP BY name, field_name ORDER BY name");
//			List<String> list_feature_active_user = new ArrayList<>();
//			List<Integer> list_rating_active_user = new ArrayList<>();
//			List<String> list_field_name = new ArrayList<>();
//			while (rs.next()) {
//				String feature_name = rs.getString("name");
//				int rating = rs.getInt("rating");
//				String field_name = rs.getString("field_name");
//				list_feature_active_user.add(feature_name);
//				list_field_name.add(field_name);
//				list_rating_active_user.add(rating);
//			}
//        	
//			rs = stmt.executeQuery("SELECT user_id FROM similar_user WHERE active_user_id = '" + active_user_id + "' ORDER BY user_id ASC");
//			List<String> list_similar_user = new ArrayList<>();
//			while (rs.next()) {
//				String similar_user = rs.getString("user_id");
//				list_similar_user.add(similar_user);
//			}
//
//			List<Double> list_avg_rating_user_u = new ArrayList<>();
//			for (int i = 0; i < list_field_name.size(); i++) {			
//				rs = stmt.executeQuery("SELECT * FROM feature_weight ORDER BY user_id ASC");
//				List<Double> list_rating_user_u = new ArrayList<>();
//				while (rs.next()) {
//					double weight = rs.getDouble(list_field_name.get(i));
//					double rating = weight * (5 - 1) + 1;
//					list_rating_user_u.add(rating);
//				}
//				
//				
//				double avg_rating_user_u = Misc.round(Misc.mean(list_rating_user_u), 2);
//				System.out.println(avg_rating_user_u);
//				list_avg_rating_user_u.add(avg_rating_user_u);
//			}
//			
//			Gson gsonObj = new Gson();
//			
//			String list_feature_json = gsonObj.toJson(list_feature_active_user);
//			String list_rating_active_user_json = gsonObj.toJson(list_rating_active_user);
//			String list_avg_rating_user_u_json = gsonObj.toJson(list_avg_rating_user_u);
//			
//			conn.close();
//		} catch (SQLException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}
        
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("RUNTIME: " + totalTime);
		
//      List<Double> x = new ArrayList<>(); x.add(250.0);x.add(20.0);x.add(115.0);
//      String json = gsonObj.toJson(x);
//      System.out.println(json);
//      System.out.println(x.toString());		
		
		//UserSimilarity.similarity_au_uu();
		
//		List<Double> x = new ArrayList<>(); x.add(250.0);x.add(20.0);x.add(115.0);
//		List<Double> y = new ArrayList<>(); y.add(279.0);y.add(12.0);y.add(225.0);
//		System.out.println(Misc.eucldSimilarity(x, y));
		
		
//		List<Double> x = new ArrayList<>(); 0.008692668936672508
//
//		x.add((double) 5);x.add((double) 3);x.add((double) 4);x.add((double) 4);x.add((double) 4);x.add((double) 4);x.add((double) 4);x.add((double) 4);
//		
//		System.out.println(Misc.standardDeviation(x));
//		System.out.println(Misc.standardization(x));
		
//		List<Double> y = new ArrayList<>();
//		y.add((double) 12);y.add((double) 6.5);y.add((double) 200);
//		
//		double e = Misc.eucldSimilarity(x, y);
//		System.out.println(e);
		
//		ArrayList<String> animals = new ArrayList<String>();
//		animals.add("cat");
//		animals.add("owl");
//		animals.add("dog");
//		animals.add("bat");
//		animals.add("fox");
//		animals.add("bat");
//		System.out.println(animals.toString().substring(1, animals.toString().length() - 1));
//		int occurrences = Collections.frequency(animals, "bat");
//		//System.out.println(occurrences);
//		
//		List<Integer> list = new ArrayList<>();
//		for (int j = 0; j < animals.size(); j++) {
//			int i = animals.indexOf(animals.get(j));
//			list.add(i);
//		}
//		System.out.println(list);
//
//
//		Map<String, List<Integer>> indexes = new HashMap<>();
//		for (int i = 0; i < animals.size(); i++) {
//		    indexes.computeIfAbsent(animals.get(i), c -> new ArrayList<>()).add(i);
//		}
//		System.out.println(indexes);
//		
//		
//	    List<String> animal = animals.stream().distinct().collect(Collectors.toList());
//	    System.out.println(animals);
//	    System.out.println(animal);
	    
	}
}