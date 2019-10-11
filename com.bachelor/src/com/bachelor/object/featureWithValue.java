package com.bachelor.object;

import java.util.List;

public class featureWithValue {
	private int id;
	private String field_name;
	private String feature_name;
	private String feature_type;
	private List<String> distinct_value;
	private int min_value;
	private int max_value;
	
	
	public int getMin_value() {
		return min_value;
	}
	public void setMin_value(int min_value) {
		this.min_value = min_value;
	}
	public int getMax_value() {
		return max_value;
	}
	public void setMax_value(int max_value) {
		this.max_value = max_value;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFeature_type() {
		return feature_type;
	}
	public void setFeature_type(String feature_type) {
		this.feature_type = feature_type;
	}
	public String getFeature_name() {
		return feature_name;
	}
	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}
	
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public List<String> getDistinct_value() {
		return distinct_value;
	}
	public void setDistinct_value(List<String> distinct_value) {
		this.distinct_value = distinct_value;
	}
	public featureWithValue() {
		// TODO Auto-generated constructor stub
	}

}
