package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.baoyz.widget.PullRefreshLayout;
import java.util.ArrayList;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.ServiceGenerator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.Utill;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.TruckAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.MenuModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMytruck extends Fragment {

    private RecyclerView myRecyclerView;
    private PullRefreshLayout layout;
    final ArrayList<FoodTruckModel> listitems = new ArrayList<>();
    private TruckAdapter truckAdapter;



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

        requestMyTruckList(UserModel.getInstance().getUserId());


        return view;
    }

    public void showMenuCardViewList(ArrayList<FoodTruckModel> listitems) {
        truckAdapter = new TruckAdapter(getActivity(),listitems, "FragmentMytruck");
        truckAdapter.notifyDataSetChanged();
        if (listitems.size() >= 0 & myRecyclerView != null) {
            myRecyclerView.setAdapter(truckAdapter);
        }
    }

    public void requestMyTruckList(String user_id) {
        listitems.clear();

        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<ArrayList<FoodTruckModel>> convertedContent = service.like_truck_list(user_id);
        convertedContent.enqueue(new Callback<ArrayList<FoodTruckModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FoodTruckModel>> call, Response<ArrayList<FoodTruckModel>> response) {
                ArrayList<FoodTruckModel> myTruckList = response.body();

//                Log.d("TAG", "바디: " + response.body().toString());

                for (FoodTruckModel myTruck: myTruckList) {
                    listitems.add(myTruck);
                }

                showMenuCardViewList(listitems); //서버에서 받아오면 카드뷰 그려주게하기
            }

            @Override
            public void onFailure(Call<ArrayList<FoodTruckModel>> call, Throwable t) {
                Log.d("실패", "onFailure: ");
                Log.d("TAG", t.getMessage());
            }
        });
    }
}
