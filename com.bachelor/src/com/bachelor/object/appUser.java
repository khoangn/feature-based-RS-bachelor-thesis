package com.bachelor.object;

public class appUser {

	private int id;
	private int active;
	private String password;
	private String username;
	private boolean has_valid_item_result;

	public appUser() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isHas_valid_item_result() {
		return has_valid_item_result;
	}

	public void setHas_valid_item_result(boolean has_valid_item_result) {
		this.has_valid_item_result = has_valid_item_result;
	}

}
