package yeppy.service.models;

public class LikeRequestModel {
    private String userId;
    private String businessId;
    private String[] categories;

    public LikeRequestModel(){

    }

    public LikeRequestModel(String userId, String businessId, String[] categories) {
        this.userId = userId;
        this.businessId = businessId;
        this.categories = categories;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }
}
