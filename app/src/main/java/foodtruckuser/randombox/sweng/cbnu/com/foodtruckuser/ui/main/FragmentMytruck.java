package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.baoyz.widget.PullRefreshLayout;
import java.util.ArrayList;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.Utill;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Model.FoodTruckModel;

public class FragmentMytruck extends Fragment {

    private RecyclerView myRecyclerView;
    private PullRefreshLayout layout;
    final ArrayList<FoodTruckModel> listitems = new ArrayList<>();
    private MyAdapter myAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mytruck, container, false);

        //리스트 리프레쉬
        layout = (PullRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        layout.setColorSchemeColors(Utill.getInstance().getRandomColor(),Utill.getInstance().getRandomColor(),
                                        Utill.getInstance().getRandomColor(),Utill.getInstance().getRandomColor());
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.setRefreshing(false);
                    }
                }, 4000);
            }
        });
        layout.setRefreshing(false);

        myRecyclerView = (RecyclerView)view.findViewById(R.id.cardView);
        myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(MyLayoutManager);
        //listitems.addAll(MyAdapter.getInstance().getMyTruckList());
        myAdapter = new MyAdapter(getActivity(),listitems);
        if (listitems.size() >= 0 & myRecyclerView != null) {
            myRecyclerView.setAdapter(myAdapter);
        }

        return view;
    }
}
