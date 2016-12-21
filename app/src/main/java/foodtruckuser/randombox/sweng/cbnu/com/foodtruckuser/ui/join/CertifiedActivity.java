package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Random;
import java.util.regex.Pattern;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.Utill;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.GMailSender;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.sign.SigninActivity;

public class CertifiedActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etCertifiedEmail;
    private EditText et_signup_nick;
    private EditText etCertifiedNum;
    private Button btCertified;
    private Button btSendCertified;
    private int CertifiedNum;
    private String strCertified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certified_activity);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        setupToolbar();
        etCertifiedEmail = ((EditText) findViewById(R.id.etCertifiedEmail));
        etCertifiedNum = ((EditText) findViewById(R.id.etCertifiedNum));
        btCertified = (Button) findViewById(R.id.btCertified);
        btSendCertified = (Button) findViewById(R.id.btSendCertified);
        btSendCertified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Random rand = new Random(System.currentTimeMillis());
                    CertifiedNum = rand.nextInt(9990) + 1000; //1000~9999까지 숫자를 랜덤으로 받는다.
                    if (!check_email(etCertifiedEmail.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "이메일 형식이 올바르지 않습니다.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("RANDOM", Integer.toString(CertifiedNum));//랜덤함수 로그로 출력
                        strCertified = Integer.toString(CertifiedNum);//랜덤함수를 string변수로 변환
                        GMailSender mail = new GMailSender("kula9393@gmail.com", "nsegkzyioslyafcw");
                        try {
                            //순서대로, 제목 - 본문 - 보내는 사람 메일 - 받는 사람 메일
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            mail.sendMail("겟잇트럭 인증번호 발송메일입니다.", "귀하의 인증번호는 " + strCertified + " 입니다.", "GetEat@naver.com", etCertifiedEmail.getText().toString());
                            Toast.makeText(getApplicationContext(), "해당 메일로 인증번호가 전송되었습니다.",
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("SendMail", e.getMessage(), e);
                        }
                    }
            }
        });
        btCertified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCertifiedNum.getText().toString().equals(strCertified)){
                    Toast.makeText(getApplicationContext(), "인증이 완료되었습니다.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "인증번호가 올바르지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("인증 받기");
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

    private boolean check_pw(String paramString) {
        return Pattern.compile("(([A-Za-z0-9]).{7,20})").matcher(paramString).matches();
    }

}
