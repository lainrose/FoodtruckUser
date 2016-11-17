package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.nightonke.boommenu.CircleButton;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.GpsService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.FragmentSubMain;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main.FragmentMap;

public class MapItemAdapter extends RecyclerView.Adapter<MapItemAdapter.TruckViewHolder>  {

        private ArrayList<FoodTruckModel> homeList;
        private Context mContext = null;
        private GpsService gpsService = new GpsService(mContext);
        public MapItemAdapter(Context c, ArrayList<FoodTruckModel> listitems) {
            this.mContext = c;
            this.homeList = listitems;
        }

        @Override
            public TruckViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // create a new view
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_item, parent, false);
                return new TruckViewHolder(v);
            }

        @Override
        public void onBindViewHolder(final TruckViewHolder holder, final int position) {

            holder.coverImageView.setImageResource(homeList.get(position).getFtImage());
            holder.coverImageView.setTag(homeList.get(position).getFtImage());
            holder.titleTextView.setText(homeList.get(position).getFtName());
            holder.payTextView.setText(homeList.get(position).getFtPayment());
            holder.categoryTextView.setText(homeList.get(position).getFT_LOCATIONNAME());
            System.out.println("주소 : " +homeList.get(position).getFT_LOCATIONNAME());

        }

        @Override
        public int getItemCount() {
            return homeList.size();
        }

        public static class TruckViewHolder extends RecyclerView.ViewHolder {

            public CircleImageView coverImageView;
            public TextView titleTextView;
            public TextView payTextView;
            public TextView categoryTextView;

            public TruckViewHolder(View v) {
                super(v);
                coverImageView = (CircleImageView)v.findViewById(R.id.coverImageView);
                titleTextView = (TextView)v.findViewById(R.id.titleTextView);
                payTextView = (TextView)v.findViewById(R.id.payTextView);
                categoryTextView = (TextView)v.findViewById(R.id.categoryTextView);

            }


        }
}