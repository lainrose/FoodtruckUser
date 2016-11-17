package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.MapItemAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.TruckAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.GpsService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.FragmentSubMain;

public class FragmentMap extends Fragment implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks,
        OnMapReadyCallback, LocationListener{

    private GoogleApiClient mGoogleApiClient;
    private static final String STATE_RESOLVING_ERROR = "resolving_error";
    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;
    private ViewPager pager;

    private MapView mapview;
    private LatLng CuttrntLocation;
    private double USER_X;
    private double USER_Y;
    public GoogleMap map;
    private GpsService gpsService;
    private static FragmentMap fragmentMap;
    public static ViewPager viewPager;


    private LinearLayoutManager MyLayoutManager;
    private RecyclerViewPager mRecyclerView;
    private MapItemAdapter mapItemAdapter;
    private ArrayList<FoodTruckModel> listitems = new ArrayList<>();
    private String FT_NAME[] = {"도현트럭","의범트럭",
            "영빈트럭","현표트럭","현정트럭"};
    private int FT_CATEGORY[] = {1, 2, 3, 3, 5};
    private String FT_PAYMENT[] = {"카드", "현금", "카드/현금", "현금", "카드"};
    private int FT_IMAGES[] = {R.drawable.truck1,R.drawable.truck2,R.drawable.truck3,
            R.drawable.truck4,R.drawable.truck5};
    private Double FT_X[] = {35.841979,35.840776, 35.840550, 35.841672, 35.842655};
    private Double FT_Y[] = {127.133218,127.132788 , 127.133936, 127.135846, 127.133335};
    public static LatLng TruckLatLng[] = new LatLng[5];
    private MarkerOptions optFirst;
    public FragmentMap() {

    }
    public static synchronized FragmentMap getInstance() {
        if(fragmentMap == null){}
        try{
            if(fragmentMap ==null)
                fragmentMap = new FragmentMap();
            return fragmentMap;
        }
        finally {}
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFT();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapview=(MapView)view.findViewById(R.id.map);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        mRecyclerView = (RecyclerViewPager)view.findViewById(R.id.map_item_viewpager);

        MyLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mapItemAdapter = new MapItemAdapter(getActivity(), listitems);
        mRecyclerView.setLayoutManager(MyLayoutManager);
        mRecyclerView.setAdapter(mapItemAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.setSinglePageFling(true);
        initViewPager();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mapview.onCreate(savedInstanceState);
        mapview.onResume();
        mapview.getMapAsync(this);

        gpsService = new GpsService(getActivity());
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        map = mapview.getMap();
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.getId().equalsIgnoreCase("m0"))
                    mRecyclerView.smoothScrollToPosition(0);
                else if(marker.getId().equalsIgnoreCase("m1"))
                    mRecyclerView.smoothScrollToPosition(1);
                else if(marker.getId().equalsIgnoreCase("m2"))
                    mRecyclerView.smoothScrollToPosition(2);
                else if(marker.getId().equalsIgnoreCase("m3"))
                    mRecyclerView.smoothScrollToPosition(3);
                else if(marker.getId().equalsIgnoreCase("m4"))
                    mRecyclerView.smoothScrollToPosition(4);

                return false;
            }
        });
        final GestureDetector gestureDetector = new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener()
        {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e)
            {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child!=null&&gestureDetector.onTouchEvent(e)) {
                    Intent submain = new Intent(getContext(), FragmentSubMain.class);
                    getContext().startActivity(submain);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mResolvingError) { // more about this later
            Log.d("구글맵", "onStart connect");
            mGoogleApiClient.connect();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_RESOLVING_ERROR, mResolvingError);
    }
    @Override
    public void onStop() {
        Log.d("구글맵", "온스탑 ");
        mGoogleApiClient.disconnect();
        stopGps();
        super.onStop();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("구글맵", "온맵레디");
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
    }
    //최초 한번만 현위치 잡음
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            Log.d("구글맵", "온커넥티드");
                    Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (mLastLocation != null) {
                        CuttrntLocation = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
                        USER_X = mLastLocation.getLatitude();
                        USER_Y = mLastLocation.getLongitude();
                        Log.d("구글맵", "현재위치 저장했음" + mLastLocation.getLatitude() + "/" + mLastLocation.getLongitude());
                        map.moveCamera(CameraUpdateFactory.newLatLng(CuttrntLocation));
                        // Map 을 zoom 합니다.
                        map.animateCamera(CameraUpdateFactory.zoomTo(16));
                        // 마커 설정.
                        for(int i=0; i<5; i++){
                            optFirst = new MarkerOptions();
                            TruckLatLng[i] = new LatLng(listitems.get(i).getFtX(), listitems.get(i).getFtY());
                            listitems.get(i).setFT_LOCATIONNAME(gpsService.findAddress(listitems.get(i).getFtX(), listitems.get(i).getFtY()));
                            optFirst.position(TruckLatLng[i]);// 위도 • 경
                            if(i==0)
                                optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select_1));
                            else if (i ==1)
                                optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select_2));
                            else if (i ==2)
                                optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select_3));
                            else if (i ==3)
                                optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select_4));
                            else if (i ==4)
                                optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select_5));
                            map.addMarker(optFirst).showInfoWindow();
                }

            }
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
            } else {
                // Show rationale and request permission.
            }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("구글맵", "온커넥션서스펜드");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("구글맵", "온커넥션페일드");
    }
    //위치정보 바뀔때마다 위치 갱신함
    @Override
    public void onLocationChanged(Location location) {
        Log.d("구글맵", "온로케이션체인지드");
        gpsService.stopUsingGPS();
        stopGps();
    }
    @Override
    public void onResume()
    {
        super.onResume();
        Log.d("구글맵", "온리쥼");
    }
    @Override
    public void onPause()
    {

        super.onPause();
        Log.d("구글맵", "온푸스");
    }
    @Override
    public void onDestroy()
    {
        Log.d("구글맵", "온디스트로이");
        gpsService.stopUsingGPS();
        stopGps();
        super.onDestroy();

    }
    public void stopGps()
    {
        gpsService.stopUsingGPS();
        Log.d("구글맵", "스탑지피에스");
        if (this.mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(this.mGoogleApiClient, this);
        }
        this.mGoogleApiClient.disconnect();
    }
    public void initFT() {
        listitems.clear();
        for(int i =0;i<5;i++){
            FoodTruckModel item = new FoodTruckModel();
            item.setFtName(FT_NAME[i]);
            item.setFtImage(FT_IMAGES[i]);
            item.setFtCategory(FT_CATEGORY[i]);
            item.setFtPayment(FT_PAYMENT[i]);
            item.setFtX(FT_X[i]);
            item.setFtY(FT_Y[i]);
            listitems.add(item);
        }
    }
    private void initViewPager() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

//                updateState(scrollState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
//                mPositionText.setText("First: " + mRecyclerViewPager.getFirstVisiblePosition());
                int childCount = mRecyclerView.getChildCount();
                int width = mRecyclerView.getChildAt(0).getWidth();
                int padding = (mRecyclerView.getWidth() - width) / 2;
//                mCountText.setText("Count: " + childCount);

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                    float rate = 0;
                    ;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                        v.setScaleX(1 - rate * 0.1f);

                    } else {
                        //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                        v.setScaleX(0.9f + rate * 0.1f);
                    }
                }
            }
        });
        mRecyclerView.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int oldPosition, int newPosition) {
                Log.d("test", "oldPosition:" + oldPosition + " newPosition:" + newPosition);
                map.moveCamera(CameraUpdateFactory.newLatLng(FragmentMap.getInstance().TruckLatLng[newPosition]));
            }
        });

        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mRecyclerView.getChildCount() < 3) {
                    if (mRecyclerView.getChildAt(1) != null) {
                        if (mRecyclerView.getCurrentPosition() == 0) {
                            View v1 = mRecyclerView.getChildAt(1);
                            v1.setScaleY(0.9f);
                            v1.setScaleX(0.9f);
                        } else {
                            View v1 = mRecyclerView.getChildAt(0);
                            v1.setScaleY(0.9f);
                            v1.setScaleX(0.9f);
                        }
                    }
                } else {
                    if (mRecyclerView.getChildAt(0) != null) {
                        View v0 = mRecyclerView.getChildAt(0);
                        v0.setScaleY(0.9f);
                        v0.setScaleX(0.9f);
                    }
                    if (mRecyclerView.getChildAt(2) != null) {
                        View v2 = mRecyclerView.getChildAt(2);
                        v2.setScaleY(0.9f);
                        v2.setScaleX(0.9f);
                    }
                }

            }
        });
    }
}
