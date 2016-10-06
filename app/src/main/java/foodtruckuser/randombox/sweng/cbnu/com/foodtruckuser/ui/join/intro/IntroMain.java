package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.intro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.preference.PrefHelper;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.sign.SigninActivity;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.sign.SignupActivity;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main.MainActivity;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.mypage.User;
import me.relex.circleindicator.CircleIndicator;


public class IntroMain extends FragmentActivity implements View.OnClickListener{

    private IntroAdapter adapter;
    private ViewPager pager;
    private Context context = this;
    private Button bt_email_login;
    private Button bt_email_signup;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private CallbackManager callbackManager;
    private Profile profile;
    private Button facebookbtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.intromain);

        adapter = new IntroAdapter(getSupportFragmentManager());

        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        bt_email_login = (Button)findViewById(R.id.bt_email_login);
        bt_email_login.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
                //
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                Log.d("FaceBook :::", "id : " + newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        //sharedPreference = new SharedPreference(this);

        facebookbtn = (Button) findViewById(R.id.bt_faceebook_login);
        facebookbtn.setOnClickListener(facebookLoginListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private View.OnClickListener facebookLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //LoginManager - 요청된 읽기 또는 게시 권한으로 로그인 절차를 시작합니다.
            LoginManager.getInstance().logInWithReadPermissions(IntroMain.this,
                    Arrays.asList("public_profile", "user_friends"));
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            AccessToken.setCurrentAccessToken(loginResult.getAccessToken());
                                /* 페이스북 프로필을 가져온다*/
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    profile = Profile.getCurrentProfile();
                                    User.getInstance().setUserId( profile.getId());
                                    User.getInstance().setUserName( profile.getName());
                                    PrefHelper.getInstance(context).setUserId(User.getInstance().USER_ID);
                                    PrefHelper.getInstance(context).setUserId(User.getInstance().USER_NAME);

                                    Log.d("FaceBook :::", "id : " + User.getInstance().USER_ID);
                                    Log.d("FaceBook :::", "name : " + User.getInstance().USER_NAME);

                                    Intent mainIntent = new Intent(IntroMain.this, MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            }, 500);

                                /*
                                //사용 디바이스 핸드폰 번호 알아오기
                                TelephonyManager systemService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                                String user_phone = systemService.getLine1Number();
                                user_phone = user_phone.substring(user_phone.length() - 10, user_phone.length());
                                user_phone = "0" + user_phone;
                                */
                                /*
                                //안드로이드 기기 구글 동기화 이메일 주소 가져오기
                                Pattern emailPattern = Patterns.EMAIL_ADDRESS;
                                AccountManager am = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);
                                Account[] accounts = am.getAccounts();
                                String user_email = "";
                                for (Account account : accounts) {
                                    if (emailPattern.matcher(account.name).matches()) {
                                        user_email = account.name;
                                    }
                                }
                            */
                            /** SharedPreferences로 Facbook 정보 저장*/
                            /* 로그인될 시 세션을 유지하기 위해 환경 변수에 facebook login session 관리를 저장한다. */
                            PrefHelper.getInstance(context).setFirstFacebookLogin("LOGIN");
                        }
                        @Override
                        public void onCancel() {
                            Toast.makeText(IntroMain.this, "페이스북 로그인이 취소 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onError(FacebookException exception) {
                            Toast.makeText(IntroMain.this, "페이스북 로그인이 취소 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    };

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.bt_email_login:
                startActivity(new Intent(this, SigninActivity.class));
                break;
            case R.id.bt_email_signup:
                startActivity(new Intent(this, SignupActivity.class));
                break;
        }
    }


}