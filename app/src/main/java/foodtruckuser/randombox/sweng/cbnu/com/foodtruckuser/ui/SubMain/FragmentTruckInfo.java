package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain;

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
import android.view.MotionEvent;
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
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.MapItemAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.TruckAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.GpsService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main.FragmentMap;

/**
 * Created by Ratan on 7/29/2015.
 */
public class FragmentTruckInfo extends Fragment implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks,
        OnMapReadyCallback {

    private LatLng CuttrntLocation;
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
    private double USER_X;
    private double USER_Y;
    private GoogleApiClient mGoogleApiClient;
    private static final String STATE_RESOLVING_ERROR = "resolving_error";
    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;


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
        TruckLatLng = new LatLng(listitems.get(0).getFtX(), listitems.get(0).getFtY());
        optFirst = new MarkerOptions();
        optFirst.position(TruckLatLng);// 위도 • 경
        optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select));
        map.addMarker(optFirst).showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLng(TruckLatLng));
        map.getUiSettings().setAllGesturesEnabled(false);
        // Map 을 zoom 합니다.
        map.animateCamera(CameraUpdateFactory.zoomTo(14));

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
            item.setFtX(FT_X[i]);
            item.setFtY(FT_Y[i]);
            listitems.add(item);
        }

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
}
