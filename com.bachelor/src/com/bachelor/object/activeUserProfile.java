package com.bachelor.object;

import java.util.List;

public class activeUserProfile {
	
	private int id;
	private List<String> all_feature_quantitative;
	private List<String> all_feature_qualitative;
	private List<Double> all_rating_quantitative;
	private List<Double> all_rating_qualitative;
	private List<Double> all_rating;
	private List<Double> all_user_u_quantitative;
	private List<String> all_feature;

	public List<Double> getAll_user_u_quantitative() {
		return all_user_u_quantitative;
	}

	public void setAll_user_u_quantitative(List<Double> all_user_u_quantitative) {
		this.all_user_u_quantitative = all_user_u_quantitative;
	}

	public List<String> getAll_feature() {
		return all_feature;
	}

	public void setAll_feature(List<String> all_feature) {
		this.all_feature = all_feature;
	}

	public List<Double> getAll_rating() {
		return all_rating;
	}

	public void setAll_rating(List<Double> all_rating) {
		this.all_rating = all_rating;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<String> getAll_feature_quantitative() {
		return all_feature_quantitative;
	}

	public void setAll_feature_quantitative(List<String> all_feature_quantitative) {
		this.all_feature_quantitative = all_feature_quantitative;
	}

	public List<String> getAll_feature_qualitative() {
		return all_feature_qualitative;
	}

	public void setAll_feature_qualitative(List<String> all_feature_qualitative) {
		this.all_feature_qualitative = all_feature_qualitative;
	}

	public List<Double> getAll_rating_quantitative() {
		return all_rating_quantitative;
	}

	public void setAll_rating_quantitative(List<Double> all_rating_quantitative) {
		this.all_rating_quantitative = all_rating_quantitative;
	}

	public List<Double> getAll_rating_qualitative() {
		return all_rating_qualitative;
	}

	public void setAll_rating_qualitative(List<Double> all_rating_qualitative) {
		this.all_rating_qualitative = all_rating_qualitative;
	}
	
	public activeUserProfile() {
		// TODO Auto-generated constructor stub
	}

}
