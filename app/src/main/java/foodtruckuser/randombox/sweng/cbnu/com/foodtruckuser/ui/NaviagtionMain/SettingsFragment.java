package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.NaviagtionMain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.nightonke.boommenu.Util;
import com.pavelsikun.seekbarpreference.SeekBarPreferenceCompat;

import cn.pedant.SweetAlert.SweetAlertDialog;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.Utill;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.preference.PrefHelper;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.JoinMain;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.sign.PwChangeActivity;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences mPref;
    private Preference autoLogin;
    private Preference distance;
    private Preference category;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        Preference logInOut = (Preference)findPreference("loginout");

        category = (Preference)findPreference("category");
        autoLogin = (Preference)findPreference("emailAutoLogin");
        Preference password = (Preference)findPreference("password");

        Preference setLocation = (Preference)findPreference("alarm");
        distance = (Preference)findPreference("distance");
        final Preference alarm = (Preference)findPreference("neartruck");

        //로그아웃
        logInOut.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("로그아웃 하시겠습니까?")
                        //.setContentText("확인 버튼을 누르면 취소할 수 없습니다.")
                        .setCancelText("취소")
                        .setConfirmText("확인")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                PrefHelper.getInstance(getContext()).setPrefFacebookLogin("LOGOUT");
                                PrefHelper.getInstance(getContext()).setPrefEmailAutoLogin(false);
                                Utill.getInstance().MoveAcitivity(getContext(), JoinMain.class);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        }).show();
                return false;
            }
        });
        // 자동로그인 체크
        autoLogin.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
        {
            @Override /* TODO Auto-generated method stub */
            public boolean onPreferenceChange(Preference preference, Object newValue)
            {
                if(autoLogin.isEnabled() == true){
                    PrefHelper.getInstance(getContext()).setPrefFacebookLogin("LOGOUT");
                    PrefHelper.getInstance(getContext()).setPrefEmailAutoLogin(false);
                }
                else{
                    PrefHelper.getInstance(getContext()).setPrefFacebookLogin("LOGIN");
                    PrefHelper.getInstance(getContext()).setPrefEmailAutoLogin(true);
                }
                return true;
            }
        });
        password.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("패스워드를 변경하시겠습니까?")
                        //.setContentText("확인 버튼을 누르면 취소할 수 없습니다.")
                        .setCancelText("취소")
                        .setConfirmText("확인")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Utill.getInstance().MoveAcitivity(getContext(), PwChangeActivity.class);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        }).show();
                return false;
            }
        });
        setLocation.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("알림 위치 설정 화면으로\n이동하시겠습니까?")
                        //.setContentText("확인 버튼을 누르면 취소할 수 없습니다.")
                        .setCancelText("취소")
                        .setConfirmText("확인")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                                Utill.getInstance().MoveAcitivity(getContext(), ActivitySetLocationMap.class);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        }).show();
                return false;
            }
        });
        distance.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
        {
            @Override /* TODO Auto-generated method stub */
            public boolean onPreferenceChange(Preference preference, Object newValue)
            {
                //// TODO: 2016-12-02 id연결해서 못불러오겠음 나중에 처리
                return true;
            }
        });
        alarm.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
        {
            @Override /* TODO Auto-generated method stub */
            public boolean onPreferenceChange(Preference preference, Object newValue)
            {
                if(alarm.isEnabled() == true){
                    //// TODO: 2016-12-02 카테고리 전체닫기 설정해야되는데 모르겠음 나중에 처리
                }
                else{

                }
                return true;
            }
        });
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }
}
