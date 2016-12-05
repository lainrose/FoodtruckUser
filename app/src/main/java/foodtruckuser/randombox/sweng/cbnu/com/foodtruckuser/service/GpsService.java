package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GpsService extends Service implements LocationListener {
    private final Context mContext;

    // 현재 GPS 사용유무
    boolean isGPSEnabled = false;

    // 네트워크 사용유무
    boolean isNetworkEnabled = false;

    // GPS 상태값
    boolean isGetLocation = false;

    Location location;
    double lat; // 위도
    double lon; // 경도
    // 최소 GPS 정보 업데이트 거리 10미터
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    // 최소 GPS 정보 업데이트 시간 밀리세컨이므로 1분
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    protected LocationManager locationManager;

    public GpsService(Context context) {
        this.mContext = context;
        getLocation();
    }
  
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);
  
            // GPS 정보 가져오기 
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
  
            // 현재 네트워크 상태 값 알아오기 
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
  
            if (!isGPSEnabled && !isNetworkEnabled) {
                // GPS 와 네트워크사용이 가능하지 않을때 소스 구현
                Log.d("구글맵", "GPS사용불가" + this.isGetLocation);
            } else {
                this.isGetLocation = true;
                Log.d("구글맵", "GPS사용가능" + this.isGetLocation);
                // 네트워크 정보로 부터 위치값 가져오기 
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            // 위도 경도 저장 
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }
                 
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }
            }
  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }
      
    /**
     * GPS 종료 
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GpsService.this);
        }       
    }
      
    /**
     * 위도값을 가져옵니다. 
     * */
    public double getLatitude(){
        if(location != null){
            lat = location.getLatitude();
        }
        return lat;
    }
      
    /**
     * 경도값을 가져옵니다. 
     * */
    public double getLongitude(){
        if(location != null){
            lon = location.getLongitude();
        }
        return lon;
    }
      
    /**
     * GPS 나 wife 정보가 켜져있는지 확인합니다. 
     * */
    public boolean isGetLocation() {
        return this.isGetLocation;
    }
      
    /**
     * GPS 정보를 가져오지 못했을때 
     * 설정값으로 갈지 물어보는 alert 창
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
 
        alertDialog.setTitle("위치 서비스 설정");
        alertDialog.setMessage("무선 네트워크 사용, gps 위성 사용을 모두 체크하셔야 정확한 위치서비스가 가능합니다.\n" +
                "위치서비스 기능을 설정하시겠습니까?");
   
        // OK 를 누르게 되면 설정창으로 이동합니다. 
        alertDialog.setPositiveButton("Settings", 
                                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        // Cancle 하면 종료 합니다. 
        alertDialog.setNegativeButton("Cancel", 
                              new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        alertDialog.show();
    }
    public String findAddress(double lat, double lng) {
        StringBuffer bf = new StringBuffer();
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        String currentLocationAddress = new String();
        List<Address> address;
        try {
            if (geocoder != null) {
                // 세번째 인수는 최대결과값인데 하나만 리턴받도록 설정했다
                address = geocoder.getFromLocation(lat, lng, 1);
                // 설정한 데이터로 주소가 리턴된 데이터가 있으면
                if (address != null && address.size() > 0) {
                    // 주소
                    Address addr = address.get(0);
                    currentLocationAddress = addr.getAdminArea() + " "  // 시
                            + addr.getLocality() + " "                  // 구
                            +addr.getThoroughfare( ) + " "              // 동
                            + addr.getFeatureName();                    // 번지
                    /*
                    for (int i = 0; i <= addr.getMaxAddressLineIndex(); i++) {
                        String addLine = addr.getAddressLine(i);
                        currentLocationAddress += String.format("%s", addLine);
                    }
                    */
                }
            }

        } catch (IOException e) {
            currentLocationAddress = "해당 트럭의 위치를 찾을 수 없습니다.";
            e.printStackTrace();
        }
        //대한민국 없애주기
        return currentLocationAddress;
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
 
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
         
    }
 
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
         
    }
 
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
         
    }
 
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
         
    }
}