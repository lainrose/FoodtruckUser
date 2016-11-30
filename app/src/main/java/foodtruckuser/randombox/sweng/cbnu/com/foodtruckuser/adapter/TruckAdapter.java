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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.ServiceGenerator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.ApiService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.Acitivity.AcitivityTruckDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TruckAdapter extends RecyclerView.Adapter<TruckAdapter.TruckViewHolder>  {

        private ArrayList<FoodTruckModel> homeList;
//        private static ArrayList<FoodTruckModel> myTruckList;
        private Context mContext = null;
        public static String TruckName;
        String Url= ServiceGenerator.API_BASE_URL;

        public TruckAdapter(Context c, ArrayList<FoodTruckModel> listitems) {
            this.mContext = c;
            this.homeList = listitems;
//            myTruckList = new ArrayList<>();
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
            //holder.coverImageView.setImageResource(homeList.get(position).getFtImage());
            //holder.coverImageView.setTag(homeList.get(position).getFtImage());

            Picasso.with(mContext).load(Url + homeList.get(position).getFT_IMAGE_URL().getUrl()).into(holder.coverImageView);
//            Log.d("트럭사진", homeList.get(position).getFT_IMAGE_URL().getUrl());

            if(homeList.get(position).getFtPayment() == "false") {
                holder.payTextView.setText("카드불가");
            } else {
                holder.payTextView.setText("카드가능");
            }

            /* 일단은 필요없음.
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
            //트럭 클릭하면 Activity Detail로 이동하는 온클릭 리스너.
            holder.coverImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TAG", "해당 아이템 번호 = "+position);
                    Intent submain = new Intent(mContext, AcitivityTruckDetail.class);
                    submain.putExtra("clickedFoodTruck", homeList.get(position));
                    mContext.startActivity(submain);
                    //overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                }
            });
            //좋아요
            holder.shineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!homeList.get(position).isFT_LIKE()){
                        ApiService service = ServiceGenerator.createService(ApiService.class);
                        Call<Boolean> convertedContent = service.add_like_truck(UserModel.USER_INFO.getUserId(), homeList.get(position).getFT_ID());
                        convertedContent.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                Log.d("TAG", "좋아요 푸드트럭" + response.body().toString());
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                            }
                        });
                        Toast.makeText(mContext, holder.titleTextView.getText()+"을"+ " 좋아요!", Toast.LENGTH_SHORT).show();
                        homeList.get(position).setFT_LIKE(true);
//                        myTruckList.add(homeList.get(position));
                    }
                    else{
                        ApiService service = ServiceGenerator.createService(ApiService.class);
                        Call<Boolean> convertedContent = service.delete_like_truck(UserModel.USER_INFO.getUserId(), homeList.get(position).getFT_ID());
                        convertedContent.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                Log.d("TAG", "좋아요 취소 푸드트럭" + response.body().toString());
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                            }
                        });
                        Toast.makeText(mContext, holder.titleTextView.getText()+"을"+ " 좋아요 취소!", Toast.LENGTH_SHORT).show();
                        homeList.get(position).setFT_LIKE(false);
//                        myTruckList.remove(homeList.get(position));
                    }
                }
            });
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