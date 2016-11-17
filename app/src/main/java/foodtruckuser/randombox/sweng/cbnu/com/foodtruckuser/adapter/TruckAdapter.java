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
import com.sackcentury.shinebuttonlib.ShineButton;
import java.util.ArrayList;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.FragmentSubMain;

public class TruckAdapter extends RecyclerView.Adapter<TruckAdapter.TruckViewHolder>  {

        private ArrayList<FoodTruckModel> homeList;
        private static ArrayList<FoodTruckModel> myTruckList;
        private Context mContext = null;
        public static String TruckName;

        public TruckAdapter(Context c, ArrayList<FoodTruckModel> listitems) {
            this.mContext = c;
            this.homeList = listitems;
            myTruckList = new ArrayList<>();
        }

        @Override
            public TruckViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // create a new view
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.truck_item, parent, false);
                return new TruckViewHolder(v);
            }

        @Override
        public void onBindViewHolder(final TruckViewHolder holder, final int position) {
            holder.titleTextView.setText(homeList.get(position).getFtName());
            holder.coverImageView.setImageResource(homeList.get(position).getFtImage());
            holder.coverImageView.setTag(homeList.get(position).getFtImage());
            if(homeList.get(position).getFtPayment() == "false") {
                holder.payTextView.setText("카드불가");
            } else {
                holder.payTextView.setText("카드가능");
            }
            //holder.payTextView.setText(homeList.get(position).getFtPayment());
            /*
            if(homeList != null){
                if(!homeList.get(position).getFtLike()){
                    holder.shineButton.setChecked(false);
                }
                else{
                    holder.shineButton.setChecked(true);
                }
            }
            if(myTruckList != null){
                if(!myTruckList.get(position).getFtLike()){
                    holder.shineButton.setChecked(false);
                }
                else{
                    holder.shineButton.setChecked(true);
                }
            }
            */
            // TODO: 2016-11-17 다른화면 갔다가 오면 좋아요 풀려있으니깐 그거 확인

            holder.coverImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TAG", "해당 아이템 번호 = "+position);
                    TruckName = homeList.get(position).getFtName();
                    Intent submain = new Intent(mContext, FragmentSubMain.class);
                    mContext.startActivity(submain);
                }
            });
            holder.shineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!homeList.get(position).getFtLike()){
                        Toast.makeText(mContext, holder.titleTextView.getText()+"을"+ " 좋아요!", Toast.LENGTH_SHORT).show();
                        homeList.get(position).setFtLike(true);
                        myTruckList.add(homeList.get(position));
                    }
                    else{
                        Toast.makeText(mContext, holder.titleTextView.getText()+"을"+ " 싫어요!", Toast.LENGTH_SHORT).show();
                        homeList.get(position).setFtLike(false);
                        myTruckList.remove(homeList.get(position));
                    }

                }
            });
        }
        public static ArrayList<FoodTruckModel> getMyTruckList(){
            return myTruckList;
        }

        @Override
        public int getItemCount() {
            return homeList.size();
        }


        public static class TruckViewHolder extends RecyclerView.ViewHolder {

            public TextView titleTextView;
            public ImageView coverImageView;
            public ImageView shareImageView;
            public ShineButton shineButton;
            public TextView payTextView;

            public TruckViewHolder(View v) {
                super(v);
                titleTextView = (TextView) v.findViewById(R.id.titleTextView);
                coverImageView = (ImageView) v.findViewById(R.id.coverImageView);
                shareImageView = (ImageView) v.findViewById(R.id.shareImageView);
                shineButton = (ShineButton) v.findViewById(R.id.po_image);
                payTextView = (TextView) v.findViewById(R.id.payTextView);
            }


        }
}