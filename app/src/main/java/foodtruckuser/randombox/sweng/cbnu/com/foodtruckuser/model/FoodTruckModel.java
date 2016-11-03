package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model;

/**
 * Created by son on 11/1/16.
 */
public class FoodTruckModel {

    private  String FT_ID;
    private  String FT_NAME;
    private  int FT_CATEGORY;        //
    private  String FT_TAG;
    private  String FT_AVERAGE;
    private  Boolean FT_LIKE = false;
    private  String FT_START;
    private  String FT_PAYMENT;
    private  int FT_IMAGE;
    private  String FT_X;
    private  String FT_Y;

    public  String getFtId() {
        return FT_ID;
    }

    public  void setFtId(String ftId) {
        FT_ID = ftId;
    }

    public  String getFtName() {
        return FT_NAME;
    }

    public  void setFtName(String ftName) {
        FT_NAME = ftName;
    }

    public  int getFtCategory() {
        return FT_CATEGORY;
    }

    public  void setFtCategory(int ftCategory) {
        FT_CATEGORY = ftCategory;
    }

    public  String getFtTaag() {
        return FT_TAG;
    }

    public  void setFtTaag(String ftTaag) {
        FT_TAG = ftTaag;
    }

    public  String getFtAverage() {
        return FT_AVERAGE;
    }

    public  void setFtAverage(String ftAverage) {
        FT_AVERAGE = ftAverage;
    }

    public  Boolean getFtLike() {
        return FT_LIKE;
    }

    public  void setFtLike(Boolean ftLike) {
        FT_LIKE = ftLike;
    }

    public  String getFtStart() {
        return FT_START;
    }

    public  void setFtStart(String ftStart) {
        FT_START = ftStart;
    }

    public  String getFtPayment() {
        return FT_PAYMENT;
    }

    public  void setFtPayment(String ftPayment) {
        FT_PAYMENT = ftPayment;
    }

    public  int getFtImage() {
        return FT_IMAGE;
    }

    public  void setFtImage(int ftImage) {
        FT_IMAGE = ftImage;
    }

    public  String getFtX() {
        return FT_X;
    }

    public  void setFtX(String ftX) {
        FT_X = ftX;
    }

    public  String getFtY() {
        return FT_Y;
    }

    public  void setFtY(String ftY) {
        FT_Y = ftY;
    }
}

