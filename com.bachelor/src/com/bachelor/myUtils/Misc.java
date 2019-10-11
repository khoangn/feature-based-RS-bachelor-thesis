package com.bachelor.myUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Misc {
	//CONVERT TO 5 STAR SCALE
	public static List<Double> convertToFiveStar(List<Double> list_weight) {
		List<Double> list_converted_weight = new ArrayList<>();
		
		for (int i = 0; i < list_weight.size(); i++) {
			double converted_weight = 1 + ((list_weight.get(i) - 0)*(5-1)) / (1-0);
			list_converted_weight.add(converted_weight);
		}
		
		return list_converted_weight;
	}
	
	//CALCULATING THE QUANTITATVE SIMILARITY
	public static List<Double> quantitativeSimilarity(List<Double> list_min, List<Double> list_max, List<Double> list_value) {
		List<Double> list_similarity = new ArrayList<>();
		for (int i = 0; i < list_value.size(); i++) {
//			System.out.println("this value = " + list_value.get(i) + " max: " + list_max.get(i) + " min: " + list_min.get(i));
			if (list_value.get(i) <= list_max.get(i) && list_value.get(i) >= list_min.get(i)) {
				double similarity = 1.0;
				list_similarity.add(similarity);
			} else {
				double similarity = 0;
				list_similarity.add(similarity);
			}
//			System.out.println("therefore similarity = " + list_similarity);
		}
		return list_similarity;
	}
	
	//CREATE CUSTOM RANGE
	public static List<List<Double>> createCustomRange(double min, double max) {
		double interval = max / 6;
		List<List<Double>> list_custom_range = new ArrayList<>();
		int count = 0;
		while (count < 6) {
			List<Double> custom_range = new ArrayList<>();
			custom_range.add(min);
			custom_range.add(Misc.round(min + interval, 1));
			list_custom_range.add(custom_range);
			min = Misc.round(min + interval, 0);
			count++;
		}
//		System.out.println(list_custom_range);
		return list_custom_range;
	}
	
	//ROUNDING NUMBER
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	//STANDARDIZATION
	public static List<Double> standardization(List<Double> x) {
		List<Double> y = new ArrayList<>();
		for (int i = 0; i < x.size(); i++) {
			double s;		
			if (x.size() == 0) {
				s = 0.0;
			} else if (x.size() == 1) {
				s = x.get(i);
			} else {
				if (standardDeviation(x) == 0) s = 0;
				else s = x.get(i) - mean(x) / standardDeviation(x);
			}
//			System.out.println("S :" + s);
			y.add(s);
		}
		return y;
	}
	
	// CALCULATING THE STANDARD DEVIATION
	public static double standardDeviation(List<Double> x) {
		double s = 0;
		List<Double> y = new ArrayList<>();
		for (int i = 0; i < x.size(); i++) {
			y.add(Math.pow((x.get(i) - mean(x)), 2));
		}
//		System.out.println("X: " + x);
		if (x.size() == 0) s = 0;
		if (x.size() == 1) s = x.get(0);
		else s = Math.sqrt(sum(y) / (x.size()));
//		System.out.println("SS :" + s);
		return s;
	}
	
	//CALCULATING MEAN
	public static double mean(List<Double> x) {
		return sum(x)/x.size();
	}
	
	//CALCULATING SUM
	public static double sum(List<Double> list) {
		double sum = 0;
		for (double i : list) {
			sum += i;
		}
		return sum;
	}

	//CALCULATING NORMALIZED LIST
	public static List<Double> normalize(List<Double> list) {
		List<Double> normalized_values = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			double normalized;
			if (sum(list) == 0)
				normalized = 0;
			else
				normalized = list.get(i) / sum(list);
			normalized_values.add(normalized);
		}
		return normalized_values;
	}

    //CALCULATING ENTROPY
    public static double entropy(List<Double> list, double n) {
        double entropy;
        List<Double> list2 = new ArrayList<>();
        for (double i : list) {
            entropy = (i / n * (Math.log(i / n) / Math.log(2)));
            list2.add(entropy);
        }
        if (Misc.sum(list2) == 0) entropy = 0;
        else entropy = (-1) * Misc.sum(list2);

        return entropy;
    }
	
	// get qualitative features
	public static List<String> get_qualitative_feature() throws SQLException {
		Connection conn = DatabaseConnector.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"SELECT column_name FROM information_schema.columns WHERE table_name = 'view_rated_match_item' AND data_type = 'character varying' AND column_name != 'id' AND column_name != 'match_product_name' AND column_name != 'product_name' AND column_name != 'amazon_item_id' AND column_name != 'online_publication' AND column_name != 'user_id' AND column_name != 'rating' AND column_name != 'timestamp'");
		List<String> result_column_string = new ArrayList<>();
		while (rs.next()) {
			result_column_string.add(rs.getString(1));
		}
		return result_column_string;
	}

	// get quantitative features
	public static List<String> get_quantitative_feature() throws SQLException {
		Connection conn = DatabaseConnector.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"SELECT column_name FROM information_schema.columns WHERE table_name = 'view_rated_match_item' AND data_type != 'character varying' AND column_name != 'id' AND column_name != 'match_product_name' AND column_name != 'amazon_item_id' AND column_name != 'online_publication' AND column_name != 'user_id' AND column_name != 'rating' AND column_name != 'timestamp'");
		List<String> result_column_num = new ArrayList<>();
		while (rs.next()) {
			result_column_num.add(rs.getString(1));
		}
		conn.close();
		return result_column_num;
	}

	// get userID
	public static List<String> get_userID() throws SQLException {
		List<String> user_id = new ArrayList<>();
		Connection conn = DatabaseConnector.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"SELECT user_id, COUNT(*) FROM view_rated_match_item GROUP BY user_id HAVING COUNT(*) >= 3 ORDER BY user_id ASC");
		while (rs.next()) {
			user_id.add(rs.getString(1));
		}
		conn.close();
		return user_id;
	}

	// get All Feature
	public static List<String> get_all_feature() throws SQLException {
		// all feature: 58 qualitative + 20 quantitative
		List<String> qualitative_feature = Misc.get_qualitative_feature();
		List<String> quantitative_feature = Misc.get_quantitative_feature();
		List<String> all_feature = new ArrayList<>();
		all_feature.addAll(qualitative_feature);
		all_feature.addAll(quantitative_feature);
		// System.out.println("All Features: " + all_feature);
		return all_feature;
	}

	//CRAMER'S V
	public static double cramer_contingency(List<Double> count_distinct_x, List<Double> count_distinct_y,
			List<Double> count_cooccurence_x_y, double n, double number_of_distinct_x, double number_of_distinct_y) {
		double contingency_coefficient;
		List<Double> list1 = new ArrayList<>();
		List<Double> list2 = new ArrayList<>();

		for (int i = 0; i < count_distinct_x.size(); i++) {
			for (int j = 0; j < count_distinct_y.size(); j++) {
				double frequency_x_y_divide_n;
				if (n == 0)
					frequency_x_y_divide_n = 0;
				else
					frequency_x_y_divide_n = (count_distinct_x.get(i) * count_distinct_y.get(j)) / n;
				list1.add(frequency_x_y_divide_n);
			}
		}
		
		for (int i = 0; i < count_cooccurence_x_y.size(); i++) {
			double numerator;
			if (list1.get(i) == 0)
				numerator = 0;
			else numerator = Math.pow(count_cooccurence_x_y.get(i) - list1.get(i), 2) / list1.get(i);
			list2.add(numerator);
		}
		
//		System.out.println("count_distinct_x " + count_distinct_x);
//		System.out.println("count_distinct_y " + count_distinct_y);
//		System.out.println("count_cooccurence_x_y " + count_cooccurence_x_y);
//		System.out.println("n = " + n);
//		System.out.println(list1);
//		System.out.println(list2);
//		System.out.println(count_cooccurence_x_y.size());
//		System.out.println("zähler " + Misc.sum(list2));
//		System.out.println("nenner " + (n * Math.min(number_of_distinct_x, number_of_distinct_y)));
		
		contingency_coefficient = Math.sqrt(Misc.sum(list2) / (n * (Math.min(number_of_distinct_x, number_of_distinct_y) - 1)));
		return contingency_coefficient;
	}

	//PEARSON
	public static double pearson_correlation(List<Double> x, List<Double> y, double n) {
		double correlation_coefficient;
		double product_x_y;
		List<Double> all_product_x_y = new ArrayList<>();
		List<Double> square_x = new ArrayList<>();
		List<Double> square_y = new ArrayList<>();
		for (int i = 0; i < x.size(); i++) {
			product_x_y = x.get(i) * y.get(i);
			all_product_x_y.add(product_x_y);
		}
		for (int i = 0; i < x.size(); i++) {
			double temp = x.get(i) * x.get(i);
			square_x.add(temp);
		}
		for (int i = 0; i < y.size(); i++) {
			double temp = Math.pow(y.get(i), 2);
			square_y.add(temp);
		}
		double denominator = (Math.sqrt(n * Misc.sum(square_x) - Math.pow(Misc.sum(x), 2))
				* (Math.sqrt(n * Misc.sum(square_y) - Math.pow(Misc.sum(y), 2))));
		if (denominator == 0)
			correlation_coefficient = 0;
		else
			correlation_coefficient = (n * Misc.sum(all_product_x_y) - Misc.sum(x) * Misc.sum(y)) / denominator;
		return correlation_coefficient;
	}
	
	//Select feature values
	public static List<String> selectFeatureValues(String featureName) throws SQLException {
		List<String> list_features = new ArrayList<>();
		Connection conn = DatabaseConnector.getConnection();
		ResultSet rs;
		PreparedStatement pstmt_user = conn.prepareStatement("SELECT DISTINCT (" + featureName + ") FROM item WHERE NOT " + featureName + " ='' ORDER BY " + featureName + " ASC");
		rs = pstmt_user.executeQuery(); 
		while (rs.next()) {
			String feature = rs.getString(1);
			list_features.add(feature);
		}
		return list_features;
	}
	
	//FISHER TRANSFORMATION
	public static double fisher(double x) {
		double y = 0.5 * Math.log((1 + x)/(1 - x));
		return y;
	}
	
	//FISHER INVERSE
	public static double fisherInv(double x) {
		double y = (Math.exp(2*x) - 1) / (Math.exp(2*x) + 1);
		return y;
	}
	
	//EUCLIDEAN SIMILARITY
	public static double eucldSimilarity(List<Double> x, List<Double> y) {
		double eucld_sim = 0;
		for (int i = 0; i < x.size(); i++) {
			eucld_sim = eucld_sim + Math.pow((x.get(i) - y.get(i)) , 2);
		}
		if(x.size() == 0 && y.size() == 0) {
			eucld_sim = 0;
		} else {
			eucld_sim = Math.sqrt(eucld_sim);
			eucld_sim = 1 / (1 + eucld_sim);
		}
		return eucld_sim;
	}
}
