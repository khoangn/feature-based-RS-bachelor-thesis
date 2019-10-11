package com.bachelor.object;

import java.util.List;

public class recommendedItem {
	private String id;
	private String img_src;
	private String product_name;
	private int price;
	private int pixel_number;
	private int rating;
	private List<thisItem> list_pro;
	private List<thisItem> list_con;
	private double avg_rating;
	private int count_rating;
	private itemAverageRating all_avg_rating;
	
	public itemAverageRating getAll_avg_rating() {
		return all_avg_rating;
	}

	public void setAll_avg_rating(itemAverageRating all_avg_rating) {
		this.all_avg_rating = all_avg_rating;
	}

	public int getCount_rating() {
		return count_rating;
	}

	public void setCount_rating(int count_rating) {
		this.count_rating = count_rating;
	}

	public double getAvg_rating() {
		return avg_rating;
	}

	public void setAvg_rating(double avg_rating) {
		this.avg_rating = avg_rating;
	}

	public List<thisItem> getList_pro() {
		return list_pro;
	}

	public void setList_pro(List<thisItem> list_pro) {
		this.list_pro = list_pro;
	}

	public List<thisItem> getList_con() {
		return list_con;
	}

	public void setList_con(List<thisItem> list_con) {
		this.list_con = list_con;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public recommendedItem() {
		// TODO Auto-generated constructor stub
	}

	public String getImg_src() {
		return img_src;
	}

	public void setImg_src(String img_src) {
		this.img_src = img_src;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPixel_number() {
		return pixel_number;
	}

	public void setPixel_number(int pixel_number) {
		this.pixel_number = pixel_number;
	}

}
