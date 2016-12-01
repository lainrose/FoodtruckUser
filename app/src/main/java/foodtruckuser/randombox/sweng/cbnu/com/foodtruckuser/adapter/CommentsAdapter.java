package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.CommentsModel;

//
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private Context context;
    private ArrayList<CommentsModel> commentslist;

    public CommentsAdapter(Context context, ArrayList<CommentsModel> commentslist) {
        this.context = context;
        this.commentslist = commentslist;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.ivUserAvatar.setImageResource(commentslist.get(position).getImage());
        holder.tvComment.setText(commentslist.get(position).getTitleTextView());
    }
    @Override
    public int getItemCount() {
        return commentslist.size();
    }


    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivUserAvatar;
        private TextView tvComment;

        public CommentViewHolder(View view) {
            super(view);
            tvComment = (TextView) view.findViewById(R.id.tvComment);
            ivUserAvatar = (ImageView) view.findViewById(R.id.ivUserAvatar);

        }
    }
}
