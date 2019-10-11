package com.bachelor.itembased;

import java.sql.SQLException;

public class MainItembased {

	public static void main(String[] args) throws SQLException {
        long startTime = System.currentTimeMillis();
		
		ItemSimilarity.itemPrediction(6);
		
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("RUNTIME: " + totalTime);
	}
}
