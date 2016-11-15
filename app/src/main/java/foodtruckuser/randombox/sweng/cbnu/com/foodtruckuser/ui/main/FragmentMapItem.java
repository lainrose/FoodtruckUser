package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;

public class FragmentMapItem extends Fragment {

    public static FragmentMapItem newInstance() {
        FragmentMapItem fragment = new FragmentMapItem();
        return fragment;
    }

    public FragmentMapItem() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map_item, null);
        return root;
    }
}