package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Eases.EaseType;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.OrderType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;
import com.sackcentury.shinebuttonlib.ShineButton;
import java.util.ArrayList;
import java.util.Random;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.mypage.FoodTruck;

public class FragmentHome extends Fragment {

    //플로팅아이콘 처리 변수
    private boolean init = false;
    private BoomMenuButton boomMenuButton;
    private RecyclerView MyRecyclerView;
    private ViewTreeObserver viewTreeObserver;

    public MyAdapter myAdapter;
    ArrayList<FoodTruckModel> listitems = new ArrayList<>();

    String FT_NAME[] = {"도혀니하우스","영비니하우스",
            "횬종이 하우스","으버미하우스","횬표하우스"};

    private int FT_CATEGORY[] = {1, 2, 3, 3, 5};
    public String FT_PAYMENT[] = {"카드", "현금", "카드/현금", "현금", "카드"};
    public int FT_IMAGES[] = {R.drawable.truck1,R.drawable.truck2,R.drawable.truck3,
            R.drawable.truck4,R.drawable.truck5};



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initFT();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        boomMenuButton = (BoomMenuButton)view.findViewById(R.id.boom);
        initBoom();
        viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
            @Override
            public void onWindowFocusChanged(final boolean hasFocus) {
                if (init) return;
                init = true;

                initBoom();
            }
        });
        MyRecyclerView = (RecyclerView)view.findViewById(R.id.cardView);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAdapter = new MyAdapter(listitems);
        if (listitems.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(myAdapter);
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private ArrayList<FoodTruckModel> list;
        public MyAdapter(ArrayList<FoodTruckModel> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public ImageView coverImageView;
        public ImageView shareImageView;
        public ShineButton shineButton;
        public TextView payTextView;

        public MyViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            coverImageView = (ImageView) v.findViewById(R.id.coverImageView);
            shareImageView = (ImageView) v.findViewById(R.id.shareImageView);
            shineButton = (ShineButton) v.findViewById(R.id.po_image);
            payTextView = (TextView) v.findViewById(R.id.paytextView);



            if (shineButton != null)
                shineButton.init(getActivity());
            // 좋아요 버튼 처리 부분
            shineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), titleTextView.getText() + "푸드트럭을 좋아요 누름", Toast.LENGTH_SHORT).show();
                }
            });

                //공유 버튼 처리 부분
                shareImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                                "://" + getResources().getResourcePackageName(coverImageView.getId())
                                + '/' + "drawable" + '/' + getResources().getResourceEntryName((int)coverImageView.getTag()));
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
                        shareIntent.setType("image/jpeg");
                        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
                    }
            });
        }
    }
    // 카드뷰에 들어갈 목록 초기화 부분
    public void initFT() {
        listitems.clear();
        for(int i =0;i<5;i++){
            FoodTruckModel item = new FoodTruckModel();
            item.setFtName(FT_NAME[i]);
            item.setFtImage(FT_IMAGES[i]);
            item.setFtCategory(FT_CATEGORY[i]);
            item.setFtPayment(FT_PAYMENT[i]);
            listitems.add(item);
        }
    }

    //플로팅 아이콘 처리 함수들
    private void initBoom() {
        int number  = 8;
        ///int number = buttonNumberSeek.getProgress() + 3;

        Drawable[] drawables = new Drawable[number];
        int[] drawablesResource = new int[]{
                R.drawable.mark,
                R.drawable.refresh,
                R.drawable.copy,
                R.drawable.heart,
                R.drawable.info,
                R.drawable.like,
                R.drawable.record,
                R.drawable.search,
                R.drawable.settings
        };
        for (int i = 0; i < number; i++)
            drawables[i] = ContextCompat.getDrawable(getContext(), drawablesResource[i]);

        String[] STRINGS = new String[]{
                "전체",
                "한식",
                "일식",
                "양식",
                "중식",
                "분식",
                "디저트",
                "음료",
                "Settings"
        };
        String[] strings = new String[number];
        for (int i = 0; i < number; i++)
            strings[i] = STRINGS[i];

        int[][] colors = new int[number][2];
        for (int i = 0; i < number; i++) {
            colors[i][1] = getRandomColor();
            colors[i][0] = Util.getInstance().getPressedColor(colors[i][1]);
        }

        // Now with Builder, you can init BMB more convenient
        new BoomMenuButton.Builder()
                .subButtons(drawables, colors, strings)
                .button(ButtonType.CIRCLE)
                .boom(BoomType.PARABOLA)
                .place(PlaceType.CIRCLE_8_3)
                .boomButtonShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                .subButtonsShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                .shareStyle(3f, getRandomColor(), getRandomColor())
                .showOrder(OrderType.RANDOM)
                .hideOrder(OrderType.RANDOM)
                .duration(800)
                .showMoveEase(EaseType.EaseOutBack)
                .hideMoveEase(EaseType.EaseInOutCubic)
                .init(boomMenuButton);

                 boomMenuButton.setOnSubButtonClickListener(new BoomMenuButton.OnSubButtonClickListener() {
                     @Override
                     public void onClick(int buttonIndex){
                         ArrayList<FoodTruckModel> copy_listitems = (ArrayList<FoodTruckModel>)listitems.clone();
                         int size = listitems.size();
                         Log.d("삭제전 리스트 사이즈", Integer.toString(copy_listitems.size()));
                         for(int i=0; i<size; i++){
                             for(int j=0; j<copy_listitems.size(); j++){
                                 Log.d("이거슨 검사할 리스트 번호", j+ copy_listitems.get(j).getFtName() + " " +copy_listitems.get(j).getFtCategory() + " = " + buttonIndex);

                                 if(buttonIndex == 0){
                                     //
                                 }
                                 else if(copy_listitems.get(j).getFtCategory() != buttonIndex){
                                     Log.d("이거슨 삭제될 리스트 번호", copy_listitems.get(j).getFtCategory() + " = " + buttonIndex);
                                     copy_listitems.remove(copy_listitems.get(j));
                                     Log.d("삭제후 리스트 사이즈", Integer.toString(copy_listitems.size()));
                                 }
                             }
                             myAdapter = new MyAdapter(copy_listitems);
                             if (copy_listitems.size() > 0 & MyRecyclerView != null) {
                                 MyRecyclerView.setAdapter(myAdapter);
                             }
                         }

                     }
                 });
    }
    private String[] Colors = {
            "#F44336", "#E91E63", "#9C27B0", "#2196F3", "#03A9F4", "#00BCD4", "#009688", "#4CAF50",
            "#8BC34A", "#CDDC39", "#FFEB3B", "#FFC107", "#FF9800", "#FF5722", "#795548", "#9E9E9E", "#607D8B"
    };

    public int getRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        return Color.parseColor(Colors[p]);
    }
}
