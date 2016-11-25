package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hedgehog.ratingbar.RatingBar;

import java.util.ArrayList;

import butterknife.BindView;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.RecyclerItemClickListener;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.ReviewItemAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.MenuAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.ReviewItemAnimator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.MenuModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.ReviewModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.GpsService;

public class AcitivityTruckDetail extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks,
        OnMapReadyCallback, View.OnClickListener{

    private Toolbar toolbar;
    public GoogleMap map;
    private GpsService gpsService;
    private ArrayList<FoodTruckModel> listitems = new ArrayList<>();
    private String FT_NAME[] = {"도현트럭","의범트럭",
            "영빈트럭","현표트럭","현정트럭"};
    private int FT_CATEGORY[] = {1, 2, 3, 3, 5};
    private String FT_PAYMENT[] = {"카드", "현금", "카드/현금", "현금", "카드"};
    private int FT_IMAGES[] = {R.drawable.truck1,R.drawable.truck2,R.drawable.truck3,
            R.drawable.truck4,R.drawable.truck5};
    private Double FT_X[] = {35.841979,35.840776, 35.840550, 35.841672, 35.842655};
    private Double FT_Y[] = {127.133218,127.132788 , 127.133936, 127.135846, 127.133335};
    public static LatLng TruckLatLng;
    private MarkerOptions optFirst;
    private MapView mapview;
    private GoogleApiClient mGoogleApiClient;
    private static RecyclerView myRecyclerView;
    private MenuAdapter menuAdapter;
    private Context mContext;

    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    @BindView(R.id.btnCreate)
    FloatingActionButton fabCreate;
    RecyclerView review_view;

    private CoordinatorLayout clContent;



    //String and Integer array for Recycler View Items
    public static final String[] TITLES= {"디저트 5000원","피자 3000원","박도현 0원","1000원"
            ,"2000원","6000원","디저트 5000원","피자 3000원","박도현 0원","1000원"
            ,"2000원","6000원","디저트 5000원","피자 3000원","박도현 0원","박도현 0원","박도현 0원","박도현 0원"};
    public static final Integer[] IMAGES= {R.drawable.menuitem,R.drawable.menuitem2,R.drawable.menuitem3,R.drawable.menuitem4,R.drawable.menuitem5,
            R.drawable.ic_6,R.drawable.ic_7,R.drawable.ic_8,R.drawable.ic_9,R.drawable.ic_10,R.drawable.menuitem,
            R.drawable.menuitem2,R.drawable.menuitem3,R.drawable.menuitem4,R.drawable.menuitem5,0,0,0};

    private ArrayList<ReviewModel> reviewitems = new ArrayList<>();
    private static final Integer[] CenterIMAGES= {0, 0, 0, R.drawable.menuitem2,R.drawable.menuitem3};

    private static final Integer[] BottomIMAGES= {R.drawable.img_feed_bottom_1,R.drawable.img_feed_bottom_2,
            R.drawable.img_feed_bottom_1,R.drawable.img_feed_bottom_2,R.drawable.img_feed_bottom_1};

    private static final Integer[] UserIMAGES= {R.drawable.truck1,R.drawable.truck2,R.drawable.truck3,
            R.drawable.truck4,R.drawable.truck5};
    private static final Integer[] LikeConunts= {33,112,63,235,100533};
    private static final String[] UserNames= {"도혀니", "으버미", "현펴", "횬죵이", "영비니"};
    private static final String[] WriteText = {"가나다라마바사아자차카타파하", "안녕하세요! \n" +
            "지난 주말 좋은 체험했던 맛집 리뷰에 \n" +
            "악플로 괴롭힘을 받아서 맛집 리뷰는 \n" +
            "공감만 열어놓습니다.\n" +
            "제가 느낀점을 솔직하게 쓴 후기에 \n" +
            "악플로 상처받는 일은 \n" +
            "앞으로 없었으면 합니다. ", "늘 소통해주시고 자주 와주시는 \n" +
            "이웃분들께 양해부탁드립니다.", "공감은 열어두니 공감버튼 눌러주시면\n" +
            "댓글 주시는것과 같은걸로 생각할게요. \n" +
            "편안하게 글을 봐주셨으면해요. ", "아울러 악플러는 많은 시간을 들여 \n" +
            "맛집을 다녀오고\n" +
            "포스팅을 하고 블로그를 꾸며온 사람에게 \n" +
            "본인기분 상한 것을 \n" +
            "화풀이 하지 않았으면합니다.", "악플러는 본인글 읽어보고 망신인거 알고\n" +
            "반성하고 미안한 마음좀 갖길바랍니다."};

    private ReviewItemAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_detail);

        mContext = this;
        setupToolbar();
        setupCollapsingToolbar();
        initFT();
        clContent = (CoordinatorLayout)findViewById(R.id.content);
        mapview=(MapView)findViewById(R.id.map);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mapview.onCreate(savedInstanceState);
        mapview.onResume();
        mapview.setClickable(false);

        mapview.getMapAsync(this);

        gpsService = new GpsService(this);
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        map = mapview.getMap();
        TruckLatLng = new LatLng(listitems.get(0).getFT_LNG(), listitems.get(0).getFT_LAT());
        optFirst = new MarkerOptions();
        optFirst.position(TruckLatLng);// 위도 • 경
        optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select));
        map.addMarker(optFirst).showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLng(TruckLatLng));
        map.getUiSettings().setAllGesturesEnabled(false);
        // Map 을 zoom 합니다.
        map.animateCamera(CameraUpdateFactory.zoomTo(14));

        RatingBar mRatingBar = (RatingBar)findViewById(R.id.ratingbar);
        mRatingBar.setStarEmptyDrawable(getResources().getDrawable(R.drawable.ic_star_empty));
        mRatingBar.setStarFillDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
        mRatingBar.setStarCount(5);
        mRatingBar.setStar(5.0f);
        mRatingBar.halfStar(false);
        mRatingBar.setmClickable(false);
        mRatingBar.setStarImageWidth(120f);
        mRatingBar.setStarImageHeight(60f);
        mRatingBar.setImagePadding(35);
        /*
        mRatingBar.setOnRatingChangeListener(
                new RatingBar.OnRatingChangeListener() {
                    @Override
                    public void onRatingChange(float RatingCount) {
                        Toast.makeText(getActivity(), "the fill star is" + RatingCount, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        */
        //리사이클뷰(카드뷰)
        myRecyclerView = (RecyclerView)findViewById(R.id.menu_view);
        // Here 2 is no. of columns to be displayed
        //StaggeredGridLayoutManager MyLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //MyLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(MyLayoutManager);
        showViewList();
        myRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, myRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(position==6){
                            Intent intent = new Intent(mContext, AcitivityTruckMenu.class);
                            startActivity(intent);
                        }
                    }
                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );
        review_view = (RecyclerView)findViewById(R.id.review_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        review_view.setHasFixedSize(true);
        review_view.setLayoutManager(linearLayoutManager);
        reviewAdapter = new ReviewItemAdapter(this, reviewitems);
        review_view.setAdapter(reviewAdapter);
        review_view.setItemAnimator(new ReviewItemAnimator());

    }
    private void setupCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);

        collapsingToolbar.setTitleEnabled(false);
    }
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("스테이크 하우스");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void initFT() {
        listitems.clear();
        reviewitems.clear();
        for (int i = 0; i < 5; i++) {
            FoodTruckModel item = new FoodTruckModel();
            item.setFtName(FT_NAME[i]);
            item.setFtImage(FT_IMAGES[i]);
            item.setFtCategory(FT_CATEGORY[i]);
            item.setFtPayment(FT_PAYMENT[i]);
            item.setFT_LNG(FT_X[i]);
            item.setFT_LAT(FT_Y[i]);
            listitems.add(item);

            ReviewModel item1 = new ReviewModel();
            item1.setCenterimage(CenterIMAGES[i]);
            item1.setBottomimage(BottomIMAGES[i]);
            item1.setUserImage(UserIMAGES[i]);
            item1.setUserText(UserNames[i]);
            item1.setReviewText(WriteText[i]);
            item1.setLikesCount(LikeConunts[i]);
            reviewitems.add(item1);
        }
    }
    private void showViewList() {
        ArrayList<MenuModel> listitems = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            listitems.add(new MenuModel(TITLES[i],IMAGES[i]));
        }
        menuAdapter = new MenuAdapter(this, listitems, "AcitivityTruckDetail");
        myRecyclerView.setAdapter(menuAdapter); // set adapter on recyclerview
        menuAdapter.notifyDataSetChanged(); // Notify the adapter
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
    public void showLikedSnackbar() {
        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_review_more){
            Intent intent = new Intent(this, AcitivityTruckReview.class);
            this.startActivity(intent);
        }
    }
}
