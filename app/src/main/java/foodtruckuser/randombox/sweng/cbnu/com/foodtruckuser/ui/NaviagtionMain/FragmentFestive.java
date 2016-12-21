package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.NaviagtionMain;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.ServiceGenerator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.Utill;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.FestiveAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FestiveModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FoodTruckModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.ApiService;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.GMailSender;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join.CertifiedActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_festive, null);

        festiveListView = (ListView) view.findViewById(R.id.mainListView);


        festiveWrite = (FloatingActionButton)view.findViewById(R.id.festive_write);
        festiveWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true){
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("행사를 이용하시려면\n이메일 인증을 해야됩니다.\n인증 하시겠습니까?")
                            //.setContentText("확인 버튼을 누르면 취소할 수 없습니다.")
                            .setCancelText("취소")
                            .setConfirmText("확인")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                    Utill.getInstance().MoveAcitivity(getContext(), CertifiedActivity.class);
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            }).show();
                }
                else{
                    Utill.getInstance().MoveAcitivity(getContext(), AcitivityFestiveWriting.class);
                }



            }
        });
        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<ArrayList<FestiveModel>> call = service.festival_info();
        call.enqueue(new Callback<ArrayList<FestiveModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FestiveModel>> call, Response<ArrayList<FestiveModel>> response) {
                festiveItems = response.body();

                if(festiveItems == null) {
                    return;
                }

                // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
                final FestiveAdapter adapter = new FestiveAdapter(getContext(), festiveItems);

                // add default btn handler for each request btn on each item if custom handler not found
                adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
                    }
                });

                // set elements to adapter
                festiveListView.setAdapter(adapter);

                // set on click event listener to list view
                festiveListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {


                        // toggle clicked cell state
                        ((FoldingCell) view).toggle(false);
                        // register in adapter that state for selected cell is toggled
                        adapter.registerToggle(pos);
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<FestiveModel>> call, Throwable t) {
                Log.d("FESTIVAL", t.toString());
            }
        });

        return view;

    }

    private boolean check_email(String paramString) {
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(paramString).matches();
    }
}
