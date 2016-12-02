package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.NaviagtionMain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.Utill;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.FestiveAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FestiveModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;

public class FragmentFestive extends Fragment {

    private ArrayList<FestiveModel> festiveItems = new ArrayList<>();
    private FestiveAdapter festiveAdapter;
    private ListView festiveListView;
    private FloatingActionButton festiveWrite;

    private String YEAR[] = {"2016", "2016", "2015", "2015", "2014"};
    private String PLEDGE_PRICE[] = {"","","","",""};
    private String FESTIVE_TITLE[] = {"서움 밤도깨비 야시장", "동대문 야시장", "푸드트럭 페스티벌", "제 1회 수성못 축제", "용산 푸드"};
    private String PLACE[] = {"서울 한강공원 일대", "동대문 플라자", "수원시 시청부근", "수성못 일대", "용산 아이코마 앞"};
    private int REQUEST_COUNT[] = {1,2,3,4,5};
    private String START_DATE[] = {"12월 20일", "11월 10일", "8월 19일", "4월 15일", "2월 3일"};
    private String END_DATE[] = {"12월 24일", "11월 13일", "8월 21일", "4월 18일", "2월 7일"};
    private String CONTENT_VIEW[] = {"청년 트럭", "스테이크 하우스", "아메리칸 셰프", "미니벤치", "Healing Cafe"};
    private int RECRUIT_TRUCK[] = {4,5,6,7,8};
    private int REQUEST_TRUCK[] = {1,3,5,6,8,9};
    private String CATEGORY[] = {"가능","불가능","가능","불가능","가능"};
    private String DEADLINE[] = {"12월 2일","12월 3일","12월 4일","12월 5일","12월 6일"};


    private int FT_CATEGORY[] = {1, 2, 3, 3, 5};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_festive, null);

        festiveListView = (ListView) view.findViewById(R.id.mainListView);
        festiveAdapter = new FestiveAdapter(getContext(), festiveItems);
        festiveListView.setAdapter(festiveAdapter);
        festiveListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ((FoldingCell) view).toggle(false);
                festiveAdapter.registerToggle(pos);
            }
        });
        festiveWrite = (FloatingActionButton)view.findViewById(R.id.festive_write);
        festiveWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utill.getInstance().MoveAcitivity(getContext(), AcitivityFestiveWriting.class);
            }
        });
        return view;

    }

    private void initFestive() {
        festiveItems.clear();
        for (int i = 0; i < 5; i++) {
            FestiveModel item = new FestiveModel();
            item.setYear(YEAR[i]);
            item.setPledgePrice(PLEDGE_PRICE[i]);
            item.setFestive_title(FESTIVE_TITLE[i]);
            item.setPlace(PLACE[i]);
            item.setRequestsCount(REQUEST_COUNT[i]);
            item.setStart_date(START_DATE[i]);
            item.setEnd_date(END_DATE[i]);
            item.setFestive_content_view(CONTENT_VIEW[i]);
            item.setRecruitment_truck(RECRUIT_TRUCK[i]);
            item.setRequest_truck(REQUEST_TRUCK[i]);
            item.setFood_category(CATEGORY[i]);
            item.setDeadline(DEADLINE[i]);
            festiveItems.add(item);
        }
    }
}
