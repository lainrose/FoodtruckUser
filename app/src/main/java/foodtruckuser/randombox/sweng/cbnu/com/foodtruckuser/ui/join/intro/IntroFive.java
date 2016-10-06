package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;

public class IntroFive extends Fragment {
        private Button btnStart;

        public static IntroFive newInstance() {
            IntroFive fragment = new IntroFive();
            return fragment;
        }

        public IntroFive() {
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.intro_five, null);
        return root;
    }
}