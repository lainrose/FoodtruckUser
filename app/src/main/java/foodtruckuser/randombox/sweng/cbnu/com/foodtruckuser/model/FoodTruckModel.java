package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by son on 11/1/16.
 */


public class FoodTruckModel {

    @SerializedName("owner_id")
    private String FT_OWNER_ID;
    @SerializedName("name")
    private String FT_NAME;
    @SerializedName("category")
    private int FT_CATEGORY;
    @SerializedName("tag")
    private String FT_TAG;
    @SerializedName("rating")
    private String FT_RATING;
    @SerializedName("like")
    private Boolean FT_LIKE = false;
    @SerializedName("open")
    private String FT_START;
    @SerializedName("payment_card")
    private String FT_PAYMENT;
    @SerializedName("image")
    private int FT_IMAGE;
    @SerializedName("positionX")
    private String FT_X;
    @SerializedName("positionY")
    private String FT_Y;

    public String getFtOwnerId() {
        return FT_OWNER_ID;
    }

    public void setFtOwnerId(String ftOwnerId) {
        FT_OWNER_ID = ftOwnerId;
    }

    public String getFtName() {
        return FT_NAME;
    }

    public void setFtName(String ftName) {
        FT_NAME = ftName;
    }

    public int getFtCategory() {
        return FT_CATEGORY;
    }

    public void setFtCategory(int ftCategory) {
        FT_CATEGORY = ftCategory;
    }

    public String getFtTaag() {
        return FT_TAG;
    }

    public void setFtTaag(String ftTaag) {
        FT_TAG = ftTaag;
    }

    public String getFtRating() {
        return FT_RATING;
    }

    public void setFtRating(String ftRating) {
        FT_RATING = ftRating;
    }

    public Boolean getFtLike() {
        return FT_LIKE;
    }

    public void setFtLike(Boolean ftLike) {
        FT_LIKE = ftLike;
    }

    public String getFtStart() {
        return FT_START;
    }

    public void setFtStart(String ftStart) {
        FT_START = ftStart;
    }

    public String getFtPayment() {
        return FT_PAYMENT;
    }

    public void setFtPayment(String ftPayment) {
        FT_PAYMENT = ftPayment;
    }

    public int getFtImage() {
        return FT_IMAGE;
    }

    public void setFtImage(int ftImage) {
        FT_IMAGE = ftImage;
    }

    public String getFtX() {
        return FT_X;
    }

    public void setFtX(String ftX) {
        FT_X = ftX;
    }

    public String getFtY() {
        return FT_Y;
    }

    public void setFtY(String ftY) {
        FT_Y = ftY;
    }

//    public static FoodTruckModel parseJSON(String response) {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
//        Type collectionType = new TypeToken<List<FoodTruckModel>>(){}.getType();
//        Gson gson = gsonBuilder.create();
//        FoodTruckModel foodTruckModel = gson.fromJson(response, FoodTruckModel.class);
//        return foodTruckModel;
//    }

}

