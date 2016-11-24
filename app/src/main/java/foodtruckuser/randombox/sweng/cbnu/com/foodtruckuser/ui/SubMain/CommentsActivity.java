package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dd.CircularProgressButton;

import java.util.ArrayList;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.Utill;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.CommentsAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.CommentsModel;

public class CommentsActivity extends AppCompatActivity {
    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";

    RecyclerView commentsView;
    EditText etComment;
    Toolbar toolbar;
    private ArrayList<CommentsModel> commentslist = new ArrayList<>();
    private static final String[] commentsText = {"아 진짜 존맛탱구리네요 개추천!!!", "오지마세요 ㅎㅎ 가보시면 알꺼에요",
            "박도현 방구냄새 = 발냄새 진짜 식당에서 별냄새 다나네요",
            "아 진짜 존맛탱구리네요 개추천!!!", "오지마세요 ㅎㅎ 가보시면 알꺼에요","박도현 방구냄새 = 발냄새 진짜 식당에서 별냄새 다나네요"
    ,"박도현 방구냄새 = 발냄새 진짜 식당에서 별냄새 다나네요","박도현 방구냄새 = 발냄새 진짜 식당에서 별냄새 다나네요",
            "박도현 방구냄새 = 발냄새 진짜 식당에서 별냄새 다나네요","박도현 방구냄새 = 발냄새 진짜 식당에서 별냄새 다나네요"};
    private int FT_IMAGES[] = {R.drawable.truck1, R.drawable.truck2, R.drawable.truck3,
            R.drawable.truck4, R.drawable.truck5,R.drawable.truck1, R.drawable.truck2, R.drawable.truck3,
            R.drawable.truck4, R.drawable.truck5};
    private CommentsAdapter commentsAdapter;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_comments);

            commentsView = (RecyclerView) findViewById(R.id.rvComments);
            etComment = (EditText) findViewById(R.id.etComment);
            setupToolbar();

            initFT();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            commentsView.setLayoutManager(linearLayoutManager);
            commentsView.setHasFixedSize(true);
            commentsAdapter = new CommentsAdapter(this, commentslist);
            commentsView.setAdapter(commentsAdapter);
            commentsView.setOverScrollMode(View.OVER_SCROLL_NEVER);

            final CircularProgressButton btnSendComment = (CircularProgressButton)findViewById(R.id.btnSendComment);
            btnSendComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btnSendComment.getProgress() == 0) {
                        simulateSuccessProgress(btnSendComment);
                    } else {
                        btnSendComment.setProgress(0);
                    }
                }
            });
        }

    private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }

    private void simulateErrorProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if (value == 99) {
                    button.setProgress(-1);
                }
            }
        });
        widthAnimation.start();
    }
    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("스테이크 하우스");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void initFT() {
        commentslist.clear();
        for (int i = 0; i < 10; i++) {
            CommentsModel item = new CommentsModel();
            item.setTitleTextView(commentsText[i]);
            item.setImage(FT_IMAGES[i]);
            commentslist.add(item);
        }
    }
}
