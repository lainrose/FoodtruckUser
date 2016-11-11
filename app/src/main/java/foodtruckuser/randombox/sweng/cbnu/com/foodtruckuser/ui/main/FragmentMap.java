package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.plus.People;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.GpsService;

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

    private MapView mapview;
    private LatLng CuttrntLocation;
    private double USER_X;
    private double USER_Y;
    private GoogleMap map;
    private GpsService gpsService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapview=(MapView)view.findViewById(R.id.map);
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
        return view;
    }
    private void init(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("GPS설정정보");
        alert.setMessage("현재위치를 사용하시려면 아니오를\n위치를 새로 설정하시려면 예를\n눌러주세요.");

        alert.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //사용자 위치 선택 받기
                if (gpsService.isGetLocation()) {
                    map.setOnMapClickListener(new com.google.android.gms.maps.GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng arg0) {
                            // TODO Auto-generated method stub
                            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(arg0.latitude, arg0.longitude));
                            Log.d("GPS수신......X : ", String.valueOf(arg0.latitude));
                            Log.d("GPS수신......Y : ", String.valueOf(arg0.longitude));
                            map.addMarker(markerOptions).showInfoWindow();
                            USER_X = arg0.latitude;
                            USER_Y = arg0.longitude;
                            UserModel.getInstance().setUserX(USER_X);
                            UserModel.getInstance().setUserX(USER_Y);
                            CuttrntLocation = new LatLng(USER_X, USER_Y);
                            // sharedPreference.put(sharedPreference.user_x, String.valueOf(arg0.latitude)); //서버에 넘겨줄 좌표값
                            //sharedPreference.put(sharedPreference.user_y, String.valueOf(arg0.longitude));
                        }
                    });
                    map.setOnMapLongClickListener(new com.google.android.gms.maps.GoogleMap.OnMapLongClickListener() {
                        @Override
                        public void onMapLongClick(LatLng latLng) {
                            map.clear();
                        }
                    });
                    map.moveCamera(CameraUpdateFactory.newLatLng(CuttrntLocation));
                    map.animateCamera(CameraUpdateFactory.zoomTo(17));
                }
            }
        });
        alert.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        alert.show();
    }
    private void setCuttrntLocation(){
        if (gpsService.isGetLocation()) {
            USER_X = gpsService.getLatitude();
            USER_Y = gpsService.getLongitude();
            UserModel.getInstance().setUserX(USER_X);
            UserModel.getInstance().setUserX(USER_Y);
            //sharedPreference.put(sharedPreference.user_x, String.valueOf(x)); //서버에 넘겨줄 좌표값
            //sharedPreference.put(sharedPreference.user_y, String.valueOf(y));
            // Creating a LatLng object for the current location
            CuttrntLocation = new LatLng(USER_X, USER_Y);
            Log.d("GPS수신......X : ", String.valueOf(USER_X));
            Log.d("GPS수신......Y : ", String.valueOf(USER_Y));
            map.moveCamera(CameraUpdateFactory.newLatLng(CuttrntLocation));

            map.animateCamera(CameraUpdateFactory.zoomTo(17));

            // 마커 설정.
            MarkerOptions optFirst = new MarkerOptions();
            optFirst.position(CuttrntLocation);// 위도 • 경도
            optFirst.title("회원님의 위치입니다.");// 제목 미리보기
            optFirst.snippet("요기!");
            optFirst.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
            map.addMarker(optFirst).showInfoWindow();
        } else {
            // GPS 를 사용할수 없으므로
            gpsService.showSettingsAlert();
        }
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
            Log.d("구글맵", "현재위치 저장했음" + mLastLocation.getLatitude() + "/" + mLastLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLng(CuttrntLocation));
            // Map 을 zoom 합니다.
            map.animateCamera(CameraUpdateFactory.zoomTo(13));
            //map.setMyLocationEnabled(true);
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


}
