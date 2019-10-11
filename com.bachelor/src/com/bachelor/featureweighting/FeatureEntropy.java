package com.bachelor.featureweighting;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.myUtils.Misc;

public class FeatureEntropy {


    public static List<Double> featureEntropy() throws SQLException {
        double n = 0;
        Connection conn = DatabaseConnector.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs;
//        String query_view = "DROP VIEW view_rated_match_item;\n" +
//                "CREATE VIEW view_rated_match_item AS\n" +
//                "SELECT item.*, rated_match.amazon_item_id, rated_match.user_id, rated_match.rating, rated_match.timestamp\n" +
//                "FROM item, (SELECT match_item.item_id, user_item_rating.* FROM match_item, user_item_rating WHERE match_item.amazon_item_id = user_item_rating.amazon_item_id) AS rated_match\n" +
//                "WHERE item.id = rated_match.item_id";
//        //Create view_rated_match_item
//        stmt.executeUpdate(query_view);

        //n: total number of items
        //rs = stmt.executeQuery("SELECT COUNT(*) FROM view_match_item");
        rs = stmt.executeQuery("SELECT COUNT(*) FROM view_rated_match_item");
        while (rs.next()) {
            n = rs.getInt(1);
            System.out.println("Total number of items: " + n);
        }

        List<String> qualitative_feature = Misc.get_qualitative_feature();
        List<String> quantitative_feature = Misc.get_quantitative_feature();

        //Calculate Entropy of quantitative features
        double entropy;
        List<Double> entropy_num = new ArrayList<>();
        for (int i = 0; i < quantitative_feature.size(); i++) {
            //get each column_name of the quantitative features from the list result_column_num
            String column_name_num = quantitative_feature.get(i);
            //count the frequency of the distinct values of each column of quantitative feature
            rs = stmt.executeQuery("SELECT COUNT (*) FROM view_rated_match_item WHERE NOT " + column_name_num + " IS NULL GROUP BY " + column_name_num);
            //rs = stmt.executeQuery("SELECT COUNT (*) FROM view_rated_match_item WHERE NOT " + column_name_num + " IS NULL GROUP BY " + column_name_num);
//            do {
                //and put them in another list result_num
                List<Double> result_num = new ArrayList<>();
                while (rs.next()) {
                    result_num.add((double) rs.getInt(1));
                }
                //calculate the entropy of quantitative features (result_num) and total number of items n
                entropy = Misc.entropy(result_num, n);
                //add all entropies in the list entropy_num
                entropy_num.add(entropy);
//            } while (rs.next());
        }

        //Calculate Entropy of qualitative features
        List<Double> entropy_string = new ArrayList<>();
        for (int i = 0; i < qualitative_feature.size(); i++) {
            //get each column_name of the qualitative features from the list result_column_string
            String column_name_string = qualitative_feature.get(i);
            rs = stmt.executeQuery("SELECT COUNT (*) FROM view_match_item WHERE " + column_name_string + " != '' GROUP BY " + column_name_string);
            //rs = stmt.executeQuery("SELECT COUNT (*) FROM view_rated_match_item WHERE " + column_name_string + " != '' GROUP BY " + column_name_string);
//            do {
                //and put them in another list result_string
                List<Double> result_string = new ArrayList<>();
                while (rs.next()) {
                    result_string.add((double) rs.getInt(1));
                }
                //calculate the entropy of qualitative features and total number of items n
                entropy = Misc.entropy(result_string, n);
                //add all entropies in the list entropy_string
                entropy_string.add(entropy);
//            } while (rs.next());
        }

        //sum of all entropies
        List<Double> entropy_all = new ArrayList<>();
        entropy_all.addAll(entropy_string);
        entropy_all.addAll(entropy_num);
        System.out.println("Entropies: " + entropy_all);
        System.out.println("SUM: " + Misc.sum(entropy_all));

        //calculate normalized entropies
        List<Double> normalized_entropy = Misc.normalize(entropy_all);
        System.out.println("Normalized Entropies: " + normalized_entropy);

        //all feature: 58 qualitative + 20 quantitative
        List<String> all_feature = new ArrayList<>();
        all_feature.addAll(qualitative_feature);
        all_feature.addAll(quantitative_feature);

        System.out.println("All Features: " + all_feature);

//        for(int i = 0; i < 78; i++) {
//            System.out.println( i + " " + all_feature.get(i) + " : " + normalized_entropy.get(i));
//        }
        
//        List<Double> list_value = new ArrayList<>();
//        List<Double> list_count = new ArrayList<>();
//        rs = stmt.executeQuery("SELECT pixel_number, COUNT(pixel_number) FROM view_rated_match_item GROUP BY pixel_number");
//        while (rs.next()) {
//        	double value = rs.getInt(1);
//        	double count = rs.getInt(2);
//        	list_value.add(value);
//        	list_count.add(count);
//        }
//        System.out.println("DISTINCT VALUES WITHOUT RANGES: " + list_value);
//		Double[] array_count_numer = {0.0,0.0,0.0,0.0,0.0,0.0};
//		List<Double> list_count_new = Arrays.asList(array_count_numer);
//        List<List<Double>> list_custom_range = Misc.createCustomRange(0, 29);
//		System.out.println("CREATED RANGES: " + list_custom_range);
//        System.out.println("LIST COUNT: " + list_count);
//		for (int i = 0; i < list_value.size(); i++) {
//			for (int j = 0; j < list_custom_range.size(); j++) {
//				if(list_value.get(i) >= list_custom_range.get(j).get(0) && list_value.get(i) < list_custom_range.get(j).get(1)) {
//					list_count_new.set(j, list_count.get(i) + list_count_new.get(j));
//				}
//			}
//		}
//        System.out.println("LIST COUNT NEW: " + list_count_new);
//        double exp = Misc.entropy(list_count_new, 16071);
//        double exp1 = Misc.entropy(list_count, 16071);
//        System.out.println("ENTROPY WITH DISTINCT VALUES: " + exp1);
//        System.out.println("ENTROPY WITH RANGES: " + exp);
        conn.close();
        return normalized_entropy;
    }
}
