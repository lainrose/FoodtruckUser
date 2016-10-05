package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join;

/**
 * Created by AdminPond on 21/6/2558.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;


public class IntroOne extends Fragment {

    public static IntroOne newInstance() {
        IntroOne fragment = new IntroOne();
        return fragment;
    }

    public IntroOne() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.intro_one, null);
        return root;
    }
}