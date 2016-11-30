package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import com.baoyz.widget.PullRefreshLayout;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Eases.EaseType;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.OrderType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;
import java.util.ArrayList;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.ServiceGenerator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.Utill;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.TruckAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.ApiService;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentHome extends Fragment implements SearchView.OnQueryTextListener {

    private boolean CategorySacn = false;

    //플로팅아이콘 처리 변수
    private boolean init = false;
    private BoomMenuButton boomMenuButton;
    private RecyclerView myRecyclerView;
    private ViewTreeObserver viewTreeObserver;
    private PullRefreshLayout layout;
    private TruckAdapter truckAdapter;

    // 리스트에 들어갈 항목들
    ArrayList<FoodTruckModel> listItems = new ArrayList<>();
    private ArrayList<FoodTruckModel> categoryFilteredModelList = new ArrayList<>();
    private String FT_NAME[] = {"도현트럭", "의범트럭",
            "영빈트럭", "현표트럭", "현정트럭"};
    private int FT_CATEGORY[] = {1, 2, 3, 3, 5};
    private String FT_PAYMENT[] = {"카드", "현금", "카드/현금", "현금", "카드"};
    private int FT_IMAGES[] = {R.drawable.truck1, R.drawable.truck2, R.drawable.truck3,
            R.drawable.truck4, R.drawable.truck5};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //리스트 리프레쉬
        layout = (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        layout.setColorSchemeColors(Utill.getInstance().getRandomColor(), Utill.getInstance().getRandomColor(),
                Utill.getInstance().getRandomColor(), Utill.getInstance().getRandomColor());
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

        //리사이클뷰(카드뷰)
        myRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(MyLayoutManager);
        initFT();

        //onResponse에 카드뷰 그려주는 부분 넣어서 해결
//        new Handler().postDelayed(new Runnable() {// 1 초 후에 실행
//            @Override
//            public void run() {
//                showCardViewList(listItems); //실제로 카드뷰에 서버로부터 받아온 푸드트럭 객체 추가.
//            }
//        }, 1000);

        //플로팅 아이콘
        boomMenuButton = (BoomMenuButton) view.findViewById(R.id.boom);
        viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
            @Override
            public void onWindowFocusChanged(final boolean hasFocus) {
                if (init) return;
                init = true;
                initBoom();
            }
        });
        initBoom();

        return view;
    }

    //툴바검색
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        //서치바로 검색해서 아이템 찾기
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed 뒤로가기버튼
                        if (CategorySacn) {
                            showCardViewList(categoryFilteredModelList);
                        } else {
                            showCardViewList(listItems);
                        }
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded 서치버튼
                        if (CategorySacn) {
                            showCardViewList(categoryFilteredModelList);
                        } else {
                            showCardViewList(listItems);
                        }
                        return true; // Return true to expand action view
                    }
                }
        );
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<FoodTruckModel> filteredModelList = new ArrayList<>();
        if (CategorySacn) {
            filteredModelList = filter(categoryFilteredModelList, newText);
            showCardViewList(filteredModelList);
        } else {
            filteredModelList = filter(listItems, newText);
            showCardViewList(filteredModelList);
        }
        return true;
    }

    public void requestFoodtruckList(int num) {
        listItems.clear();

        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<ArrayList<FoodTruckModel>> convertedContent = service.foodtruck_list(num);
        convertedContent.enqueue(new Callback<ArrayList<FoodTruckModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FoodTruckModel>> call, Response<ArrayList<FoodTruckModel>> response) {
                ArrayList<FoodTruckModel> foodTruckList = response.body();

                Log.d("TAG", "바디: " + response.body().toString());

                for (FoodTruckModel foodTruck : foodTruckList
                        ) {
                    //foodTruck.setFtImage(FT_IMAGES[0]);
                    listItems.add(foodTruck);

                    Log.d("TAG", "트럭이름" + foodTruck.getFtName());
                    Log.d("TAG", "트럭이미지" + foodTruck.getFT_IMAGE_URL().getUrl());
                }
                showCardViewList(listItems); //서버에서 받아오면 카드뷰 그려주게하기
            }

            @Override
            public void onFailure(Call<ArrayList<FoodTruckModel>> call, Throwable t) {
                Log.d("실패", "onFailure: ");
                Log.d("TAG", t.getMessage());
            }
        });
    }

    // 리사이클뷰 아이템에 들어갈 목록 초기화 부분
    public void initFT() {
        listItems.clear();

        requestFoodtruckList(0); //0이 all category를 의미한다.


//      for(int i =0;i<5;i++){
//           FoodTruckModel item = new FoodTruckModel();
//           item.setFtName(FT_NAME[i]);
//           item.setFtImage(FT_IMAGES[i]);
//           item.setFtCategory(FT_CATEGORY[i]);
//           item.setFtPayment(FT_PAYMENT[i]);
//            listItems.add(item);
//        }
    }

    //플로팅 아이콘 처리 함수들
    private void initBoom() {
        int number = 8;
        ///int number = buttonNumberSeek.getProgress() + 3;

        Drawable[] drawables = new Drawable[number];
        int[] drawablesResource = new int[]{
                R.drawable.mark, R.drawable.refresh, R.drawable.copy, R.drawable.heart,
                R.drawable.info, R.drawable.like, R.drawable.record, R.drawable.search, R.drawable.settings
        };

        String[] STRINGS = new String[]{
                "전체", "한식", "일식", "양식", "중식", "분식", "디저트", "음료", "셋팅"
        };
        String[] strings = new String[number];
        for (int i = 0; i < number; i++)
            strings[i] = STRINGS[i];

        int[][] colors = new int[number][2];
        for (int i = 0; i < number; i++) {
            colors[i][1] = Utill.getInstance().getRandomColor();
            colors[i][0] = Util.getInstance().getPressedColor(colors[i][1]);
        }
        for (int i = 0; i < number; i++)
            drawables[i] = ContextCompat.getDrawable(getContext(), drawablesResource[i]);
        // Now with Builder, you can init BMB more convenient
        new BoomMenuButton.Builder()
                .subButtons(drawables, colors, strings)
                .button(ButtonType.CIRCLE)
                .boom(BoomType.PARABOLA)
                .place(PlaceType.CIRCLE_8_3)
                .boomButtonShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                .subButtonsShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                .shareStyle(3f, Utill.getInstance().getRandomColor(), Utill.getInstance().getRandomColor())
                .showOrder(OrderType.RANDOM)
                .hideOrder(OrderType.RANDOM)
                .duration(800)
                .showMoveEase(EaseType.EaseOutBack)
                .hideMoveEase(EaseType.EaseInOutCubic)
                .frames(80)
                .init(boomMenuButton);
        boomMenuButton.setOnSubButtonClickListener(new BoomMenuButton.OnSubButtonClickListener() {
            @Override
            public void onClick(int buttonIndex) {
                //categoryFilteredModelList = filter(listItems, buttonIndex);
                requestFoodtruckList(buttonIndex); //버튼 인덱스를 받아서 그 카테고리를 서버에요청, listItem에 저장 & 갱신

                //onResponse에 카드뷰 그려주는 부분 넣어서 해결
//                new Handler().postDelayed(new Runnable() {// 1 초 후에 실행
//                    @Override
//                    public void run() {
//                        showCardViewList(listItems); //갱신된 listItem 다시 보여줌
//                    }
//                }, 1000);
                //showCardViewList(categoryFilteredModelList);
            }
        });
    }

    //리스트 변경될때마다 재출력 모듈화
    private void showCardViewList(ArrayList<FoodTruckModel> filteredModelList) {
        // TODO: 2016-11-05  카드뷰 애니메이션 부분 나중에 수정11.05 https://github.com/wasabeef/recyclerview-animators
        truckAdapter = new TruckAdapter(getActivity(), filteredModelList, "FragmentHome");
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(truckAdapter);
        alphaAdapter.setFirstOnly(true);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        alphaAdapter.setDuration(1000);
        if (filteredModelList.size() >= 0 & myRecyclerView != null) {
            myRecyclerView.setAdapter(alphaAdapter);
        }
    }
    //TODO : 2016.11.06 카테고리 먼저 하고 툴바 가능 반대로는 미구현

    //툴바검색 알고리즘
    private ArrayList<FoodTruckModel> filter(ArrayList<FoodTruckModel> models, String query) {

        query = query.toLowerCase();
        ArrayList<FoodTruckModel> filteredModelList = new ArrayList<>();
        for (FoodTruckModel model : models) {
            final String text = model.getFtName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

//    //카테고리검색 알고리즘
//    private ArrayList<FoodTruckModel> filter(ArrayList<FoodTruckModel> models, int buttonIndex) {
//        CategorySacn = true;
//        categoryFilteredModelList.clear();
//        for (FoodTruckModel model : models) {
//            if (buttonIndex == 0) {
//                categoryFilteredModelList.addAll(models);
//                return categoryFilteredModelList;
//            } else if (model.getFtCategory() == buttonIndex) {
//                categoryFilteredModelList.add(model);
//            }
//        }
//        return categoryFilteredModelList;
//    }

}
