package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.sign;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.gson.JsonObject;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.ApiService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main.FragmentMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText et_signup_email;
    private EditText et_signup_nick;
    private EditText et_signup_pw;
    private static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";
    private int signupStatus = 2; //1은 성공, 2는 실패, 3은 중복
    private Button bt_singup_fragment_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

        setupToolbar();

        et_signup_email = ((EditText) findViewById(R.id.et_signup_email));
        et_signup_pw = ((EditText) findViewById(R.id.et_signup_pw));
        et_signup_nick = ((EditText) findViewById(R.id.et_signup_nick));
       bt_singup_fragment_login = (Button) findViewById(R.id.bt_singup_login);


        bt_singup_fragment_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StartSingUp())
                {
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run() {
                            if(signupStatus == 1) {
                                Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                                Intent loginIntent = new Intent(SignupActivity.this, SigninActivity.class);
                                SignupActivity.this.startActivity(loginIntent);
                                SignupActivity.this.finish();
                            } else if (signupStatus == 2) {
                                Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                Toast.makeText(getApplicationContext(), "중복된 이메일이 있습니다.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, 1500);
                }
            }
        });
    }
    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("회원가입");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    private boolean check_email(String paramString) {
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(paramString).matches();
    }

    private boolean check_name(String paramString) {
        return Pattern.compile("(([ㄱ-힣0-9a-zA-Z]).{2,20})").matcher(paramString).matches();
    }

    private boolean check_pw(String paramString) {
        return Pattern.compile("(([A-Za-z0-9]).{7,20})").matcher(paramString).matches();
    }

    private Boolean StartSingUp()
    {
        if (check_email(this.et_signup_email.getText().toString()))
        {
            if (check_pw(this.et_signup_pw.getText().toString()))
            {
                if (check_name(this.et_signup_nick.getText().toString()))
                {
                    getSignUpRequest();
                    return true;
                }
                Toast.makeText(SignupActivity.this, "이름을 2자 이상 20자 이내로 입력해 주세요", Toast.LENGTH_SHORT).show();
                return false;
            }
            Toast.makeText(SignupActivity.this, "비밀번호를 8자이상 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
    }
        Toast.makeText(SignupActivity.this, "올바른 이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show();
        return false;
    }


    private void getSignUpRequest() {

        // TODO: 2016-12-02 이거 모델 안에서 바꿔주기
        JsonObject client_info = new JsonObject();
        client_info.addProperty("email", et_signup_email.getText().toString());
        client_info.addProperty("password", et_signup_pw.getText().toString());
        client_info.addProperty("nickName", et_signup_nick.getText().toString());

        Log.d("회원가입", client_info.toString());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://server-blackdog11.c9users.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<Integer> convertedContent = service.client_join(client_info);

        convertedContent.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body().toString() == "1") {
                    signupStatus = 1;
                } else if(response.body().toString() == "2") {
                    signupStatus = 2;
                } else {
                    signupStatus = 3;
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("실패", t.getMessage().toString());
            }
        });
    }
}
