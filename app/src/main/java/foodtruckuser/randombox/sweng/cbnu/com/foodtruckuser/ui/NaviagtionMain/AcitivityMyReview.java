package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.NaviagtionMain;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import java.util.ArrayList;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.ReviewItemAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.ReviewItemAnimator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.ReviewModel;

public class AcitivityMyReview extends AppCompatActivity {

    private ReviewItemAdapter reviewAdapter;
    private RecyclerView review_view;
    private Toolbar toolbar;
    private LinearLayout clContent;
    private String btnName;

    private ArrayList<ReviewModel> reviewitems = new ArrayList<>();

    private static final Integer[] CenterIMAGES= {0, 0, 0, R.drawable.menuitem2, R.drawable.menuitem3};

    private static final Integer[] BottomIMAGES= {R.drawable.img_feed_bottom_1, R.drawable.img_feed_bottom_2,
            R.drawable.img_feed_bottom_1, R.drawable.img_feed_bottom_2, R.drawable.img_feed_bottom_1};

    private static final Integer[] UserIMAGES= {R.drawable.truck1, R.drawable.truck2, R.drawable.truck3,
            R.drawable.truck4, R.drawable.truck5};

    private static final Integer[] LikeConunts= {33,112,63,235,100533};

    private static final String[] UserNames= {"도혀니", "으버미", "현펴", "횬죵이", "영비니"};

    private static final String[] WriteText = {"이쁜 현정아!~", "기숙사 가서 졸지말고!", "리뷰 빨리 옮기거랔ㅋㅋㅋ\n사진 없는거 그대로나오는거" +
            "는 나중에 디비연결하고 다시 리사이징 할꺼야 글씨만 나오게 지금 기능 되는거 사진 누르면 좋아요하면 라이크갯수 왔다갔다만함" +
            "나머지는 되면 그때 말해줌!"
    ,"이식당 맛없어여 이러려고 여기왔나 자괴감 들고....", "개존망탱구리네요"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_review);
        clContent = (LinearLayout) findViewById(R.id.content);
        setupToolbar();
        initView();
        initReviewView();
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

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("나의 리뷰");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initReviewView(){
        review_view = (RecyclerView)findViewById(R.id.review_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        review_view.setHasFixedSize(true);
        review_view.setLayoutManager(linearLayoutManager);
        reviewAdapter = new ReviewItemAdapter(this, reviewitems);
        review_view.setAdapter(reviewAdapter);
        review_view.setItemAnimator(new ReviewItemAnimator());
    }

    private void initFT(){
        reviewitems.clear();
//        for (int i = 0; i < 5; i++) {
//            ReviewModel item1 = new ReviewModel();
//            item1.setCenterimage(CenterIMAGES[i]);
//            item1.setBottomimage(BottomIMAGES[i]);
//            item1.setUserImage(UserIMAGES[i]);
//            item1.setUserText(UserNames[i]);
//            item1.setReviewText(WriteText[i]);
//            item1.setLikesCount(LikeConunts[i]);
//            reviewitems.add(item1);
//        }
    }
    private void initView() {
       initFT();
    }

    public void showLikedSnackbar() {
        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

}
