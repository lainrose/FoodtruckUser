package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.sign;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.preference.PrefHelper;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.ApiService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main.FragmentMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// TODO: 2016-11-17 안드로이드에서 서버로 로그인 정보 넘어갈 때 암호화하기
public class SigninActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText et_signin_email;
    private EditText et_signin_pw;
    private CheckBox bt_auto_login;
    private SharedPreferences mPref;
    private SharedPreferences.Editor editor;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        et_signin_email = ((EditText) findViewById(R.id.et_pw_input));
        et_signin_pw = ((EditText) findViewById(R.id.et_pw_reinput));
        bt_auto_login = (CheckBox)findViewById(R.id.bt_auto_login);
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
        switch (item.getItemId()) {
            case android.R.id.home: {
                //overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                finish();
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
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

    public void Onclick_Signin(View v) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://server-blackdog11.c9users.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<UserModel> convertedContent = service.request_login(et_signin_email.getText().toString(), et_signin_pw.getText().toString());
        convertedContent.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.d("Response status code: ", String.valueOf(response.code()));

                // if parsing the JSON body failed, `response.body()` returns null
                UserModel.USER_INFO = response.body();
                if (UserModel.getInstance() == null) {
                    Log.d("TAG", "아이디비번오류");
                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                } else if(String.valueOf(UserModel.getInstance().getUserMail()).equals(et_signin_email.getText().toString())) {
                    Log.d("TAGG", "로그인성공!");
                    if(bt_auto_login.isChecked()){
                        PrefHelper.getInstance(mContext).setPrefEmailAutoLogin(true);
                    }
                    else{
                        PrefHelper.getInstance(mContext).setPrefEmailAutoLogin(false);
                    }
                    Toast.makeText(getApplicationContext(), "환영합니다. 겟잇트럭", Toast.LENGTH_LONG).show();
                    Intent loginIntent = new Intent(SigninActivity.this, FragmentMain.class);
                    startActivity(loginIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("실패", t.getMessage().toString());
            }
        });
    }
}
