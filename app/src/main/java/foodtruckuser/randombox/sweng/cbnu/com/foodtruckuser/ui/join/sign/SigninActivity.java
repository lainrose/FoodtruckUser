package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Ui.join.sign;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Model.UserModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Service.ApiService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Ui.main.FragmentMain;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class SigninActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText et_signin_email;
    private EditText et_signin_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        et_signin_email = ((EditText) findViewById(R.id.et_signin_email));
        et_signin_pw = ((EditText) findViewById(R.id.et_signin_pw));
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("이메일로 로그인");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        //actionBar.setHomeAsUpIndicator(R.drawable.button_back); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                //verridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    public void Onclick_Signin(View v){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://server-test-opwe37.c9users.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<UserModel> call = service.request_login(et_signin_email.getText().toString(), et_signin_pw.getText().toString());
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Response<UserModel> response, Retrofit retrofit) {
                Log.d("Response status code: ", String.valueOf(response.code()));

                // isSuccess is true if response code => 200 and <= 300
                if (!response.isSuccess()) {
                    // print response body if unsuccessful
                    try {
                        Log.d("response unsuccessful: ", response.errorBody().string());
                    } catch (IOException e) {
                        // do nothing
                    }
                    return;
                }
                // if parsing the JSON body failed, `response.body()` returns null
                UserModel decodedResponse = response.body();
                if (decodedResponse == null) {
                    return;
                }
                Log.d("TAG", String.valueOf(decodedResponse.getUserId()));
                Log.d("TAG", decodedResponse.getUserMail());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("Failure", "onFailure");
                Log.d("Failure", t.getMessage());
            }
        });

        Toast.makeText(this, "환영합니다. 푸드트럭", Toast.LENGTH_LONG).show();
        Intent loginIntent = new Intent(SigninActivity.this, FragmentMain.class);
        startActivity(loginIntent);
        finish();
    }


}
