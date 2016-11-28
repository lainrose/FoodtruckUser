package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.NaviagtionMain;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }

}
