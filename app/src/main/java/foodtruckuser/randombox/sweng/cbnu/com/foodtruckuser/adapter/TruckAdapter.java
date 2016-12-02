package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Text;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Set;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.ServiceGenerator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.preference.PrefHelper;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.ApiService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.Acitivity.AcitivityTruckDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TruckAdapter extends RecyclerView.Adapter<TruckAdapter.TruckViewHolder> {

    private ArrayList<FoodTruckModel> homeList;
    private Context mContext = null;
    private String call;
    private Set<String> likedTruckIdSet;
    String Url = ServiceGenerator.API_BASE_URL;

    public TruckAdapter(Context c, ArrayList<FoodTruckModel> listitems, String call) {
        this.mContext = c;
        this.homeList = listitems;
        this.call = call;
        likedTruckIdSet = PrefHelper.getInstance(mContext).getLikedTruckId();
    }

    @Override
    public TruckViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.truck_item, parent, false);
        return new TruckViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TruckViewHolder holder, final int position) {


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
        holder.titleTextView.setText(homeList.get(position).getFtName() + " : " + category);
        Picasso.with(mContext).load(Url + homeList.get(position).getFT_IMAGE_URL().getUrl()).into(holder.coverImageView);

        //카드 여부
        if (homeList.get(position).getFtPayment() == "false") {
            holder.payButton.setBackgroundResource(R.drawable.button_shape_green_main);
        } else {
            holder.payButton.setBackgroundResource(R.drawable.button_shape_red_main);        }

        //영업여부
        if (homeList.get(position).isFT_isOPEN() == true) {
            holder.isOpenTextButton.setBackgroundResource(R.drawable.button_shape_green_main);
        } else {
            holder.isOpenTextButton.setBackgroundResource(R.drawable.button_shape_red_main);
        }

        // TODO: 2016-12-01 어플 껐다 켰을 때 체크 안됨
        //Set에 푸드트럭 ID있나 검사해서 있으면 체크 해준다. 없는애들은 다 해제
        if (call.equals("FragmentHome")) {
            if (likedTruckIdSet.contains(homeList.get(position).getFT_ID())) {
                Log.d("TEST", "프홈" + homeList.get(position).getFtName());
                holder.shineButton.setChecked(true);
                homeList.get(position).setFT_LIKE(true);
            } else {
                holder.shineButton.setChecked(false);
                homeList.get(position).setFT_LIKE(false);
            }
            //내 푸드트럭 리스트는 무조건 좋아요 눌려있어야 하니까 설정
        } else if (call.equals("FragmentMytruck")) {
            Log.d("TEST", "프마" + homeList.get(position).getFtName());
            holder.shineButton.setChecked(true);
            homeList.get(position).setFT_LIKE(true);
        }

        //트럭 클릭하면 Activity Detail로 이동하는 온클릭 리스너.
        holder.coverImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "해당 아이템 번호 = " + position);
                Intent submain = new Intent(mContext, AcitivityTruckDetail.class);
//                submain.putExtra("clickedFoodTruck", homeList.get(position));
                FoodTruckModel.TRUCK_INFO = homeList.get(position);
                mContext.startActivity(submain);
                //overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });

        //좋아요버튼 리스너
        holder.shineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //좋아요 누르면, 서버에 넣어주고 Set에도 넣어서 Pref로 저장
                if (!homeList.get(position).isFT_LIKE()) {
                    requestAddLikeTruck(holder, position); //좋아요 상태 true 해주는건 이 함수에
                    likedTruckIdSet.add(homeList.get(position).getFT_ID());
                    PrefHelper.getInstance(mContext).setLikedTruckid(likedTruckIdSet);
                    //좋아요 취소하면, 서버에 넣어주고 Set에도 넣어서 Pref로 저장
                } else {
                    requestRemoveLikeTruck(holder, position); //좋아요 상태 false 해주는건 이 함수에
                    likedTruckIdSet.remove(homeList.get(position).getFT_ID());
                    PrefHelper.getInstance(mContext).setLikedTruckid(likedTruckIdSet);
                }
            }
        });
    }

    public void requestAddLikeTruck(final TruckViewHolder holder, final int position) {
        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<Boolean> convertedContent = service.add_like_truck(UserModel.getInstance().getUserId(), homeList.get(position).getFT_ID());
        convertedContent.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("TAG", "좋아요 푸드트럭" + response.body().toString());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        Toast.makeText(mContext, holder.titleTextView.getText() + "을" + " 좋아요!", Toast.LENGTH_SHORT).show();
        homeList.get(position).setFT_LIKE(true);
    }

    public void requestRemoveLikeTruck(final TruckViewHolder holder, final int position) {
        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<Boolean> convertedContent = service.delete_like_truck(UserModel.getInstance().getUserId(), homeList.get(position).getFT_ID());
        convertedContent.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("TAG", "좋아요 취소 푸드트럭" + response.body().toString());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        Toast.makeText(mContext, holder.titleTextView.getText() + "을" + " 좋아요 취소!", Toast.LENGTH_SHORT).show();
        homeList.get(position).setFT_LIKE(false);
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public static class TruckViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public ImageView coverImageView;
        public ShineButton shineButton;
        public Button payButton;
        public TextView isOpenTextButton;


        public TruckViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            coverImageView = (ImageView) v.findViewById(R.id.coverImageView);
            shineButton = (ShineButton) v.findViewById(R.id.po_image);
            payButton = (Button) v.findViewById(R.id.payButton);
            isOpenTextButton = (Button) v.findViewById(R.id.isOpenTextButton);

        }
    }
}