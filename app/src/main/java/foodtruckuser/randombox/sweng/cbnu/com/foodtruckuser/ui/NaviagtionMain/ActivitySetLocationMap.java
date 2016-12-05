package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.NaviagtionMain;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import cn.pedant.SweetAlert.SweetAlertDialog;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.ServiceGenerator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.Utill;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.preference.PrefHelper;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.ApiService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.GpsService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.JoinMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySetLocationMap extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,
        OnMapReadyCallback, LocationListener {

    private GoogleMap googleMap;
    private LatLng selectLatLng;
    private Toolbar toolbar;
    private GoogleApiClient mGoogleApiClient;
    private MapFragment mapFragment;
    private Button SetLocationBtn;
    private double CuttrntX;
    private double CuttrntY;
    private Context mContext = this;
    private boolean mResolvingError = false;
    private static final String STATE_RESOLVING_ERROR = "resolving_error";
    private GpsService gpsService;
    private Boolean MarkerFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivitiy_setlocation_map);
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.acitivitymap);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        gpsService = new GpsService(this);
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        googleMap = ( (MapFragment) getFragmentManager().findFragmentById(R.id.acitivitymap) ).getMap();
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select));
                markerOptions.position(latLng);
                CuttrntX = latLng.latitude;
                CuttrntY = latLng.longitude;
                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.addMarker(markerOptions);
                MarkerFlag = true;
            }
        });
        SetLocationBtn = (Button) findViewById (R.id.SetLocationBtn);
        SetLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MarkerFlag){
                    new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("선택한 위치를 사용하시겠습니까?")
                            //.setContentText("확인 버튼을 누르면 취소할 수 없습니다.")
                            .setCancelText("취소")
                            .setConfirmText("확인")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                    Log.d("선택한 위치", CuttrntX +"//" + CuttrntY);
                                    //// TODO: 2016-12-01 이거 내위치 보낼꺼니까 CurrentX,Y 보내면 됨
                                    ApiService service = ServiceGenerator.createService(ApiService.class);
                                    Call<Boolean> call = service.set_location(UserModel.getInstance().getUserId(), (float)CuttrntX, (float)CuttrntY);
                                    call.enqueue(new Callback<Boolean>() {
                                        @Override
                                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                            Boolean actionCheck = response.body();
                                            if(actionCheck) {
                                                Toast.makeText(getApplicationContext(), "위치 저장 성공", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Boolean> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            }).show();
                }
                else{
                    new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("알림받을 위치를 선택해주시기 바랍니다.?")
                            .setConfirmText("확인")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            }).show();
                }

            }
        });
        mapFragment.getMapAsync(this);

        setupToolbar();

    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("알림 위치 설정");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public void onMapReady(final GoogleMap map) {
        // TODO: 2016-11-25 6.0이상에서 최초로 앱 설치 후 실행 시 퍼미션 물어봄, 그러고 종료. 그 뒤론 잘 작동함 이거 해결해야함
        //안드 6.0 달라진 퍼미션
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        Log.d("구글맵", "온맵레디");
        if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            Log.d("안되나", "onMapReady: ");
            // Show rationale and request permission.
        }
        //Marker marker = googleMap.addMarker(new MarkerOptions().position(newLatLng).
        //icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select)));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                selectLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(selectLatLng));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
                Log.d("구글맵", "알림위치설정 위치 잡음" + mLastLocation.getLatitude() + "/" + mLastLocation.getLongitude());
                return;
            }
            else
                Log.d("구글맵", "알림위치설정 위치 못 잡음");

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
}
 