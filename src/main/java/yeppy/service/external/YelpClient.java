package yeppy.service.external;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import yeppy.service.core.Restaurant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class YelpClient {
    private static final String HOST = "https://api.yelp.com";
    private static final String ENDPOINT = "/v3/businesses/search";
    private static final String CLIENT_ID = "4Sgz8aE5foVW8ibwEzZD3Q";
    private static final String API_KEY = "WQq6QJkLZcNYSh5frtt3NuFSxHg6AsbYW_u9UPXTe_tZbCLkahJ31p4xAmkgeESMcVPbTFwPfXInLC_Q-tggXOf5DdZIdnsq4BGUh6BF8xkGF7113Yym08-W9vQhXnYx";

    public static List<Restaurant> search(float lat, float lon, String term){
        String query = String.format("latitude=%s&longitude=%s&term=%s&categories=%s",lat, lon, term, "Restaurant");
        String url = HOST + ENDPOINT + "?" + query;
        StringBuilder responseBody = new StringBuilder();
        try{
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer "+API_KEY);

            int responseCode = connection.getResponseCode(); //send request
            if(responseCode != 200) {
                return new ArrayList<>();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            reader.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            JSONObject businesses = new JSONObject(responseBody.toString());
            return getRestaurantList(businesses.getJSONArray("businesses"));
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public static List<Restaurant> getRestaurantList(JSONArray restaurants) throws JSONException{
        List<Restaurant> list = new ArrayList<>();
        for(int i = 0; i < restaurants.length(); i++){
            JSONObject restaurantObj = restaurants.getJSONObject(i);
            Restaurant restaurant = new Restaurant();
            if (!restaurantObj.isNull("id")) {
                restaurant.setId(restaurantObj.getString("id"));
            }
            if (!restaurantObj.isNull("name")) {
                restaurant.setName(restaurantObj.getString("name"));
            }
            if (!restaurantObj.isNull("rating")) {
                restaurant.setRating(restaurantObj.getFloat("rating"));
            }
            if (!restaurantObj.isNull("price")) {
                restaurant.setPrice(restaurantObj.getString("price"));
            }
            if (!restaurantObj.isNull("phone")) {
                restaurant.setPhone(restaurantObj.getString("phone"));
            }
            if (!restaurantObj.isNull("image_url")) {
                restaurant.setUrl(restaurantObj.getString("image_url"));
            }
            if (!restaurantObj.isNull("location")) {
                restaurant.setAddress(restaurantObj.getJSONObject("location").getJSONArray("display_address").toString());
            }
            if (!restaurantObj.isNull("distance")) {
                restaurant.setDistance(restaurantObj.getFloat("distance"));
            }
            restaurant.setCategories(getCategories(restaurantObj));

            list.add(restaurant);
        }
        return list;
    }

    public static Set<String> getCategories(JSONObject restaurantObj) throws JSONException{
        Set<String> categories = new HashSet<>();
        if (!restaurantObj.isNull("categories")) {
            JSONArray classifications = restaurantObj.getJSONArray("categories");
            for (int i = 0; i < classifications.length(); ++i) {
                JSONObject classification = classifications.getJSONObject(i);
                if (!classification.isNull("alias")) {
                    categories.add(classification.getString("alias"));
                }
            }
        }
        return categories;
    }

    public static List<Restaurant> searchMostPopular(float lat, float lon){
        String query = String.format("latitude=%s&longitude=%s&categories=%s&sort_by=rating&limit=10&radius=16000",lat, lon, "Restaurant");
        String url = HOST + ENDPOINT + "?" + query;
        StringBuilder responseBody = new StringBuilder();
        try{
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer "+API_KEY);

            int responseCode = connection.getResponseCode(); //send request
            if(responseCode != 200) {
                return new ArrayList<>();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            reader.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            JSONObject businesses = new JSONObject(responseBody.toString());
            return getRestaurantList(businesses.getJSONArray("businesses"));
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public static List<Restaurant> searchByCategory(float lat, float lon, List<String> categories){
        StringBuilder category = new StringBuilder();
        for(String s : categories){
            category.append(s);
            category.append(",");
        }
        if(category.charAt(category.length() - 1) == ','){
            category.deleteCharAt(category.length() - 1);
        }
        String query = String.format("latitude=%s&longitude=%s&categories=%s&limit=10&radius=30000",lat, lon, category.toString());
        String url = HOST + ENDPOINT + "?" + query;
        StringBuilder responseBody = new StringBuilder();
        try{
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer "+API_KEY);

            int responseCode = connection.getResponseCode(); //send request
            if(responseCode != 200) {
                return new ArrayList<>();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            reader.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            JSONObject businesses = new JSONObject(responseBody.toString());
            return getRestaurantList(businesses.getJSONArray("businesses"));
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
