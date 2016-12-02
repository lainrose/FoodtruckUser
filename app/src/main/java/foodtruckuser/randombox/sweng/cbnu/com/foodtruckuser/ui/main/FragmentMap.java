package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
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

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.ServiceGenerator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.MapItemAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.ApiService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.GpsService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.Acitivity.AcitivityTruckDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// TODO: 2016-11-17 맵이랑 db연동
public class FragmentMap extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,
        OnMapReadyCallback, LocationListener {

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
    public GoogleMap map;
    private GpsService gpsService;
    private static FragmentMap fragmentMap;
    public static ViewPager viewPager;


    private LinearLayoutManager MyLayoutManager;
    private RecyclerViewPager mRecyclerView;
    private MapItemAdapter mapItemAdapter;
    private ArrayList<FoodTruckModel> listitems = new ArrayList<>();

//    private String FT_NAME[] = {"도현트럭", "의범트럭",
//            "영빈트럭", "현표트럭", "현정트럭"};
//    private int FT_CATEGORY[] = {1, 2, 3, 3, 5};
//    private String FT_PAYMENT[] = {"카드", "현금", "카드/현금", "현금", "카드"};
//    private int FT_IMAGES[] = {R.drawable.truck1, R.drawable.truck2, R.drawable.truck3,
//            R.drawable.truck4, R.drawable.truck5};
//    private Double FT_X[] = {35.841979, 35.840776, 35.840550, 35.841672, 35.842655}; //이게 Lat
//    private Double FT_Y[] = {127.133218, 127.132788, 127.133936, 127.135846, 127.133335}; //이게 Lng

    //public LatLng TruckLatLng[] = new LatLng[];
    public ArrayList<LatLng> TruckLatLng = new ArrayList<>();
    private MarkerOptions optFirst;

    public FragmentMap() {

    }

    public static synchronized FragmentMap getInstance() {
        if (fragmentMap == null) {
        }
        try {
            if (fragmentMap == null)
                fragmentMap = new FragmentMap();
            return fragmentMap;
        } finally {
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFT();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapview = (MapView) view.findViewById(R.id.map);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        MyLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);


        mRecyclerView = (RecyclerViewPager) view.findViewById(R.id.map_item_viewpager);
        mRecyclerView.setLayoutManager(MyLayoutManager);
//        mapItemAdapter = new MapItemAdapter(getActivity(), listitems);
//        mRecyclerView.setAdapter(mapItemAdapter);
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
                for (int i = 0; i < TruckLatLng.size(); i++) {
                    if (marker.getPosition() == TruckLatLng.get(i)) {
                        mRecyclerView.smoothScrollToPosition(i);
                    }
                }
                return false;
            }
        });
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

//        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                View child = rv.findChildViewUnder(e.getX(), e.getY());
//                if (child != null && gestureDetector.onTouchEvent(e)) {
//                    //트럭 세부정보로 가는데 지울예정
//                    //mRecyclerView.getChildPosition(child)
//                    Intent submain = new Intent(getContext(), AcitivityTruckDetail.class);
//                    getContext().startActivity(submain);
//                }
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//
//        });

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

        // TODO: 2016-11-25 6.0이상에서 최초로 앱 설치 후 실행 시 퍼미션 물어봄, 그러고 종료. 그 뒤론 잘 작동함 이거 해결해야함
            //안드 6.0 달라진 퍼미션
            int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }

            Log.d("구글맵", "온맵레디");
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
            } else {
                Log.d("안되나", "onMapReady: ");
            // Show rationale and request permission.
        }
    }

    public void setMarkerImage() {
        int i = 0;
        for (FoodTruckModel item : listitems) {
            Log.d("item", "i : " + String.valueOf(i));
            optFirst = new MarkerOptions();
            // TODO: 2016-11-24 Iterator패턴으로 바꿔보기. 근데 큰차이 없는듯
            // TODO: 2016-12-02 여기 코드 리팩토링좀...
            optFirst.position(TruckLatLng.get(i));
            optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select));
            map.addMarker(optFirst).showInfoWindow();
            i++;
            // TODO: 2016-11-24 이미지에 변수명 넣어서 호출하기, 가장 가까운 트럭부터 계산해서 1번으로 보여주기
//                if (i == 0)
//                    optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select));
//            else if (i == 1)
//                optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select_2));
//            else if (i == 2)
//            map.addMarker(optFirst).showInfoWindow();
        }
    }


    //최초 한번만 현위치 잡음
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("구글맵", "온커넥티드");
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            CuttrntLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            Log.d("구글맵", "현재위치 저장했음" + mLastLocation.getLatitude() + "/" + mLastLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLng(CuttrntLocation));
            // Map 을 zoom 합니다.
            map.animateCamera(CameraUpdateFactory.zoomTo(16));
            // 마커 설정.

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
    public void onResume() {
        super.onResume();
        Log.d("구글맵", "온리쥼");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("구글맵", "온푸스");
    }

    @Override
    public void onDestroy() {
        Log.d("구글맵", "온디스트로이");
        gpsService.stopUsingGPS();
        stopGps();
        super.onDestroy();

    }

    public void stopGps() {
        gpsService.stopUsingGPS();
        Log.d("구글맵", "스탑지피에스");
        if (this.mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(this.mGoogleApiClient, this);
        }
        this.mGoogleApiClient.disconnect();
    }

    public void showViewPagerList() {
        mapItemAdapter = new MapItemAdapter(getActivity(), listitems);
        mRecyclerView.setAdapter(mapItemAdapter);
    }

    public void requestFoodtruckList(int num) {
        listitems.clear();

        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<ArrayList<FoodTruckModel>> convertedContent = service.foodtruck_list(num);
        convertedContent.enqueue(new Callback<ArrayList<FoodTruckModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FoodTruckModel>> call, Response<ArrayList<FoodTruckModel>> response) {
                ArrayList<FoodTruckModel> foodTruckList = response.body();

                if(foodTruckList == null) {
                    return;
                }

                for (FoodTruckModel foodTruck : foodTruckList) {
                    if(foodTruck.getFT_LAT() == null || foodTruck.getFT_LNG() == null) {
                        break;
                    }
                    TruckLatLng.add(new LatLng(foodTruck.getFT_LAT(), foodTruck.getFT_LNG()));
                    foodTruck.setFT_LOCATIONNAME(gpsService.findAddress(foodTruck.getFT_LAT(), foodTruck.getFT_LNG()));
                    listitems.add(foodTruck);

                    Log.d("TAG", "맵트럭 : " + foodTruck.getFtName());
                }
                setMarkerImage();
                showViewPagerList();
            }

            @Override
            public void onFailure(Call<ArrayList<FoodTruckModel>> call, Throwable t) {
                Log.d("실패", "onFailure: ");
            }
        });
    }

    public void initFT() {
        requestFoodtruckList(0); //0은 전체 푸드트럭 리스트 받아오기

//        for(int i =0;i<5;i++){
//            FoodTruckModel item = new FoodTruckModel();
//            item.setFtName(FT_NAME[i]);
//            item.setFtImage(FT_IMAGES[i]);
//            item.setFtCategory(FT_CATEGORY[i]);
//            item.setFtPayment(FT_PAYMENT[i]);
//            item.setFtX(FT_X[i]);
//            item.setFtY(FT_Y[i]);
//            listitems.add(item);
//        }
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
                CameraUpdate location = CameraUpdateFactory.newLatLngZoom(TruckLatLng.get(newPosition), 16);
                Log.d("맵", "TruckLatLng " + TruckLatLng.get(newPosition));
                // Log.d("맵", "newPosition : " + String.valueOf(newPosition));
                map.animateCamera(location);
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
