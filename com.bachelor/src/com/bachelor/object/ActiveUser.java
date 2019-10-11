package com.bachelor.object;

public class ActiveUser {

	private String id;
	private String username;
	private String field_name;
	private double rating;
	private double min_custom_range;
	private double max_custom_range;
	private String feature_value_name;
	
	public ActiveUser() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public double getMin_custom_range() {
		return min_custom_range;
	}

	public void setMin_custom_range(double min_custom_range) {
		this.min_custom_range = min_custom_range;
	}

	public double getMax_custom_range() {
		return max_custom_range;
	}

	public void setMax_custom_range(double max_custom_range) {
		this.max_custom_range = max_custom_range;
	}

	public String getFeature_value_name() {
		return feature_value_name;
	}

	public void setFeature_value_name(String feature_value_name) {
		this.feature_value_name = feature_value_name;
	}

}
