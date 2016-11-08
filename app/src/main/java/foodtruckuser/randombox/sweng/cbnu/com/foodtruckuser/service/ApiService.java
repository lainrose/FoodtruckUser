package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Service;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Model.UserModel;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiService {
    //public static final String API_URL = "https://server-test-opwe37.c9users.io/";
    //@FormUrlEncoded
    @GET("/client/login_request")
    Call<UserModel> request_login(@Query("email") String email, @Query("password") String password);
}