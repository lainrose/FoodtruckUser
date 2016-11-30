package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by son on 11/1/16.
 */


public class FoodTruckModel implements Serializable {
    public static FoodTruckModel TRUCK_INFO;

    @SerializedName("id")
    private String FT_ID;
    @SerializedName("owner_id")
    private String FT_OWNER_ID;
    @SerializedName("name")
    private String FT_NAME;
    @SerializedName("category")
    private int FT_CATEGORY;
    @SerializedName("tag")
    private String FT_TAG;
    @SerializedName("rating")
    private double FT_RATING;
    @SerializedName("like")
    private int FT_LIKE_NUM;
    private boolean FT_LIKE; //눌려있냐 안눌려있냐..//곧 없앤다.
    @SerializedName("open")
    private boolean FT_isOPEN;
    @SerializedName("payment_card")
    private String FT_PAYMENT;
    @SerializedName("image")
    private FoodTruckUrlModel FT_IMAGE_URL;
    @SerializedName("lat")
    private Double FT_LAT; //위도 Y축
    @SerializedName("lng")
    private Double FT_LNG; //경도 X축
    @SerializedName("review_num")
    private int FT_REVIEW_NUM; //등록된 리뷰 수
    @SerializedName("phone_number")
    private String FT_PHONE_NUMBER;

    private String FT_LOCATIONNAME;
    //private int FT_IMAGE;

    private String   titleTextView = "스테이크 하우스"; //스테이크 하우스
    private String   hashTextView = "#목살 스테이크 #샐러드 #목살"; //해쉬태그
    private int   reviewTextView = 123; //리뷰 갯수
    private int   likeTextView = 321; //좋아요 갯수
    //    private Float   ratingTextView = 2.0f; //평점 수
//    private Boolean  openText = false; //영업여부 텍스트
//    private String   openingText = "영업 중"; //영업중 표시
//    private String   openingText1 = "11월 24일 오후 19시 20분"; //영업중 표시
//    private String   openingText2 = "9시간 22분 경과"; //영업중 표시
//    private String   addressTextView = "전라북도 전주시 덕진구 덕진동1가 공과대학 5호관 507호"; //주소
// TODO: 2016-11-28 구글맵 API 이용해서 거리 구하기.https://developers.google.com/maps/documentation/distance-matrix/intro
    private String   timerTextView = "현위치 기준 약 12분 거리";
    private String   phoneTextView = "032-561-1416";
    private String opentimeTextView = "평일 10AM ~ 9PM";
    private int   emoticonText1 = 9 ; //맛있다 텍스트
    private int   emoticonText2 = 4; //괜찮다 텍스트
    private int   emoticonText3 = 3; //별로 텍스트
    private String   btn_review_more; //리뷰 더보기
    private String   visitHomepage = "http://www.naver.com"; //홈페이지 방문

    public static synchronized FoodTruckModel getInstance() {
        if (TRUCK_INFO == null) {
            TRUCK_INFO  = new FoodTruckModel();
        }
        return TRUCK_INFO;
    }

    //눌렸나 안눌렸나에 쓰는거. 곧 없애버린ㄴ다.
    public boolean isFT_LIKE() {
        return FT_LIKE;
    }

    public void setFT_LIKE(boolean FT_LIKE) {
        this.FT_LIKE = FT_LIKE;
    }

    public int getFT_REVIEW_NUM() {
        return FT_REVIEW_NUM;
    }

    public void setFT_REVIEW_NUM(int FT_REVIEW_NUM) {
        this.FT_REVIEW_NUM = FT_REVIEW_NUM;
    }

    public String getFT_ID() {
        return FT_ID;
    }

    public void setFT_ID(String FT_ID) {
        this.FT_ID = FT_ID;
    }

    public String getFT_PHONE_NUMBER() {
        return FT_PHONE_NUMBER;
    }

    public void setFT_PHONE_NUMBER(String FT_PHONE_NUMBER) {
        this.FT_PHONE_NUMBER = FT_PHONE_NUMBER;
    }

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

    public String getFtTag() {
        return FT_TAG;
    }

    public void setFtTag(String ftTag) {
        FT_TAG = ftTag;
    }

    public int getFT_LIKE_NUM() {
        return FT_LIKE_NUM;
    }

    public void setFT_LIKE_NUM(int FT_LIKE_NUM) {
        this.FT_LIKE_NUM = FT_LIKE_NUM;
    }

    public boolean isFT_isOPEN() {
        return FT_isOPEN;
    }

    public void setFT_isOPEN(boolean FT_isOPEN) {
        this.FT_isOPEN = FT_isOPEN;
    }

    public double getFtRating() {
        return FT_RATING;
    }

    public void setFtRating(double ftRating) {
        FT_RATING = ftRating;
    }

    public String getFtPayment() {
        return FT_PAYMENT;
    }

    public void setFtPayment(String ftPayment) {
        FT_PAYMENT = ftPayment;
    }

//    public int getFtImage() {
//        return FT_IMAGE;
//    }
//
//    public void setFtImage(int ftImage) {
//        FT_IMAGE = ftImage;
//    }

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






    public String getTimerTextView() {
        return timerTextView;
    }

    public void setTimerTextView(String timerTextView) {
        this.timerTextView = timerTextView;
    }

    public String getPhoneTextView() {
        return phoneTextView;
    }

    public void setPhoneTextView(String phoneTextView) {
        this.phoneTextView = phoneTextView;
    }

    public String getOpentimeTextView() {
        return opentimeTextView;
    }

    public void setOpentimeTextView(String opentileTextView) {
        this.opentimeTextView = opentileTextView;
    }

    public int getEmoticonText1() {
        return emoticonText1;
    }

    public void setEmoticonText1(int emoticonText1) {
        this.emoticonText1 = emoticonText1;
    }

    public int getEmoticonText2() {
        return emoticonText2;
    }

    public void setEmoticonText2(int emoticonText2) {
        this.emoticonText2 = emoticonText2;
    }

    public int getEmoticonText3() {
        return emoticonText3;
    }

    public void setEmoticonText3(int emoticonText3) {
        this.emoticonText3 = emoticonText3;
    }

    public String getBtn_review_more() {
        return btn_review_more;
    }

    public void setBtn_review_more(String btn_review_more) {
        this.btn_review_more = btn_review_more;
    }

    public String getVisitHomepage() {
        return visitHomepage;
    }

    public void setVisitHomepage(String visitHomepage) {
        this.visitHomepage = visitHomepage;
    }


    //    public static FoodTruckModel parseJSON(String response) {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
//        Type collectionType = new TypeToken<List<FoodTruckModel>>(){}.getType();
//        Gson gson = gsonBuilder.create();
//        FoodTruckModel foodTruckModel = gson.fromJson(response, FoodTruckModel.class);
//        return foodTruckModel;
//    }
    public class FoodTruckUrlModel implements Serializable {
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

