package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service;

import java.util.List;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import retrofit.Call;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiService {
    public static final String API_URL = "https://server-blackdog11.c9users.io/";
    @GET("/client/foodtruck_list")
    Call<List<FoodTruckModel>> listFoodTrucks();
    @GET("/client/login_requset")
    Call<UserModel> request_login(@Query("email") String email, @Query("password") String password);
}