package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.androidstaggeredgridlayoutmanager_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;

import java.util.ArrayList;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;

/**
 * Created by SONU on 25/09/15.
 */
public class RecyclerView_Activity extends AppCompatActivity {
    private static RecyclerView recyclerView;

    //String and Integer array for Recycler View Items
    public static final String[] TITLES= {"Hood","Full Sleeve Shirt","Shirt","Jean Jacket","Jacket"
    ,"Hood","Full Sleeve Shirt","Shirt","Jean Jacket","Jacket", "Hood","Full Sleeve Shirt","Shirt","Jean Jacket","Jacket"};
    public static final Integer[] IMAGES= {R.drawable.ic_1,R.drawable.ic_2,R.drawable.ic_3,R.drawable.ic_4,R.drawable.ic_5,
            R.drawable.ic_6, R.drawable.ic_7,R.drawable.ic_8,R.drawable.ic_9,R.drawable.ic_10,0,0,0,0,0};

    private static String navigateFrom;//String to get Intent Value
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        initViews();
        populatRecyclerView();
    }

    // Initialize the view
    private void initViews() {
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Set Back Icon on Activity

        navigateFrom = getIntent().getStringExtra("navigateFrom");//Get Intent Value in String

        recyclerView = (RecyclerView)
                findViewById(R.id.recycler1_view);
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));// Here 2 is no. of columns to be displayed

}


    // populate the list view by adding data to arraylist
    private void populatRecyclerView() {
        ArrayList<Data_Model> arrayList = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            arrayList.add(new Data_Model(TITLES[i],IMAGES[i]));
        }
        RecyclerView_Adapter  adapter = new RecyclerView_Adapter(RecyclerView_Activity.this, arrayList);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview
        adapter.notifyDataSetChanged();// Notify the adapter

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}