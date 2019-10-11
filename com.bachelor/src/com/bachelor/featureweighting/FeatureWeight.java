package com.bachelor.featureweighting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.myUtils.Misc;

public class FeatureWeight {
    public static void featureWeighting() throws SQLException {
        List<Double> all_entropy = FeatureEntropy.featureEntropy();
        List<List<Double>> cramer_contingency = DependencyCoefficientQualitative.contingency();
        List<List<Double>> pearson_correlation = DependencyCoefficientQuantitative.correlation();
        List<List<Double>> all_coefficients = new ArrayList<>();
        for (int i = 0; i < cramer_contingency.size(); i++) {
            List<Double> all = new ArrayList<>();
            all.addAll(cramer_contingency.get(i));
            all.addAll(pearson_correlation.get(i));
            all_coefficients.add(all);

            System.out.println("VC: " + cramer_contingency.get(i).size());
            System.out.println("PCC: " + pearson_correlation.get(i).size());            
            System.out.println("ALL: " + all_coefficients.get(0).size());
        }

        System.out.println("ALL ENTROPIES " + all_entropy.size());
        System.out.println("ALL COEFFICIENTS " + all_coefficients.get(0).size());
        
        List<List<Double>> all_weights = new ArrayList<>();
        for (int i = 0; i < all_coefficients.size(); i++) {
            List<Double> all = new ArrayList<>();
            for (int j = 0; j < all_coefficients.get(i).size(); j++) {
                double w = all_entropy.get(j) * all_coefficients.get(i).get(j);
                all.add(w);
            }
            //System.out.println("ALL " + all);
            all_weights.add(Misc.normalize(all));
        }
        System.out.println("All weights: " + all_weights);

        List<String> quantitative_feature = Misc.get_quantitative_feature();
        List<String> qualitative_feature = Misc.get_qualitative_feature();

        //all feature: 58 qualitative + 20 quantitative
        List<String> all_feature = new ArrayList<>();
        all_feature.addAll(qualitative_feature);
        all_feature.addAll(quantitative_feature);
        System.out.println("All Features" + all_feature);
       
        //get user_id
        List<String> user_id = Misc.get_userID();

        Connection conn = DatabaseConnector.getConnection();
        Statement stmt = conn.createStatement();

//        String query = "INSERT INTO feature_weights (user_id, feature_id, weight) VALUES (?, (SELECT id FROM features WHERE field_name = ?), ?)";
//        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//        	for (int i = 0; i < user_id.size(); i++) {       		
//        		for (int j = 0; j < all_feature.size(); j++) {
//        			pstmt.setString(1, user_id.get(i));
//        			pstmt.setString(2, all_feature.get(j));
//        			pstmt.setDouble(3, all_weights.get(i).get(j));
//        			pstmt.executeUpdate();
//        		}       		
//        	}
        System.out.println("NUMBER OF WEIGHTS: " + all_weights.get(0).size());	
        String features = all_feature.toString();
        features = (String) features.subSequence(1, features.length() - 1);
        System.out.println("!! " + features);
        
        
        String prepared_query = "TRUNCATE TABLE feature_weight_new;" ;
//        					  + "ALTER SEQUENCE feature_weight_new_id_seq RESTART;" ;
        stmt.executeUpdate(prepared_query);

        String query = "INSERT INTO feature_weight_new (user_id, " + features + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 0; i < user_id.size(); i++) {
                pstmt.setString(1, user_id.get(i));
                for (int j = 0; j < all_weights.get(i).size(); j++) {
                    pstmt.setDouble(j + 2, all_weights.get(i).get(j));
                }
                pstmt.executeUpdate();
            }
            System.out.println("SUCCESSFULLY ADDED");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
