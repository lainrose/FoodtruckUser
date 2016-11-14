package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import retrofit.Call;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiService {
    public static final String API_URL = "https://server-blackdog11.c9users.io/";
    @FormUrlEncoded
    @GET("/client/login_request")
    Call<UserModel> request_login(@Query("email") String email, @Query("password") String password);
}