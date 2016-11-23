package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.ReviewModel;

public class ReviewItemAdapter extends RecyclerView.Adapter<ReviewItemAdapter.ReviewViewHolder>  {

        private ArrayList<ReviewModel> listitems;
        private Context mContext = null;

        public ReviewItemAdapter(Context c, ArrayList<ReviewModel> listitems) {
            this.mContext = c;
            this.listitems = listitems;
        }

        @Override
        public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
            return new ReviewViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ReviewViewHolder holder, final int position) {

            holder.ivFeedCenter.setImageResource(listitems.get(position).getCenterimage());
            holder.ivFeedBottom.setImageResource(listitems.get(position).getBottomimage());
            holder.userImageView.setImageResource(listitems.get(position).getUserImage());
            holder.userImageView.setTag(listitems.get(position).getUserImage());
            holder.userTextView.setText(listitems.get(position).getUserText());

        }

        @Override
        public int getItemCount() {
            return listitems.size();
        }

        public static class ReviewViewHolder extends RecyclerView.ViewHolder {

            private CircleImageView userImageView;
            private TextView userTextView;
            private ImageView ivFeedCenter;
            private ImageView ivFeedBottom;
            private ImageButton btnComments;
            private ImageButton btnLike;
            private ImageButton btnMore;
            private View vBgLike;
            private ImageView ivLike;
            private TextSwitcher tsLikesCounter;
            private ImageView ivUserProfile;
            private FrameLayout vImageRoot;

            public ReviewViewHolder(View v) {
                super(v);
                userImageView = (CircleImageView)v.findViewById(R.id.userImageView);
                userTextView = (TextView)v.findViewById(R.id.userTextView);
                ivFeedCenter = (ImageView)v.findViewById(R.id.ivFeedCenter);
                ivFeedBottom = (ImageView)v.findViewById(R.id.ivFeedBottom);
                btnComments = (ImageButton)v.findViewById(R.id.btnComments);
                btnLike = (ImageButton)v.findViewById(R.id.btnLike);
                btnMore = (ImageButton)v.findViewById(R.id.btnMore);
                vBgLike = (View)v.findViewById(R.id.vBgLike);
                ivLike = (ImageView)v.findViewById(R.id.ivLike);
                tsLikesCounter = (TextSwitcher)v.findViewById(R.id.tsLikesCounter);
                ivUserProfile = (ImageView)v.findViewById(R.id.ivUserProfile);
                vImageRoot = (FrameLayout)v.findViewById(R.id.vImageRoot);

            }


        }
}