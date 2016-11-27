package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.Acitivity;

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

    private static RecyclerView menu_view;
    private MenuAdapter menuAdapter;
    private Toolbar toolbar;

    public static final String[] TITLES = {"디저트 5000원", "피자 3000원", "박도현 0원", "1000원"
            , "2000원", "6000원", "디저트 5000원", "피자 3000원", "박도현 0원", "1000원"
            , "2000원", "6000원", "디저트 5000원", "피자 3000원", "박도현 0원", "박도현 0원", "박도현 0원", "박도현 0원"};
    public static final Integer[] IMAGES = {R.drawable.menuitem, R.drawable.menuitem2, R.drawable.menuitem3, R.drawable.menuitem4, R.drawable.menuitem5,
            R.drawable.ic_6, R.drawable.ic_7, R.drawable.ic_8, R.drawable.ic_9, R.drawable.ic_10, R.drawable.intro_pic1,
            R.drawable.intro_pic2, R.drawable.intro_pic3, R.drawable.intro_pic4, R.drawable.intro_pic5, 0, 0, 0};
    private ArrayList<MenuModel> menuitems = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_menu);

        setupToolbar();
        initFT();
        intMenuView();
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

    private void intMenuView() {
        menu_view = (RecyclerView) findViewById(R.id.menu_view);
        // Here 2 is no. of columns to be displayed
        StaggeredGridLayoutManager MyLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        MyLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        menu_view.setHasFixedSize(true);
        menu_view.setLayoutManager(MyLayoutManager);
        menuAdapter = new MenuAdapter(this, menuitems, "AcitivityTruckMenu");
        menu_view.setAdapter(menuAdapter);// set adapter on recyclerview
        menuAdapter.notifyDataSetChanged();// Notify the adapter
    }

    private void initFT() {
        menuitems.clear();
        for (int i = 0; i < IMAGES.length; i++) {
            MenuModel item2 = new MenuModel();
            item2.setImage(IMAGES[i]);
            item2.setTitle(TITLES[i]);
            menuitems.add(item2);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
