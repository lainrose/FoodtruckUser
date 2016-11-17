package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service;

import java.util.ArrayList;
import java.util.List;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface ApiService {
    //public static final String API_URL = "https://server-blackdog11.c9users.io/";
    //로그인 요청
    @FormUrlEncoded
    @POST("/client/login_requset")
    Call<UserModel> request_login(@Field("email") String email/*, @Query("password") String password*/);

    //FragmentHome에서 푸드트럭 리스트 요청
    @GET("/client/foodtruck_list")
    Call<ArrayList<FoodTruckModel>> listFoodTrucks();

}