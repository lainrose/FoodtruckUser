package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.sign;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.ApiService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main.FragmentMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// TODO: 2016-11-17 안드로이드에서 서버로 로그인 정보 넘어갈 때 암호화하기
public class PwChangeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText et_pw_input;
    private EditText et_pw_reinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        et_pw_input = ((EditText) findViewById(R.id.et_pw_input));
        et_pw_reinput = ((EditText) findViewById(R.id.et_pw_reinput));
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("패스워드 변경");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
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

    public void Onclick_PwChange(View v) {


    }
}
