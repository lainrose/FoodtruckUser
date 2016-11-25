package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service;

import com.google.gson.JsonObject;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

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
    Call<ArrayList<FoodTruckModel>> listFoodTrucks(@Field("category") int category);
    //@GET("/client/foodtruck_list")
    //Call<ArrayList<FoodTruckModel>> listFoodTrucks(@Query("category") int category);


}