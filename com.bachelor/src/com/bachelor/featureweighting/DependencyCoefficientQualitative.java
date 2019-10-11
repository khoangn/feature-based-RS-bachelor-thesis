package com.bachelor.featureweighting;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.myUtils.Misc;

public class DependencyCoefficientQualitative {
	public static List<List<Double>> contingency() throws SQLException {
        List<List<Double>> cramer_contingency = new ArrayList<>();
        double n = 0;
        double number_of_distinct_rating = 0;
        double number_of_distinct_value = 0;

        Connection conn = DatabaseConnector.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs;

        List<String> qualitative_feature = Misc.get_qualitative_feature();
        System.out.println("Number of qualitative features " + qualitative_feature.size());
        System.out.println("Qualitative Features " + qualitative_feature);

        //get the user_id
        List<String> result_user_id = Misc.get_userID();

        //for each user
        for (int j = 0; j < result_user_id.size(); j++) {
            List<String> distinct_rating = new ArrayList<>();
            List<Double> count_distinct_rating = new ArrayList<>();
            //get the set of distinct values of the ratings, and the frequency of each value
            rs = stmt.executeQuery("SELECT rating, COUNT(*) FROM view_rated_match_item WHERE user_id = '" + result_user_id.get(j) + "' GROUP BY rating ORDER BY rating ASC");
            while (rs.next()) {
                distinct_rating.add(rs.getString(1));
                count_distinct_rating.add(rs.getDouble(2));
            }
            //System.out.println(distinct_rating + "|" + count_distinct_rating);

            //get number of the rated items
            rs = stmt.executeQuery("SELECT COUNT(*) FROM view_rated_match_item WHERE user_id = '" + result_user_id.get(j) + "'");
            while (rs.next()) {
                n = rs.getDouble(1);
            }
            //System.out.println("n = " + n);

            //to get number of distinct ratings
            rs = stmt.executeQuery("SELECT COUNT (DISTINCT rating) FROM view_rated_match_item WHERE user_id = '" + result_user_id.get(j) + "'");
            while (rs.next()) {
                number_of_distinct_rating = rs.getDouble(1);
            }

            //for each item feature
            List<Double> all_vc = new ArrayList<>();
            for (int i = 0; i < qualitative_feature.size(); i++) {
                rs = stmt.executeQuery("SELECT " + qualitative_feature.get(i) + ", rating, id FROM view_rated_match_item WHERE user_id = '" + result_user_id.get(j) + "' AND " + qualitative_feature.get(i) + " != '' ORDER BY id");
                List<String> rating = new ArrayList<>();
                List<String> featureValue = new ArrayList<>();
                while (rs.next()) {
                    featureValue.add(rs.getString(1));
                    rating.add(rs.getString(2));
                }
                //System.out.println(result_column_string.get(i) + " " + featureValue + "|" + rating);

                //to get the set of distinct values of the feature values, and the frequency of each value
                rs = stmt.executeQuery("SELECT " + qualitative_feature.get(i) + ", COUNT(*) FROM view_rated_match_item WHERE user_id = '" + result_user_id.get(j) + "' GROUP BY " + qualitative_feature.get(i) + " ORDER BY " + qualitative_feature.get(i) + " ASC");
                List<String> distinct_featureValue = new ArrayList<>();
                List<Double> count_distinct_featureValue = new ArrayList<>();
                while (rs.next()) {
                    distinct_featureValue.add(rs.getString(1));
                    count_distinct_featureValue.add(rs.getDouble(2));
                }
                //System.out.println(result_column_string.get(i) + " " + distinct_featureValue + "|" + count_distinct_featureValue);

                //to get the frequency of simultaneous occurrences of the feature value and rating
                rs = stmt.executeQuery("SELECT t1." + qualitative_feature.get(i) + ", t2.rating, COUNT(t3.rating)\n" +
                        "FROM (SELECT DISTINCT " + qualitative_feature.get(i) + " FROM view_rated_match_item WHERE user_id = '" + result_user_id.get(j) + "') AS t1\n" +
                        "CROSS JOIN (SELECT DISTINCT rating FROM view_rated_match_item WHERE user_id = '" + result_user_id.get(j) + "') AS t2\n" +
                        "LEFT JOIN view_rated_match_item AS t3 ON t3.user_id = '" + result_user_id.get(j) + "' AND t3." + qualitative_feature.get(i) + " = t1." + qualitative_feature.get(i) + " AND t3.rating = t2.rating\n" +
                        "GROUP BY t1." + qualitative_feature.get(i) + ", t2.rating ORDER BY t1." + qualitative_feature.get(i) + " ASC ,t2.rating ASC");
                List<String> val = new ArrayList<>();
                List<String> rat = new ArrayList<>();
                List<Double> count_val_rat = new ArrayList<>();
                while (rs.next()) {
                    val.add(rs.getString(1));
                    rat.add(rs.getString(2));
                    count_val_rat.add(rs.getDouble(3));
                }
                //System.out.println("val" + val + " || rat" + rat + " || count val_rat" + count_val_rat);

                //to get number of distinct feature values
                rs = stmt.executeQuery("SELECT COUNT (DISTINCT " + qualitative_feature.get(i) + ") FROM view_rated_match_item WHERE user_id = '" + result_user_id.get(j) + "'");
                while (rs.next()) {
                    number_of_distinct_value = rs.getDouble(1);
                    //System.out.println(number_of_distinct_value);
                }

                double vc = Misc.cramer_contingency(count_distinct_rating, count_distinct_featureValue, count_val_rat, n, number_of_distinct_rating, number_of_distinct_value);
                if (Double.isNaN(vc)) all_vc.add(0.0);
                else all_vc.add(vc);
            }
            cramer_contingency.add(all_vc);
        }
        conn.close();
        System.out.println("CRAMER CONTINGENCY " + cramer_contingency);
        return cramer_contingency;
    }
}
