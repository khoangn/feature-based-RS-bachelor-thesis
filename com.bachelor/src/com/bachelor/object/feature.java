package com.bachelor.object;

public class feature {
	private int id;
	private String feature_name;
	private String field_name;
	private boolean showing;	
	private String feature_type;
	private String feature_value;
	
	public String getFeature_value() {
		return feature_value;
	}

	public void setFeature_value(String feature_value) {
		this.feature_value = feature_value;
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

	public boolean isShowing() {
		return showing;
	}

	public void setShowing(boolean showing) {
		this.showing = showing;
	}

	
	public feature() {
	}

}
