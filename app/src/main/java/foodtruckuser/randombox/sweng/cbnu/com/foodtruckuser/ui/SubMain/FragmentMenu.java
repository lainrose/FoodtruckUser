package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.MenuModel;

public class FragmentMenu extends Fragment {

    private static RecyclerView myRecyclerView;
    private static String navigateFrom;//String to get Intent Value
    private MenuAdapter menuAdapter;
    private ArrayList<MenuModel> listitems;

    //String and Integer array for Recycler View Items
    public static final String[] TITLES= {"나 햄벅","나 햄벅","나 햄벅","나 햄벅","나 햄벅"
            ,"나 햄벅","나 햄벅","나 햄벅","나 햄벅"};
    public static final Integer[] IMAGES= {R.drawable.ic_1,R.drawable.ic_2,R.drawable.ic_3,
            R.drawable.ic_4,R.drawable.ic_5, 0, 0, 0, 0};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        //리사이클뷰(카드뷰)
        myRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        myRecyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager MyLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        MyLayoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(MyLayoutManager);
        populatRecyclerView();
        return view;
    }
    // populate the list view by adding data to arraylist
    private void populatRecyclerView() {
        listitems = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            listitems.add(new MenuModel(TITLES[i],IMAGES[i]));
        }
        menuAdapter = new MenuAdapter(getContext(), listitems);
        myRecyclerView.setAdapter(menuAdapter);// set adapter on recyclerview
        menuAdapter.notifyDataSetChanged();// Notify the adapter

    }
}
