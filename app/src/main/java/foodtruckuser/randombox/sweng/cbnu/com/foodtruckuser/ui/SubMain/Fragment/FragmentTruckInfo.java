package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.RecyclerItemClickListener;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.MenuAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.GpsService;

/**
 * Created by Ratan on 7/29/2015.
 */
public class FragmentTruckInfo extends Fragment implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks,
        OnMapReadyCallback {

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
    FragmentManager mFragmentManager;

    //String and Integer array for Recycler View Items
    public static final String[] TITLES= {"디저트 5000원","피자 3000원","박도현 0원","1000원"
            ,"2000원","6000원","디저트 5000원","피자 3000원","박도현 0원","1000원"
            ,"2000원","6000원","디저트 5000원","피자 3000원","박도현 0원","박도현 0원","박도현 0원","박도현 0원"};
    public static final Integer[] IMAGES= {R.drawable.menuitem,R.drawable.menuitem2,R.drawable.menuitem3,R.drawable.menuitem4,R.drawable.menuitem5,
            R.drawable.ic_6,R.drawable.ic_7,R.drawable.ic_8,R.drawable.ic_9,R.drawable.ic_10,R.drawable.menuitem,
            R.drawable.menuitem2,R.drawable.menuitem3,R.drawable.menuitem4,R.drawable.menuitem5,0,0,0};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFT();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_truckinfo, container, false);

        mapview=(MapView)view.findViewById(R.id.map);

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mapview.onCreate(savedInstanceState);
        mapview.onResume();
        mapview.setClickable(false);

        mapview.getMapAsync(this);

        gpsService = new GpsService(getActivity());
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
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

        RatingBar mRatingBar = (RatingBar)view.findViewById(R.id.Ratingbar);
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
        myRecyclerView = (RecyclerView)view.findViewById(R.id.menu_view);
        // Here 2 is no. of columns to be displayed
        //StaggeredGridLayoutManager MyLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //MyLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(MyLayoutManager);
        //showViewList();
        myRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), myRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.subcontainerView, new FragmentMenu()).commit();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );
        return view;
    }
    public void initFT() {
        listitems.clear();
        for (int i = 0; i < 5; i++) {
            FoodTruckModel item = new FoodTruckModel();
            item.setFtName(FT_NAME[i]);
            item.setFtImage(FT_IMAGES[i]);
            item.setFtCategory(FT_CATEGORY[i]);
            item.setFtPayment(FT_PAYMENT[i]);
            item.setFT_LNG(FT_X[i]);
            item.setFT_LAT(FT_Y[i]);
            listitems.add(item);
        }

    }
    /*
    private void showViewList() {
        ArrayList<MenuModel> listitems = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            listitems.add(new MenuModel(TITLES[i],IMAGES[i]));
        }
        menuAdapter = new MenuAdapter(getContext(), listitems, "");
        myRecyclerView.setAdapter(menuAdapter);// set adapter on recyclerview
        menuAdapter.notifyDataSetChanged();// Notify the adapter

    }
    */
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
}
