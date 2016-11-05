package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private ArrayList<FoodTruckModel> list;
        public MyAdapter(ArrayList<FoodTruckModel> Data) {
            this.list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_items, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.titleTextView.setText(list.get(position).getFtName());
        holder.coverImageView.setImageResource(list.get(position).getFtImage());
        holder.coverImageView.setTag(list.get(position).getFtImage());
        holder.payTextView.setText(list.get(position).getFtPayment());

    }

        @Override
        public int getItemCount() {
            return list.size();
        }
}