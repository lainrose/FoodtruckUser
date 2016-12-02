package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service;

import com.google.gson.JsonObject;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FestiveModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.MenuModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.ReviewModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    //public static final String API_URL = "https://server-blackdog11.c9users.io/";
    //로그인 요청
    @FormUrlEncoded
    @POST("/client/login_request")
    Call<UserModel> request_login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/client/client_join")
    Call<Integer> client_join(@Field("client_info") JsonObject client_info);

    //FragmentHome에서 푸드트럭 리스트 요청
    @FormUrlEncoded
    @POST("/client/foodtruck_list")
    Call<ArrayList<FoodTruckModel>> foodtruck_list(@Field("category") int category);
    //@GET("/client/foodtruck_list")
    //Call<ArrayList<FoodTruckModel>> listFoodTrucks(@Query("category") int category);

    @GET("/common/truck_menus")
    Call<ArrayList<MenuModel>> truck_menus(@Query("foodtruck_id") String id);

    @GET("common/foodtruck_reviews")
    Call<ArrayList<ReviewModel>> foodtruck_reviews(@Query("foodtruck_id") String id);

    @GET("/client/add_like_truck")
    Call<Boolean> add_like_truck(@Query("client_id") String client_id, @Query("foodtruck_id") String foodtruck_id);

    @GET("/client/delete_like_truck")
    Call<Boolean> delete_like_truck(@Query("client_id") String client_id, @Query("foodtruck_id") String foodtruck_id);

    @FormUrlEncoded
    @POST("/client/like_truck_list")
    Call<ArrayList<FoodTruckModel>> like_truck_list(@Field("client_id") String id);

    @FormUrlEncoded
    @POST("/client/save_review")
    Call<FoodTruckModel> save_review(@Field("review_info") JsonObject review_info);

    @GET("/client/save_token")
    Call<Boolean> save_token(@Query("token") String token, @Query("client_id") String id);

    @Multipart
    @POST("/client/save_festival")
    Call<Integer> save_festival(@Part MultipartBody.Part file, @Part("festival_info") JsonObject festival_info);

    // ======================= 행사 관련 요청 =======================
    //행사 정보 요청
    @GET("/common/festival_info")
    Call<ArrayList<FestiveModel>> festival_info();
//
//    //행사 참가신청 요청
//    @GET("/owner/request_festival")
//    Call<Boolean> request_festival(@Query("owner_id") int owner_id, @Query("festival_id") int festival_id);

    //행사 참가신청 취소 요청
    @GET("/owner/request_cancle_festival")
    Call<Boolean> request_cancle_festival(@Query("owner_id") int owner_id, @Query("festival_id") int festival_id);

    //내가 참가신청한 행사 정보 요청
    @GET("/owner/selected_festival_info")
    Call<ArrayList<FestiveModel>> selected_festival(@Query("onwer_id") int owner_id);

}