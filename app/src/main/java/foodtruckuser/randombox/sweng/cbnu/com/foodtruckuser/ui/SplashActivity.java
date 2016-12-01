package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.preference.PrefHelper;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.JoinMain;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main.FragmentMain;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                /** SharedPreference 환경 변수 사용 **/
                /** prefs.getString() return값이 LOGOUT이라면 2번째 함수를 대입한다. **/

                String facebook_login = PrefHelper.getInstance(context).getPrefFacebookLogin();
                Log.d("FACEBOOK_LOGIN::::::", facebook_login);
                /** 환경 변수 중 로그인 부분을 체킹하여 Activity 전환 */
                // 페이스북 로그인
                Log.d("자동 로그인", ""+PrefHelper.getInstance(context).getPrefEmailAutoLogin());
                if(facebook_login.equals("LOGIN") || PrefHelper.getInstance(context).getPrefEmailAutoLogin() == true)
                {
                    Intent loginIntent = new Intent(SplashActivity.this, FragmentMain.class);
                    SplashActivity.this.startActivity(loginIntent);
                    SplashActivity.this.finish();
                }
                // 페이스북 로그아웃
                else if(facebook_login.equals("LOGOUT") || PrefHelper.getInstance(context).getPrefEmailAutoLogin() == false)
                {
                    Intent loginIntent = new Intent(SplashActivity.this, JoinMain.class);
                    SplashActivity.this.startActivity(loginIntent);
                    SplashActivity.this.finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}