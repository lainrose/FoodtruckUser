package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.ServiceGenerator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.MenuModel;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private ArrayList<MenuModel> listitems;
    private Context context;
    private float imageWidth;
    private float imageHeight;
    private MenuViewHolder holder;
    private String call;
    String Url = ServiceGenerator.API_BASE_URL;


    public MenuAdapter(Context context, ArrayList<MenuModel> listitems, String Call) {
        this.context = context;
        this.listitems = listitems;
        this.call = Call;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        ViewGroup v = (ViewGroup) mInflater.inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(v);
    }

    // TODO: 2016-11-30 플레이스 홀더도 원하는 사이즈대로 맞춰야함.
    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final int position) {
        final MenuModel model = listitems.get(position);
        this.holder = holder;

        //이미지 리사이징
        float width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        float margin = (int) convertDpToPixel(10f, (Activity) context);
        imageWidth = ((width - (margin)) / 2);

        if (call.equals("AcitivityTruckMenu")) {
            //float i = ((float) imageWidth) / ((float) image.getWidth());
//            imageHeight = i * (image.getHeight());
            // TODO: 2016-11-30  높이도 리사이징 해줘야함 http://stackoverflow.com/questions/25799967/android-get-drawable-image-after-picasso-loaded
            imageHeight = (float) (imageWidth * 1.1); //나중에..
        } else if (call.equals("AcitivityTruckDetail")) {
            imageHeight = imageWidth;
        }

//            Bitmap image = BitmapFactory.decodeResource(context.getResources(), model.getImage());
//            setBitmapImage(image);

        Picasso.with(context)
                .load(Url + listitems.get(position).getImage().getUrl())
                .resize((int) imageWidth, (int) imageHeight)
                .placeholder(R.drawable.menuitem)
                .into(holder.imageview);
        holder.title.setText(model.getTitle());

        if (call.equals("AcitivityTruckDetail")) {
            if (position == 4) {
                holder.nexttitle.setText("전체 사진 보기");
            } else {
                holder.nexttitle.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != listitems ? listitems.size() : 0);
    }

//    private void setBitmapImage(Bitmap image) {
//        float width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
//        float margin = (int) convertDpToPixel(10f, (Activity) context);
//        // two images, three margins of 10dips
//        imageWidth = ((width - (margin)) / 2);
//        if (image != null) {
//            float i = ((float) imageWidth) / ((float) image.getWidth());
//            if (call.equals("AcitivityTruckMenu"))
//                imageHeight = i * (image.getHeight());
//            else if (call.equals("AcitivityTruckDetail"))
//                imageHeight = imageWidth;
//            holder.imageview.setImageBitmap(Bitmap.createScaledBitmap(image, (int) imageWidth, (int) imageHeight, false));
//        } else {
//            holder.imageview.setImageBitmap(image);
//        }
//    }

    private float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        // View holder for griddview recycler view as we used in listview
        public TextView title;
        public TextView nexttitle;
        public ImageView imageview;

        public MenuViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            nexttitle = (TextView) view.findViewById(R.id.nextimage);
            imageview = (ImageView) view.findViewById(R.id.image);
        }
    }
}