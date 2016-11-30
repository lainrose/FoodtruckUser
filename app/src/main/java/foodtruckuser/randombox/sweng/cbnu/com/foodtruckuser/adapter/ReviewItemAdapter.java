package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.ServiceGenerator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.ReviewModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.Acitivity.AcitivityTruckDetail;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.Acitivity.AcitivityTruckReview;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.Acitivity.ActivityComments;

public class ReviewItemAdapter extends RecyclerView.Adapter<ReviewItemAdapter.ReviewViewHolder>  {

        public static ArrayList<ReviewModel> reviewitems;
        private Context mContext = null;
        public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
        public static final String ACTION_LIKE_IMAGE_CLICKED = "action_like_image_button";

        public ReviewItemAdapter(Context c, ArrayList<ReviewModel> listitems) {
            this.mContext = c;
            this.reviewitems = listitems;
        }

        @Override
        public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
            ReviewViewHolder reviewViewHolder = new ReviewViewHolder(v);
            return reviewViewHolder;

        }

        @Override
        public void onBindViewHolder(final ReviewViewHolder holder, final int position) {

            Picasso.with(mContext).load(ServiceGenerator.API_BASE_URL + reviewitems.get(position).getImage())
                    .resize(holder.ivFeedCenter.getMaxWidth(), holder.ivFeedCenter.getMaxHeight())
                    .into(holder.ivFeedCenter);
            Picasso.with(mContext).load(ServiceGenerator.API_BASE_URL + reviewitems.get(position).getClientImage()).into(holder.userImageView);
            holder.ivFeedBottom.setText(reviewitems.get(position).getContent());
            holder.userImageView.setTag(reviewitems.get(position).getClientImage());
            holder.userTextView.setText(reviewitems.get(position).getClientNickname());

//            holder.ivFeedCenter.setImageResource(reviewitems.get(position).getImage().get);
//            holder.ivFeedBottom.setText(reviewitems.get(position).getReviewText());
//            holder.userImageView.setImageResource(reviewitems.get(position).getUserImage());
//            holder.userImageView.setTag(reviewitems.get(position).getUserImage());
//            holder.userTextView.setText(reviewitems.get(position).getUserText());
//            holder.tsLikesCounter.setCurrentText(holder.vImageRoot.getResources().getQuantityString(
//                        R.plurals.likes_count, reviewitems.get(position).getLikesCount(), reviewitems.get(position).getLikesCount()
//                ));
            holder.btnComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            holder.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            holder.ivFeedCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //여긴 의범이꺼
//                    reviewitems.get(position).setLikesCount(reviewitems.get(position).isLiked() ?
//                            reviewitems.get(position).getLikesCount()-1 : reviewitems.get(position).getLikesCount()+1);
//                    reviewitems.get(position).setLiked(reviewitems.get(position).isLiked() ? false : true);
//                    notifyItemChanged(position, ACTION_LIKE_BUTTON_CLICKED);
//                    if(!reviewitems.get(position).isLiked()){
//                        if (mContext instanceof AcitivityTruckDetail) {
//                            ((AcitivityTruckDetail) mContext).showLikedSnackbar();
//                        }
//                        else if(mContext instanceof AcitivityTruckReview){
//                            ((AcitivityTruckReview) mContext).showLikedSnackbar();
//                        }
//                     }
                }
            });

            holder.reviewlikebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    reviewitems.get(position).setLikesCount(reviewitems.get(position).isLiked() ?
//                        reviewitems.get(position).getLikesCount()-1 : reviewitems.get(position).getLikesCount()+1);
//                    reviewitems.get(position).setLiked(reviewitems.get(position).isLiked() ? false : true);
//                    if(reviewitems.get(position).isLiked()){
//                        if (mContext instanceof AcitivityTruckDetail) {
//                            ((AcitivityTruckDetail) mContext).showLikedSnackbar();
//                        }
//                        else if(mContext instanceof AcitivityTruckReview){
//                            ((AcitivityTruckReview) mContext).showLikedSnackbar();
//                        }
//                    }
                }
            });
            holder.btnComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent intent = new Intent(mContext, ActivityComments.class);
                    int[] startingLocation = new int[2];
                    view.getLocationOnScreen(startingLocation);
                    intent.putExtra(ActivityComments.ARG_DRAWING_START_LOCATION, startingLocation[1]);
                    mContext.startActivity(intent);

                }
            });
            holder.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        @Override
        public int getItemCount() {
            return reviewitems.size();
        }

        public static class ReviewViewHolder extends RecyclerView.ViewHolder {

            private CircleImageView userImageView;
            private TextView userTextView;
            private ImageView ivFeedCenter;
            private TextView ivFeedBottom;
            private ImageButton btnComments;
            public ImageButton btnMore;
            public View vBgLike;
            public ImageView ivLike;
            public TextSwitcher tsLikesCounter;
            public FrameLayout vImageRoot;
            public ShineButton reviewlikebutton;

            public ReviewViewHolder(View v) {
                super(v);
                userImageView = (CircleImageView)v.findViewById(R.id.userImageView);
                userTextView = (TextView)v.findViewById(R.id.userTextView);
                ivFeedCenter = (ImageView)v.findViewById(R.id.ivFeedCenter);
                ivFeedBottom = (TextView)v.findViewById(R.id.ivFeedBottom);
                btnComments = (ImageButton)v.findViewById(R.id.btnComments);
                btnMore = (ImageButton)v.findViewById(R.id.btnMore);
                vBgLike = (View)v.findViewById(R.id.vBgLike);
                ivLike = (ImageView)v.findViewById(R.id.ivLike);
                tsLikesCounter = (TextSwitcher)v.findViewById(R.id.tsLikesCounter);
                vImageRoot = (FrameLayout)v.findViewById(R.id.vImageRoot);
                reviewlikebutton = (ShineButton)v.findViewById(R.id.po_image2);

            }
        }


}