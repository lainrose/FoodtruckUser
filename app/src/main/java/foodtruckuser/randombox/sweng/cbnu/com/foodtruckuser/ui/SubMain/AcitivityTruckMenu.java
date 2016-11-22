package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.MenuAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.MenuModel;

public class AcitivityTruckMenu extends AppCompatActivity {

    private static RecyclerView myRecyclerView;
    private MenuAdapter menuAdapter;

    //String and Integer array for Recycler View Items
    public static final String[] TITLES= {"디저트 5000원","피자 3000원","박도현 0원","1000원"
            ,"2000원","6000원","디저트 5000원","피자 3000원","박도현 0원","1000원"
            ,"2000원","6000원","디저트 5000원","피자 3000원","박도현 0원","박도현 0원","박도현 0원","박도현 0원"};
    public static final Integer[] IMAGES= {R.drawable.menuitem,R.drawable.menuitem2,R.drawable.menuitem3,R.drawable.menuitem4,R.drawable.menuitem5,
            R.drawable.ic_6,R.drawable.ic_7,R.drawable.ic_8,R.drawable.ic_9,R.drawable.ic_10,R.drawable.intro_pic1,
            R.drawable.intro_pic2,R.drawable.intro_pic3,R.drawable.intro_pic4,R.drawable.intro_pic5,0,0,0};
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_menu);

        setupToolbar();
        //리사이클뷰(카드뷰)
        myRecyclerView = (RecyclerView)findViewById(R.id.menu_view);
        // Here 2 is no. of columns to be displayed
        StaggeredGridLayoutManager MyLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        MyLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(MyLayoutManager);
        showViewList();
    }
    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("스테이크 하우스");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showViewList() {
        ArrayList<MenuModel> listitems = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            listitems.add(new MenuModel(TITLES[i],IMAGES[i]));
        }
        menuAdapter = new MenuAdapter(this, listitems, "AcitivityTruckMenu");
        myRecyclerView.setAdapter(menuAdapter);// set adapter on recyclerview
        menuAdapter.notifyDataSetChanged();// Notify the adapter

    }
}
