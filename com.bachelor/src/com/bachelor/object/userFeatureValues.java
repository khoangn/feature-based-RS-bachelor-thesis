package com.bachelor.object;

import java.util.List;

public class userFeatureValues {
	
	private int id;
	private int user_id;
	private int feature_id;
	private String field_name;
	private String name;
	private String value;
	private int min;
	private int max;
	private int rating;
	private List<String> list_value;
	private List<String> list_value_similar_user;
	private List<Integer> list_count_similar_user;
	
	public List<Integer> getList_count_similar_user() {
		return list_count_similar_user;
	}
	public void setList_count_similar_user(List<Integer> list_count_similar_user) {
		this.list_count_similar_user = list_count_similar_user;
	}
	public List<String> getList_value_similar_user() {
		return list_value_similar_user;
	}
	public void setList_value_similar_user(List<String> list_value_similar_user) {
		this.list_value_similar_user = list_value_similar_user;
	}
	public List<String> getList_value() {
		return list_value;
	}
	public void setList_value(List<String> list_value) {
		this.list_value = list_value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getFeature_id() {
		return feature_id;
	}
	public void setFeature_id(int feature_id) {
		this.feature_id = feature_id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public userFeatureValues() {
		// TODO Auto-generated constructor stub
	}

}
