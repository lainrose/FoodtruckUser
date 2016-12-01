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

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.Utill;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter.FoldingCellListAdapter;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FestiveModel;

public class FragmentFestive extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_festive, null);

        ListView theListView = (ListView) view.findViewById(R.id.mainListView);

        // prepare elements to display
        final ArrayList<FestiveModel> items = FestiveModel.getTestingList();

        // add custom btn handler to first list item
        //입점신청하기버튼 리스너
        items.get(0).setRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("정말 신청하시겠습니까?")
                        .setContentText("확인 버튼을 누르면 취소할 수 없습니다.")
                        //취소버튼 text
                        .setCancelText("취소")
                        //확인버튼 text
                        .setConfirmText("확인")
                        .showCancelButton(true)
                        // 확인버튼 리스너
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Utill.getInstance().MoveAcitivity(getContext(), AcitivityFestiveWriting.class);
                            }
                        })
                        //취소버튼 리스너
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                            }
                        }).show();
            }
        });

        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(getContext(), items);

        // add default btn handler for each request btn on each item if custom handler not found
        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // set elements to adapter
        theListView.setAdapter(adapter);

        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {


                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });

        return view;

    }
}
