package com.bachelor.object;

import java.util.List;

public class itemRating {
	private String id;
	private int count_rating;
	private List<Double> list_count_rating;
	private double item_rank;
	private String item_name;
	private String item_img;
	private int item_rating;
	private int rating;
	
	public double getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public int getItem_rating() {
		return item_rating;
	}
	public void setItem_rating(int item_rating) {
		this.item_rating = item_rating;
	}
	public String getItem_img() {
		return item_img;
	}
	public void setItem_img(String item_img) {
		this.item_img = item_img;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public double getItem_rank() {
		return item_rank;
	}
	public void setItem_rank(double item_rank) {
		this.item_rank = item_rank;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCount_rating() {
		return count_rating;
	}
	public void setCount_rating(int count_rating) {
		this.count_rating = count_rating;
	}
	public List<Double> getList_count_rating() {
		return list_count_rating;
	}
	public void setList_count_rating(List<Double> list_count_rating) {
		this.list_count_rating = list_count_rating;
	}
	
}
