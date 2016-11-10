package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.MenuModel;

    public class MenuAdapter extends
            RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {// Recyclerview will extend to
        // recyclerview adapter
        private ArrayList<MenuModel> listitems;
        private Context context;

        public MenuAdapter(Context context, ArrayList<MenuModel> listitems) {
            this.context = context;
            this.listitems = listitems;
        }

        @Override
        public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

            ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                    R.layout.menu_item, parent, false);
            MenuViewHolder listHolder = new MenuViewHolder(mainGroup);
            return listHolder;
        }

        @Override
        public void onBindViewHolder(MenuViewHolder holder, int position) {
            final MenuModel model = listitems.get(position);

            MenuViewHolder mainHolder = (MenuViewHolder) holder;
            Bitmap image = BitmapFactory.decodeResource(context.getResources(), model.getImage());
            mainHolder.title.setText(model.getTitle());
            mainHolder.imageview.setImageBitmap(image);
        }

        @Override
        public int getItemCount() {
            return (null != listitems ? listitems.size() : 0);
        }

        public class MenuViewHolder extends RecyclerView.ViewHolder {
            // View holder for griddview recycler view as we used in listview
            public TextView title;
            public ImageView imageview;

            public MenuViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                imageview = (ImageView) view.findViewById(R.id.image);
            }
        }
    }
