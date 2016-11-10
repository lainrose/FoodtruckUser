package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.MenuModel;

    public class MenuAdapter extends
            RecyclerView.Adapter<MenuAdapter.MyViewHolder> {// Recyclerview will extend to
        // recyclerview adapter
        private ArrayList<MenuModel> listitems;
        private Context context;

        public MenuAdapter(Context context, ArrayList<MenuModel> listitems) {
            this.context = context;
            this.listitems = listitems;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final MenuModel model = listitems.get(position);

            MyViewHolder mainHolder = (MyViewHolder) holder;
            Bitmap image = BitmapFactory.decodeResource(context.getResources(), model.getImage());
            mainHolder.title.setText(model.getTitle());
            mainHolder.imageview.setImageBitmap(image);
        }

        @Override
        public int getItemCount() {
            return listitems.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            // View holder for griddview recycler view as we used in listview
            public TextView title;
            public ImageView imageview;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                imageview = (ImageView) view.findViewById(R.id.image);
            }
        }
    }
