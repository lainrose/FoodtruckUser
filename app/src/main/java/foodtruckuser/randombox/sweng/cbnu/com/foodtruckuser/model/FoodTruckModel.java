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
    @SerializedName("truck_image")
    private FoodTruckUrlModel FT_IMAGE_URL;
    @SerializedName("lat")
    private Double FT_LAT; //위도 Y축
    @SerializedName("lng")
    private Double FT_LNG; //경도 X축

    private String FT_LOCATIONNAME;
    private int FT_IMAGE;


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

    public String getFT_LOCATIONNAME() {
        return FT_LOCATIONNAME;
    }

    public void setFT_LOCATIONNAME(String FT_LOCATIONNAME) {
        this.FT_LOCATIONNAME = FT_LOCATIONNAME;
    }

    public FoodTruckUrlModel getFT_IMAGE_URL() {
        return FT_IMAGE_URL;
    }

    public void setFT_IMAGE_URL(FoodTruckUrlModel FT_IMAGE_URL) {
        this.FT_IMAGE_URL = FT_IMAGE_URL;
    }

    public Double getFT_LAT() {
        return FT_LAT;
    }

    public void setFT_LAT(Double FT_LAT) {
        this.FT_LAT = FT_LAT;
    }

    public Double getFT_LNG() {
        return FT_LNG;
    }

    public void setFT_LNG(Double FT_LNG) {
        this.FT_LNG = FT_LNG;
    }


    //    public static FoodTruckModel parseJSON(String response) {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
//        Type collectionType = new TypeToken<List<FoodTruckModel>>(){}.getType();
//        Gson gson = gsonBuilder.create();
//        FoodTruckModel foodTruckModel = gson.fromJson(response, FoodTruckModel.class);
//        return foodTruckModel;
//    }
    public class FoodTruckUrlModel {
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

