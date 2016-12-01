package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.SubMain.Acitivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hedgehog.ratingbar.RatingBar;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Set;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.RecyclerItemClickListener;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.ServiceGenerator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.Utill;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.ReviewItemAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.MenuAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.ReviewItemAnimator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.TruckAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.MenuModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.ReviewModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.preference.PrefHelper;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.ApiService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.GpsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcitivityTruckDetail extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, OnMapReadyCallback, View.OnClickListener {

    private Toolbar toolbar;
    public GoogleMap map;
    private GpsService gpsService;
    public static LatLng TruckLatLng;
    private MarkerOptions optFirst;
    private MapView mapview;
    private GoogleApiClient mGoogleApiClient;
    private Context mContext;
    private CoordinatorLayout clContent;
    private ImageView ivHeaderImage;
    private RecyclerView menu_view;
    private RecyclerView review_view;
    private MenuAdapter menuAdapter;
    private ReviewItemAdapter reviewAdapter;
    private Bundle savedInstanceState;
    private CollapsingToolbarLayout collapsingToolbar;
    private ShineButton likebtn;
    private ArrayList<FoodTruckModel> listitems = new ArrayList<>();
    private ArrayList<ReviewModel> reviewitems = new ArrayList<>();
    private ArrayList<MenuModel> menuitems = new ArrayList<>();
    private FoodTruckModel item;
    private ShareDialog shareDialog;
    private CallbackManager callbackManager;
    private Set<String> likedTruckIdSet;


    String Url = ServiceGenerator.API_BASE_URL;

    private String FT_NAME[] = {"도현트럭", "의범트럭",
            "영빈트럭", "현표트럭", "현정트럭"};

    private int FT_CATEGORY[] = {1, 2, 3, 3, 5};

    private String FT_PAYMENT[] = {"카드", "현금", "카드/현금", "현금", "카드"};

    private int FT_IMAGES[] = {R.drawable.truck1, R.drawable.truck2, R.drawable.truck3,
            R.drawable.truck4, R.drawable.truck5};

    private Double FT_X[] = {35.841979, 35.840776, 35.840550, 35.841672, 35.842655};

    private Double FT_Y[] = {127.133218, 127.132788, 127.133936, 127.135846, 127.133335};

    public static final String[] TITLES = {"디저트 5000원", "피자 3000원", "박도현 0원", "1000원"
            , "2000원", "6000원", "디저트 5000원", "피자 3000원", "박도현 0원", "1000원"
            , "2000원", "6000원", "디저트 5000원", "피자 3000원", "박도현 0원", "박도현 0원", "박도현 0원", "박도현 0원"};

    public static final Integer[] IMAGES = {R.drawable.menuitem, R.drawable.menuitem2, R.drawable.menuitem3, R.drawable.menuitem4, R.drawable.menuitem5,
            R.drawable.ic_6, R.drawable.ic_7, R.drawable.ic_8, R.drawable.ic_9, R.drawable.ic_10, R.drawable.menuitem,
            R.drawable.menuitem2, R.drawable.menuitem3, R.drawable.menuitem4, R.drawable.menuitem5, 0, 0, 0};

    private static final Integer[] CenterIMAGES = {0, 0, 0, R.drawable.menuitem2, R.drawable.menuitem3};

    private static final Integer[] BottomIMAGES = {R.drawable.img_feed_bottom_1, R.drawable.img_feed_bottom_2,
            R.drawable.img_feed_bottom_1, R.drawable.img_feed_bottom_2, R.drawable.img_feed_bottom_1};

    private static final Integer[] UserIMAGES = {R.drawable.truck1, R.drawable.truck2, R.drawable.truck3,
            R.drawable.truck4, R.drawable.truck5};

    private static final Integer[] LikeConunts = {33, 112, 63, 235, 100533};

    private static final String[] UserNames = {"도혀니", "으버미", "현펴", "횬죵이", "영비니"};

    private static final String[] WriteText = {"가나다라마바사아자차카타파하", "안녕하세요! \n" +
            "지난 주말 좋은 체험했던 맛집 리뷰에 \n" +
            "악플로 괴롭힘을 받아서 맛집 리뷰는 \n" +
            "공감만 열어놓습니다.\n" +
            "제가 느낀점을 솔직하게 쓴 후기에 \n" +
            "악플로 상처받는 일은 \n" +
            "앞으로 없었으면 합니다. ", "늘 소통해주시고 자주 와주시는 \n" +
            "이웃분들께 양해부탁드립니다.", "공감은 열어두니 공감버튼 눌러주시면\n" +
            "댓글 주시는것과 같은걸로 생각할게요. \n" +
            "편안하게 글을 봐주셨으면해요. ", "아울러 악플러는 많은 시간을 들여 \n" +
            "맛집을 다녀오고\n" +
            "포스팅을 하고 블로그를 꾸며온 사람에게 \n" +
            "본인기분 상한 것을 \n" +
            "화풀이 하지 않았으면합니다.", "악플러는 본인글 읽어보고 망신인거 알고\n" +
            "반성하고 미안한 마음좀 갖길바랍니다."};
    private KakaoLink kakaoLink;
    private KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_detail);
        mContext = this;
        this.savedInstanceState = savedInstanceState;

        likedTruckIdSet = PrefHelper.getInstance(mContext).getLikedTruckId();

//        item = (FoodTruckModel) getIntent().getSerializableExtra("clickedFoodTruck");
        item = FoodTruckModel.getInstance();
        Log.d("TAG", "클릭된 푸드트럭 이름 : " + item.getFtName());

        //FoodTruckModel.TRUCK_INFO = item; //싱글톤 테스트

        initId(item);
        initToolbar(item);
        initCollapsingToolbar();
        initFT();
        initMap();
        initRatingBar(item);
        initMenuView();
        initReviewView();

        //카톡공유
        try {
            kakaoLink = KakaoLink.getKakaoLink(getApplicationContext());
            kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
        } catch (KakaoParameterException e) {
            alert(e.getMessage());
        }
        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendKakaoTalkLink();
                kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
            }
        });
        //페북공유
        FloatingActionButton actionB = (FloatingActionButton) findViewById(R.id.action_b);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFacebookLink();
            }
        });
        //리뷰쓰기
        FloatingActionButton actionC = (FloatingActionButton) findViewById(R.id.action_c);
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utill.getInstance().MoveAcitivity(getApplicationContext(), AcitivityWriting.class);
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });

        // TODO: 2016-12-01 껐다켰을 때 지랄나는거
        // TODO: 2016-12-01 액티비티 디테일에서 눌렀을 때 프레그먼트로 나와서 리프레시
        likebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!item.isFT_LIKE()) {
                    requestAddLikeTruck(); //좋아요 상태 true 해주는건 이 함수에
                    likedTruckIdSet.add(item.getFT_ID());
                    PrefHelper.getInstance(mContext).setLikedTruckid(likedTruckIdSet);
                } else {
                    requestRemoveLikeTruck(); //좋아요 상태 false 해주는건 이 함수에
                    likedTruckIdSet.remove(item.getFT_ID());
                    PrefHelper.getInstance(mContext).setLikedTruckid(likedTruckIdSet);
                }

                showLikedSnackbar();
            }
        });
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Utill.getInstance().MoveAcitivity(mContext, ActivityMap.class, FT_X[0], FT_Y[0]);
            }
        });
        mapview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utill.getInstance().MoveAcitivity(mContext, ActivityMap.class, FT_X[0], FT_Y[0]);
            }
        });

    }

    // 사라지는 상단 이미지 설정
    private void initCollapsingToolbar() {
        collapsingToolbar.setTitleEnabled(false);
    }

    // 툴바 백버튼 설정
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

    // 툴바 설정
    private void initToolbar(FoodTruckModel item) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(item.getFtName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // 리사이클러_리뷰 설정
    private void initReviewView() {
        review_view = (RecyclerView) findViewById(R.id.review_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        review_view.setHasFixedSize(true);
        review_view.setNestedScrollingEnabled(false);
        review_view.setLayoutManager(linearLayoutManager);

        requestReview(item.getFT_ID());
    }

    public void showReviewList(ArrayList<ReviewModel> reviewitems) {
        reviewAdapter = new ReviewItemAdapter(this, reviewitems);
        review_view.setAdapter(reviewAdapter);
        review_view.setItemAnimator(new ReviewItemAnimator());
    }


    public void requestReview(String foodtruck_id) {
        reviewitems.clear();

        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<ArrayList<ReviewModel>> convertedContent = service.foodtruck_reviews(foodtruck_id);

        convertedContent.enqueue(new Callback<ArrayList<ReviewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ReviewModel>> call, Response<ArrayList<ReviewModel>> response) {
                ArrayList<ReviewModel> ReviewList = response.body();

                Log.d("TAG", "바디: " + response.body().toString());

                for (ReviewModel review : ReviewList
                        ) {
                    reviewitems.add(review);
                }
                showReviewList(reviewitems);
                ReviewModel.REVIEW_LIST = reviewitems;
            }

            @Override
            public void onFailure(Call<ArrayList<ReviewModel>> call, Throwable t) {
                Log.d("실패", "onFailure: ");
                Log.d("TAG", t.getMessage());
            }
        });
    }

    // 리사이클러_메뉴 설정
    private void initMenuView() {
        menu_view = (RecyclerView) findViewById(R.id.menu_view);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        menu_view.setHasFixedSize(true);
        menu_view.setLayoutManager(MyLayoutManager);

        requestFoodtruckMenu(item.getFT_ID());
    }

    private void showMenuCardViewList(ArrayList<MenuModel> menuitems) {
        menuAdapter = new MenuAdapter(this, menuitems, "AcitivityTruckDetail");
        menu_view.setAdapter(menuAdapter); // set adapter on recyclerview
        menuAdapter.notifyDataSetChanged(); // Notify the adapter
        menu_view.addOnItemTouchListener(
                new RecyclerItemClickListener(this, menu_view, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == 4) {
                            Utill.getInstance().MoveAcitivity(mContext, AcitivityTruckMenu.class);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }

    // 평점 설정
    private void initRatingBar(FoodTruckModel item) {
        RatingBar mRatingBar = (RatingBar) findViewById(R.id.Ratingbar);
        mRatingBar.setStarEmptyDrawable(getResources().getDrawable(R.drawable.ic_star_empty));
        mRatingBar.setStarFillDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
        mRatingBar.setStarHalfDrawable(getResources().getDrawable(R.drawable.ic_star_half));
        mRatingBar.setStarCount(5);
        mRatingBar.setStar((float) item.getFtRating());
        mRatingBar.halfStar(true);
        mRatingBar.setmClickable(false);
        mRatingBar.setStarImageWidth(120f);
        mRatingBar.setStarImageHeight(60f);
        mRatingBar.setImagePadding(35);
    }

    // 맵 설정
    private void initMap() {
        mapview = (MapView) findViewById(R.id.truck_detail_map);
        mapview.onCreate(savedInstanceState);
        mapview.onResume();
        mapview.getMapAsync(this);
        mapview.setClickable(false);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        map = mapview.getMap();
        TruckLatLng = new LatLng(listitems.get(0).getFT_LNG(), listitems.get(0).getFT_LAT());
        optFirst = new MarkerOptions();
        optFirst.position(TruckLatLng);
        optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_like_select));
        map.addMarker(optFirst).showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLng(TruckLatLng));
        map.getUiSettings().setAllGesturesEnabled(false);
        map.animateCamera(CameraUpdateFactory.zoomTo(14));
    }

    // TODO: 2016-11-26 트럭 이미지 및 리뷰 설정 -> DB연동 바람
    private void initFT() {
        listitems.clear();
        reviewitems.clear();
        for (int i = 0; i < 5; i++) {
            FoodTruckModel item = new FoodTruckModel();
            //item.setFtName(FT_NAME[i]);
            //item.setFtImage(FT_IMAGES[i]);
            item.setFtCategory(FT_CATEGORY[i]);
            item.setFtPayment(FT_PAYMENT[i]);
            item.setFT_LNG(FT_X[i]);
            item.setFT_LAT(FT_Y[i]);
            listitems.add(item);

//            ReviewModel item1 = new ReviewModel();
//            item1.setCenterimage(CenterIMAGES[i]);
//            item1.setBottomimage(BottomIMAGES[i]);
//            item1.setUserImage(UserIMAGES[i]);
//            item1.setUserText(UserNames[i]);
//            item1.setReviewText(WriteText[i]);
//            item1.setLikesCount(LikeConunts[i]);
//            reviewitems.add(item1);

//            MenuModel item2 = new MenuModel();
//            item2.setImage(IMAGES[i]);
//            item2.setTitle(TITLES[i]);
//            menuitems.add(item2);
        }
    }

    // 화면에 뿌려질 id 연결 및 설정
    private void initId(FoodTruckModel item) {
        setContentView(R.layout.activity_truck_detail);

        gpsService = new GpsService(this);
        clContent = (CoordinatorLayout) findViewById(R.id.content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        likebtn = (ShineButton) findViewById(R.id.likebtn);
        ivHeaderImage = (ImageView) findViewById(R.id.header);
        FacebookSdk.sdkInitialize(getApplicationContext()); //페이스북SDK 초기화
        callbackManager = CallbackManager.Factory.create(); //콜백메소드 생성
        shareDialog = new ShareDialog(this);

        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        TextView hashTextView = (TextView) findViewById(R.id.hashTextView);
        TextView reviewCountTextView = (TextView) findViewById(R.id.reviewCountTextView);
        TextView likeTextView = (TextView) findViewById(R.id.likeTextView);
        TextView ratingTextView = (TextView) findViewById(R.id.ratingTextView);
        ImageView openImage = (ImageView) findViewById(R.id.openImage);
        TextView openText = (TextView) findViewById(R.id.openText);
        TextView openingText = (TextView) findViewById(R.id.openingText);
//        TextView openingText1 = (TextView)findViewById(R.id.openingText1);
//        TextView openingText2 = (TextView)findViewById(R.id.openingText2);
        TextView addressTextView = (TextView) findViewById(R.id.addressTextView);
        TextView timerTextView = (TextView) findViewById(R.id.timerTextView);
        TextView phoneTextView = (TextView) findViewById(R.id.phoneTextView);
        TextView opentileTextView = (TextView) findViewById(R.id.opentileTextView);
        TextView emoticonText1 = (TextView) findViewById(R.id.emoticonText1);
        TextView emoticonText2 = (TextView) findViewById(R.id.emoticonText2);
        TextView emoticonText3 = (TextView) findViewById(R.id.emoticonText3);

        //FoodTruckModel item = new FoodTruckModel();
        Drawable ic_check_on = getResources().getDrawable(R.drawable.ic_check_on);
        Drawable ic_check_off = getResources().getDrawable(R.drawable.ic_check_off);

        //이미지 : 완료
        Picasso.with(mContext).load(Url + item.getFT_IMAGE_URL().getUrl()).into(ivHeaderImage);
        titleTextView.setText(item.getFtName());
        //태그 : 완료
        hashTextView.setText(item.getFtTag());
        //리뷰 카운트 수 : 완료
        reviewCountTextView.setText("" + item.getFT_REVIEW_NUM());
        //좋아요 카운트 수 : 완료
        likeTextView.setText("" + item.getFT_LIKE_NUM());
        //평점 : 완료
        ratingTextView.setText("" + item.getFtRating());
        //영업중 이미지 : 완료
        openImage.setImageDrawable(item.isFT_isOPEN() ? ic_check_on : ic_check_off);
        //영업중 이미지 아래 텍스트 : 완료
        openText.setText(item.isFT_isOPEN() ? "영업 중" : "영업 전");
        //영업중 이미지 옆 텍스트. : 완료
        openingText.setText(item.isFT_isOPEN() ? "영업 중" : "영업 종료");
//        //영업중이면 null, 영업 종료면 몇시에 영업종료 했는지
//        openingText1.setText(item.getOpeningText1());
//        //영업중이면 영업 시작부터 지금까지 시간, 영업 종료면 영업 종료부터 지금까지 시간.
//        openingText2.setText(item.getOpeningText2());
        //주소
        addressTextView.setText(gpsService.findAddress(item.getFT_LAT(), item.getFT_LNG()));
        //현 위치로부터 트럭이 몇분 거리에 떨어져있는지
        timerTextView.setText(item.getTimerTextView());
        //핸드폰 번호
        phoneTextView.setText(item.getPhoneTextView());
        //아마 영업시간 텍스트뷰
        opentileTextView.setText(item.getOpentimeTextView());
        //이건 안고쳐도 될듯
        emoticonText1.setText("맛있다! (" + item.getEmoticonText1() + ")");
        emoticonText2.setText("괜찮다 (" + item.getEmoticonText2() + ")");
        emoticonText3.setText("별로 (" + item.getEmoticonText3() + ")");

        if(item.isFT_LIKE()) {
            likebtn.setChecked(true);
        } else {
            likebtn.setChecked(false);
        }

    }

    // 맵 관련 처리
    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
    }

    // 하단의 스내바(알림창)
    public void showLikedSnackbar() {
        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
    }

    // 화면에 들어가는 모든 버튼 설정
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_review_more) {
            Utill.getInstance().MoveAcitivity(this, AcitivityTruckReview.class, "btn_review_more");
        } else if (view.getId() == R.id.emoticonBtn1) {
            Utill.getInstance().MoveAcitivity(this, AcitivityTruckReview.class, "emoticonBtn1");
        } else if (view.getId() == R.id.emoticonBtn2) {
            Utill.getInstance().MoveAcitivity(this, AcitivityTruckReview.class, "emoticonBtn2");
        } else if (view.getId() == R.id.emoticonBtn3) {
            Utill.getInstance().MoveAcitivity(this, AcitivityTruckReview.class, "emoticonBtn3");
        } else if (view.getId() == R.id.visitHomepage) {
            FoodTruckModel item = new FoodTruckModel();
            Utill.getInstance().MoveAcitivity(this, ActivityWebView.class, item.getVisitHomepage());
        } else if (view.getId() == R.id.phoneTextLayout) {
            Utill.getInstance().MoveAcitivity(this, item.getPhoneTextView());
        } else if (view.getId() == R.id.addressTextView) {
            if (Utill.isAndroid4()) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(gpsService.findAddress(item.getFT_LAT(), item.getFT_LNG()));
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("주소가 복사되었습니다.", gpsService.findAddress(item.getFT_LAT(), item.getFT_LNG()));
                clipboard.setPrimaryClip(clip);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    private void sendFacebookLink() {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("푸드트럭 발전여부")
                    .setContentDescription("푸드트럭 놀러오세요~")
                    .setImageUrl(Uri.parse("http://ilyo.co.kr/contents/article/images/2016/0909/1473355983769129.jpg"))
                    .setContentUrl(Uri.parse("http://software.jbnu.ac.kr"))
                    .build();
            shareDialog.show(linkContent);
        }
        /*
        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
        int imageid = R.drawable.truck5;
        Bitmap image = BitmapFactory.decodeResource(this.getResources(), imageid);
                 SharePhoto sharePhoto = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent photoContent = new SharePhotoContent.Builder()
                .addPhoto(sharePhoto)
                .build();
                */
    }

    private void sendKakaoTalkLink() {
        try {
            kakaoTalkLinkMessageBuilder.addText("맜있는 푸드트럭!!!!!");
            kakaoTalkLinkMessageBuilder.addImage("https://pbs.twimg.com/profile_images/662419409600811009/lRH4GDHK.jpg", 300, 200);
            kakaoTalkLinkMessageBuilder.addWebLink("겟잇트렁 다운받기", "http://software.jbnu.ac.kr");
            kakaoTalkLinkMessageBuilder.addWebButton("해당 앱 실행하기", "http://software.jbnu.ac.kr");
            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, this);
        } catch (KakaoParameterException e) {
            alert(e.getMessage());
        }
    }

    //대화상자띄우기
    private void alert(String message) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .create().show();
    }
    public void requestAddLikeTruck() {
        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<Boolean> convertedContent = service.add_like_truck(UserModel.USER_INFO.getUserId(), item.getFT_ID());
        convertedContent.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("TAG", "좋아요 푸드트럭" + response.body().toString());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        Toast.makeText(mContext, item.getFtName() + "을" + " 좋아요!", Toast.LENGTH_SHORT).show();
        item.setFT_LIKE(true);
    }

    public void requestRemoveLikeTruck() {
        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<Boolean> convertedContent = service.delete_like_truck(UserModel.USER_INFO.getUserId(), item.getFT_ID());
        convertedContent.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("TAG", "좋아요 취소 푸드트럭" + response.body().toString());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        Toast.makeText(mContext, item.getFtName() + "을" + " 좋아요 취소!", Toast.LENGTH_SHORT).show();
        item.setFT_LIKE(false);
    }

    public void requestFoodtruckMenu(String id) {
        menuitems.clear();

        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<ArrayList<MenuModel>> convertedContent = service.truck_menus(id);
        convertedContent.enqueue(new Callback<ArrayList<MenuModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MenuModel>> call, Response<ArrayList<MenuModel>> response) {
                ArrayList<MenuModel> menuList = response.body();

                Log.d("TAG", "바디: " + response.body().toString());
                Log.d("TAG", "메뉴리스트 사이즈 : " + menuitems.size());

                if (menuList.size() >= 5) {
                    for (int i = 0; i < 5; i++) {
                        menuitems.add(menuList.get(i));
                    }
                } else {
                    for (MenuModel menu : menuList) {
                        menuitems.add(menu);
                    }
                }
                MenuModel.MENU_INFO_LIST = menuitems; //싱글톤(메뉴)
                showMenuCardViewList(menuitems); //서버에서 받아오면 카드뷰 그려주게하기
            }

            @Override
            public void onFailure(Call<ArrayList<MenuModel>> call, Throwable t) {
                Log.d("실패", "onFailure: ");
                Log.d("TAG", t.getMessage());
            }
        });
    }
}
