package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.sackcentury.shinebuttonlib.ShineButton;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public ImageView coverImageView;
        public ImageView shareImageView;
        public ShineButton shineButton;
        public TextView payTextView;

        public MyViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            coverImageView = (ImageView) v.findViewById(R.id.coverImageView);
            shareImageView = (ImageView) v.findViewById(R.id.shareImageView);
            shineButton = (ShineButton) v.findViewById(R.id.po_image);
            payTextView = (TextView) v.findViewById(R.id.paytextView);


            if (shineButton != null)
                //shineButton.init(getActivity());
            // 좋아요 버튼 처리 부분
                shineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getActivity(), titleTextView.getText() + "푸드트럭을 좋아요 누름", Toast.LENGTH_SHORT).show();
                }
            });

                //공유 버튼 처리 부분
                shareImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*
                        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                                "://" + getResources().getResourcePackageName(coverImageView.getId())
                                + '/' + "drawable" + '/' + getResources().getResourceEntryName((int)coverImageView.getTag()));
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
                        shareIntent.setType("image/jpeg");
                        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
                        */
                    }
            });
        }
    }