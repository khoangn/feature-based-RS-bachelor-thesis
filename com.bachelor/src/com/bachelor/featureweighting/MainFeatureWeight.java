package com.bachelor.featureweighting;

import java.sql.SQLException;


public class MainFeatureWeight {
    public static void main(String[] args) throws SQLException {
        
        long startTime = System.currentTimeMillis();
        
//       FeatureEntropy.featureEntropy();
//        System.out.println(DependencyCoefficientQualitative.contingency());
//        System.out.println(DependencyCoefficientQuantitative.correlation());
        FeatureWeight.featureWeighting();
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
    }
}
