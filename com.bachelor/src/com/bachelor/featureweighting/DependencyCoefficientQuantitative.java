package com.bachelor.featureweighting;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.myUtils.Misc;

public class DependencyCoefficientQuantitative {
	public static List<List<Double>> correlation() throws SQLException {
        List<List<Double>> correlation_coefficients = new ArrayList<>();
        double n = 0;

        Connection conn = DatabaseConnector.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs;

        //get the quantitative features
        List<String> quantitative_feature = Misc.get_quantitative_feature();
        System.out.println("Number of quantitative features " + quantitative_feature.size());
        System.out.println("Quantitative Features " + quantitative_feature);

        //get the user_id
        List<String> result_user_id = Misc.get_userID();

        for (int i = 0; i < result_user_id.size(); i++) {
            List<Double> all_pcc = new ArrayList<>();
            //get number of the rated items
            rs = stmt.executeQuery("SELECT COUNT(*) FROM view_rated_match_item WHERE user_id = '" + result_user_id.get(i) + "'");
            while (rs.next()) {
                n = rs.getDouble(1);
            }
            //System.out.println("n = " + n);
            for (int j = 0; j < quantitative_feature.size(); j++) {
                List<Double> featureValue = new ArrayList<>();
                List<Double> rating = new ArrayList<>();
                rs = stmt.executeQuery("SELECT " + quantitative_feature.get(j) + ", rating FROM view_rated_match_item WHERE user_id = '" + result_user_id.get(i) + "'");
                while (rs.next()) {
                    featureValue.add(rs.getDouble(1));
                    rating.add(rs.getDouble(2));
                }
                double pcc = Math.abs(Misc.pearson_correlation(rating, featureValue, n));
                all_pcc.add(pcc);
            }
            correlation_coefficients.add(all_pcc);
        }
        conn.close();
        //System.out.println("PEARSON CORRELATION " + correlation_coefficients);
        return correlation_coefficients;
    }
}
