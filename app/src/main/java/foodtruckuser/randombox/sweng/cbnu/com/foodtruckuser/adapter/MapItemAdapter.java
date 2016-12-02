package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.ServiceGenerator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.GpsService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.Acitivity.AcitivityTruckDetail;

public class MapItemAdapter extends RecyclerView.Adapter<MapItemAdapter.TruckViewHolder> {

    private ArrayList<FoodTruckModel> homeList;
    private Context mContext = null;
    private GpsService gpsService = new GpsService(mContext);
    String Url = ServiceGenerator.API_BASE_URL;


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

        //이름
        holder.titleTextView.setText(homeList.get(position).getFtName());

        //사진
        Picasso.with(mContext).load(Url + homeList.get(position).getFT_IMAGE_URL().getUrl()).into(holder.coverImageView);

        //결제방법
        if (homeList.get(position).getFtPayment() == "false") {
            holder.payTextView.setText("카드불가");
        } else {
            holder.payTextView.setText("카드가능");
        }

        //주소
        holder.locationTextView.setText(homeList.get(position).getFT_LOCATIONNAME());

        //카테고리
        String category;
        switch (homeList.get(position).getFtCategory()) {
            case 1 : category = "한식"; break;
            case 2 : category = "일식"; break;
            case 3 : category = "양식"; break;
            case 4 : category = "중식"; break;
            case 5 : category = "분식"; break;
            case 6 : category = "디저트"; break;
            case 7 : category = "음료"; break;
            default: category = ""; break;
        }
        holder.categoryTextView.setText(category);

        //리뷰 수
        holder.reviewCountTextView.setText("" + homeList.get(position).getFT_REVIEW_NUM());

        //좋아요 수
        holder.likeTextView.setText("" + homeList.get(position).getFT_LIKE_NUM());

        //평점
        holder.ratingTextView.setText("" + homeList.get(position).getFtRating());
//
//        //영업여부
//        if(homeList.get(position).isFT_isOPEN() == true) {
//            holder.isOpenTextView.setText("영업중");
//            String strColor = "#28c908";
//            holder.isOpenTextView.setTextColor(Color.parseColor(strColor));
//        } else {
//            holder.isOpenTextView.setText("영업종료");
//            String strColor = "#dd0606";
//            holder.isOpenTextView.setTextColor(Color.parseColor(strColor));
//        }

        //영업여부
        if (homeList.get(position).isFT_isOPEN() == true) {
            holder.isOpenTextButton.setText("영업중");
            holder.isOpenTextButton.setBackgroundResource(R.drawable.button_shape_green);
        } else {
            holder.isOpenTextButton.setText("영업종료");
            holder.isOpenTextButton.setBackgroundResource(R.drawable.button_shape_red);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "해당 아이템 번호 = " + position);
                FoodTruckModel.TRUCK_INFO = homeList.get(position);
                Intent submain = new Intent(mContext, AcitivityTruckDetail.class);
                mContext.startActivity(submain);
            }
        });

    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public static class TruckViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView coverImageView;
        public TextView titleTextView;
        public TextView payTextView;
        public TextView locationTextView;
        public TextView categoryTextView;
        public TextView reviewCountTextView;
        public TextView likeTextView;
        public TextView ratingTextView;
        //public TextView isOpenTextView;
        public TextView isOpenTextButton;


        public TruckViewHolder(View v) {
            super(v);
            coverImageView = (CircleImageView) v.findViewById(R.id.coverImageView);
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            payTextView = (TextView) v.findViewById(R.id.payButton);
            locationTextView = (TextView) v.findViewById(R.id.locationTextView);
            categoryTextView = (TextView) v.findViewById(R.id.categoryTextView);
            reviewCountTextView = (TextView) v.findViewById(R.id.reviewCountTextView);
            likeTextView = (TextView) v.findViewById(R.id.likeTextView);
            ratingTextView = (TextView) v.findViewById(R.id.ratingTextView);
            isOpenTextButton = (Button) v.findViewById(R.id.isOpenTextButton);

        }


    }
}