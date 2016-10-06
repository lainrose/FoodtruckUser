package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;

public class IntroFour extends Fragment {

    public static IntroFour newInstance() {
        IntroFour fragment = new IntroFour();
        return fragment;
    }

    public IntroFour() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.intro_four, null);
        return root;
    }
}