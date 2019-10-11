package com.bachelor.object;

import java.util.List;

public class userU {

	private String amazon_id;
	private List<String> all_item_id;
	private List<String> all_feature;
	private List<Double> all_feature_weight;
	private List<String> list_quantitative;
	private List<String> list_qualitative;
	private List<List<Double>> list_quantitative_value;
	private List<List<String>> list_qualitative_value;
	private List<Double> list_similarity_quantitative;
	private List<Double> list_similarity_qualitative;
	private double rating_similarity;
	private double overall_similarity;
	private List<Double> all_feature_rating;
	private List<Double> other_feature_rating;
	private List<String> other_feature;
	
	
	public List<Double> getAll_feature_rating() {
		return all_feature_rating;
	}

	public void setAll_feature_rating(List<Double> all_feature_rating) {
		this.all_feature_rating = all_feature_rating;
	}

	public List<Double> getOther_feature_rating() {
		return other_feature_rating;
	}

	public void setOther_feature_rating(List<Double> other_feature_rating) {
		this.other_feature_rating = other_feature_rating;
	}

	public List<String> getOther_feature() {
		return other_feature;
	}

	public void setOther_feature(List<String> other_feature) {
		this.other_feature = other_feature;
	}

	public double getRating_similarity() {
		return rating_similarity;
	}

	public void setRating_similarity(double rating_similarity) {
		this.rating_similarity = rating_similarity;
	}



	public double getOverall_similarity() {
		return overall_similarity;
	}

	public void setOverall_similarity(double overall_similarity) {
		this.overall_similarity = overall_similarity;
	}

	public List<Double> getList_similarity_qualitative() {
		return list_similarity_qualitative;
	}

	public void setList_similarity_qualitative(List<Double> list_similarity_qualitative) {
		this.list_similarity_qualitative = list_similarity_qualitative;
	}

	public List<Double> getList_similarity_quantitative() {
		return list_similarity_quantitative;
	}

	public void setList_similarity_quantitative(List<Double> list_similarity_quantitative) {
		this.list_similarity_quantitative = list_similarity_quantitative;
	}

	public List<String> getList_quantitative() {
		return list_quantitative;
	}

	public void setList_quantitative(List<String> list_quantitative) {
		this.list_quantitative = list_quantitative;
	}

	public List<String> getList_qualitative() {
		return list_qualitative;
	}

	public void setList_qualitative(List<String> list_qualitative) {
		this.list_qualitative = list_qualitative;
	}

	public List<List<Double>> getList_quantitative_value() {
		return list_quantitative_value;
	}

	public void setList_quantitative_value(List<List<Double>> list_quantitative_value) {
		this.list_quantitative_value = list_quantitative_value;
	}

	public List<List<String>> getList_qualitative_value() {
		return list_qualitative_value;
	}

	public void setList_qualitative_value(List<List<String>> list_qualitative_value) {
		this.list_qualitative_value = list_qualitative_value;
	}


	public List<String> getAll_item_id() {
		return all_item_id;
	}

	public void setAll_item_id(List<String> all_item_id) {
		this.all_item_id = all_item_id;
	}

	public String getAmazon_id() {
		return amazon_id;
	}

	public void setAmazon_id(String amazon_id) {
		this.amazon_id = amazon_id;
	}

	public List<String> getAll_feature() {
		return all_feature;
	}

	public void setAll_feature(List<String> all_feature) {
		this.all_feature = all_feature;
	}

	public List<Double> getAll_feature_weight() {
		return all_feature_weight;
	}

	public void setAll_feature_weight(List<Double> all_feature_weight) {
		this.all_feature_weight = all_feature_weight;
	}

	public userU() {
		// TODO Auto-generated constructor stub
	}

}
