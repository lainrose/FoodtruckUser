package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.NaviagtionMain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;

import cn.pedant.SweetAlert.SweetAlertDialog;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.preference.PrefHelper;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.JoinMain;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.sign.PwChangeActivity;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    Preference autoLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        Preference logInOut = (Preference)findPreference("loginout");
        autoLogin = (Preference)findPreference("emailAutoLogin");
        Preference password = (Preference)findPreference("password");
        Preference alarm = (Preference)findPreference("alarm");
        Preference distance = (Preference)findPreference("distance");
        Preference neartruck = (Preference)findPreference("neartruck");

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
                                Intent intent = new Intent(getContext(), JoinMain.class);
                                startActivity(intent);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                //
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
                                Intent intent = new Intent(getContext(), PwChangeActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                //
                            }
                        }).show();
                return false;
            }
        });
        alarm.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                return false;
            }
        });
        distance.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                return false;
            }
        });
        neartruck.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                return false;
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
