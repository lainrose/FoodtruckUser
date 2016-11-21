package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.MenuModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.FragmentMenu;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main.SentFragment;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {// Recyclerview will extend to
        // recyclerview adapter
        private ArrayList<MenuModel> listitems;
        private Context context;
        private float imageWidth;
        private MenuViewHolder holder;
        FragmentManager mFragmentManager;

        public MenuAdapter(Context context, ArrayList<MenuModel> listitems) {
            this.context = context;
            this.listitems = listitems;
        }

        @Override
        public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

            ViewGroup v = (ViewGroup) mInflater.inflate(R.layout.menu_item, parent, false);
             return new MenuViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MenuViewHolder holder, int position) {
            final MenuModel model = listitems.get(position);
            this.holder = holder;
            Bitmap image = BitmapFactory.decodeResource(context.getResources(), model.getImage());
            setBitmapImage(image);
            holder.title.setText(model.getTitle());
        }

        @Override
        public int getItemCount() {
            return (null != listitems ? listitems.size() : 0);
        }

        private void setBitmapImage(Bitmap image){
            float width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
            float height = ((Activity)context).getWindowManager().getDefaultDisplay().getHeight();
            float margin = (int)convertDpToPixel(10f, (Activity)context);
            // two images, three margins of 10dips
            imageWidth = ((width - (margin)) / 2);
            if(image != null){
                float i = ((float) imageWidth) / ((float) image.getWidth());
                //float imageHeight = i * (image.getHeight());
                float imageHeight = imageWidth;
                holder.imageview.setImageBitmap(Bitmap.createScaledBitmap(image, (int)imageWidth, (int)imageHeight,false));
            }
            else{
                holder.imageview.setImageBitmap(image);
            }
        }
        private float convertDpToPixel(float dp, Context context){
            Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float px = dp * (metrics.densityDpi/160f);
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