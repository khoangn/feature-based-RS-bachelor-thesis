package com.bachelor.object;

public class userItemRating {
    private String user_id;
    private String amazon_item_id;
    private double rating;

    public userItemRating() {}
    public userItemRating(String userId, String amazonId, double rating) {
        this.user_id = userId;
        this.amazon_item_id = amazonId;
        this.rating = rating;
    }

    public String getUser_id() {
        return user_id;
    }
    public String getAmazon_item_id() {
        return amazon_item_id;
    }
    public double getRating() {
        return rating;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setAmazon_item_id(String amazon_item_id) {
        this.amazon_item_id = amazon_item_id;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
