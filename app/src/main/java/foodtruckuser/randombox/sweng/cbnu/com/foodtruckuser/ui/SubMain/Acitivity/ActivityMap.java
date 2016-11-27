package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.Acitivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;

public class ActivityMap extends AppCompatActivity implements OnMapReadyCallback {

    private Double x;
    private Double y;
    private GoogleMap googleMap;
    private LatLng newLatLng;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivitiy_map);

        Intent intent = getIntent();
        x = intent.getExtras().getDouble("Key");
        y = intent.getExtras().getDouble("Key1");
        Log.d("tag", x + "   " + y);
        newLatLng = new LatLng(x,y);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.acitivitymap);
        mapFragment.getMapAsync(this);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("스테이크 하우스");
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
        googleMap = map;

        Marker marker = googleMap.addMarker(new MarkerOptions().position(newLatLng).
                icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }

}
 