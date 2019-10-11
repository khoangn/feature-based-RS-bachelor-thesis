package com.bachelor.object;

import java.util.List;

public class itemAverageRating {
	private String id;
	private String img_src;
	private double average_rating;
	private String product_name;
	private int total_count_rating;
	private List<ratingCount> list_each_rating;
	
	
	public List<ratingCount> getList_each_rating() {
		return list_each_rating;
	}
	public void setList_each_rating(List<ratingCount> list_each_rating) {
		this.list_each_rating = list_each_rating;
	}
	public int getTotal_count_rating() {
		return total_count_rating;
	}
	public void setTotal_count_rating(int total_count_rating) {
		this.total_count_rating = total_count_rating;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImg_src() {
		return img_src;
	}
	public void setImg_src(String img_src) {
		this.img_src = img_src;
	}
	public double getAverage_rating() {
		return average_rating;
	}
	public void setAverage_rating(double average_rating) {
		this.average_rating = average_rating;
	}

	
	
}
