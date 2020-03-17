package yeppy.service.models;

public class SearchRequestModel {
    private String userId;
    private String term;
    private float latitude;
    private float longitude;

    public SearchRequestModel(){

    }

    public SearchRequestModel(String userId, String term, float latitude, float longitude) {
        this.userId = userId;
        this.term = term;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
