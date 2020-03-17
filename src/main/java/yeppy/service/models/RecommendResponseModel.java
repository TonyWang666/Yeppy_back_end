package yeppy.service.models;

import yeppy.service.core.Restaurant;

import java.util.List;

public class RecommendResponseModel {
    private List<Restaurant> restaurants;
    private int resultCode;
    private String message;

    public RecommendResponseModel() {

    }

    public RecommendResponseModel(List<Restaurant> restaurants, int resultCode, String message) {
        this.restaurants = restaurants;
        this.resultCode = resultCode;
        this.message = message;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
