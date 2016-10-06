package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.sign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;

public class SignupActivity extends AppCompatActivity {

    private EditText et_signup_fragment_email;
    private EditText et_signup_fragment_nick;
    private EditText et_signup_fragment_pw;
    private TextView tv_singup_fragment_already_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        this.et_signup_fragment_email = ((EditText)findViewById(R.id.et_signup_fragment_email));
        this.et_signup_fragment_pw = ((EditText)findViewById(R.id.et_signup_fragment_pw));
        this.et_signup_fragment_nick = ((EditText)findViewById(R.id.et_signup_fragment_nick));
    }

    private boolean check_email(String paramString)
    {
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(paramString).matches();
    }

    private boolean check_name(String paramString)
    {
        return Pattern.compile("(([ㄱ-힣0-9a-zA-Z]).{2,20})").matcher(paramString).matches();
}

    private boolean check_pw(String paramString)
    {
        return Pattern.compile("(([A-Za-z0-9]).{7,20})").matcher(paramString).matches();
    }

    public void StartSingUp()
    {
        if (check_email(this.et_signup_fragment_email.getText().toString()))
        {
            if (check_pw(this.et_signup_fragment_pw.getText().toString()))
            {
                if (check_name(this.et_signup_fragment_nick.getText().toString()))
                {
                    getSignUpRequest();
                    return;
                }
                Toast.makeText(SignupActivity.this, "이름을 2자 이상 20자 이내로 입력해 주세요", 0).show();
                return;
            }
            Toast.makeText(SignupActivity.this, "비밀번호를 8자이상 입력해주세요", 0).show();
            return;
        }
        Toast.makeText(SignupActivity.this, "올바른 이메일 형식이 아닙니다", 0).show();
    }

    private void getSignUpRequest()
    {
        //showProgressDialog("회원가입 중입니다...");
    }
}
