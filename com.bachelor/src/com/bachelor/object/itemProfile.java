package com.bachelor.object;

import java.util.List;

public class itemProfile {
	private String id;
	private String img_src;
	private List<String> list_field_name;
	private List<String> list_feature_name;
	private List<String> list_feature_value;
	private int price;
	private String product_name;
	
	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getImg_src() {
		return img_src;
	}

	public void setImg_src(String img_src) {
		this.img_src = img_src;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getList_field_name() {
		return list_field_name;
	}

	public void setList_field_name(List<String> list_field_name) {
		this.list_field_name = list_field_name;
	}

	public List<String> getList_feature_name() {
		return list_feature_name;
	}

	public void setList_feature_name(List<String> list_feature_name) {
		this.list_feature_name = list_feature_name;
	}

	public List<String> getList_feature_value() {
		return list_feature_value;
	}

	public void setList_feature_value(List<String> list_feature_value) {
		this.list_feature_value = list_feature_value;
	}

	public itemProfile() {
		// TODO Auto-generated constructor stub
	}

}
