package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;
import java.util.HashSet;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.ServiceGenerator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.FestiveModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class FestiveAdapter extends ArrayAdapter<FestiveModel> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    private Context context;
    final int REQ_CODE_SELECT_IMAGE=100;

    public FestiveAdapter(Context context, List<FestiveModel> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // get item for selected view
        final FestiveModel item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.year = (TextView) cell.findViewById(R.id.title_date_year);
            viewHolder.end_date = (TextView) cell.findViewById(R.id.title_end_date);
            viewHolder.start_date = (TextView) cell.findViewById(R.id.title_start_date);
            viewHolder.festive = (TextView) cell.findViewById(R.id.title_festive);
            viewHolder.place = (TextView) cell.findViewById(R.id.title_place);
            viewHolder.contentRequestBtn = (TextView) cell.findViewById(R.id.request_btn);

            viewHolder.title_recruitment_truck= (TextView)cell.findViewById(R.id.title_recruitment_truck);
            viewHolder.title_cost = (TextView) cell.findViewById(R.id.title_cost);
            viewHolder.title_deadline = (TextView) cell.findViewById(R.id.title_deadline);

            viewHolder.festive_content_view = (TextView) cell.findViewById(R.id.festive_content_view);
            viewHolder.recruitment_truck = (TextView) cell.findViewById(R.id.recruitment_truck);
            viewHolder.request_truck = (TextView) cell.findViewById(R.id.request_truck);
            viewHolder.food_category = (TextView) cell.findViewById(R.id.food_category);
            viewHolder.deadline = (TextView) cell.findViewById(R.id.deadline);
            viewHolder.festival_image = (ImageView) cell.findViewById(R.id.festive_image);
            Picasso.with(context).load(ServiceGenerator.API_BASE_URL + item.getImage()).into(viewHolder.festival_image);

            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        // bind data from selected element to view through view holder
        viewHolder.year.setText(item.getYear());
        viewHolder.end_date.setText(item.getStart_date());
        viewHolder.start_date.setText(item.getEnd_date());
        viewHolder.festive.setText(item.getFestive_title());
        viewHolder.place.setText(item.getPlace());

        viewHolder.title_recruitment_truck.setText(item.getRecruitment_truck());
        viewHolder.title_cost.setText(item.getTitle_cost());
        viewHolder.title_deadline.setText(item.getDeadline());

        viewHolder.festive_content_view.setText(item.getFestive_content_view());
        viewHolder.recruitment_truck.setText(String.valueOf(item.getRecruitment_truck()));
        viewHolder.request_truck.setText(String.valueOf(item.getRequest_truck()));
        viewHolder.food_category.setText(item.getSupport_elec());
        viewHolder.deadline.setText(item.getDeadline());

        viewHolder.contentRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","떠라! ");
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)

                        .setTitleText("정말 신청하시겠습니까?")
                        .setContentText("확인 버튼을 누르면 취소할 수 없습니다.")
                        //취소버튼 text
                        .setCancelText("취소")
                        //확인버튼 text
                        .setConfirmText("확인")
                        .showCancelButton(true)
                        //취소버튼 리스너
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {

                            @Override

                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("삭제되었습니다.")
                                        .setConfirmText("확인")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                            }

                        })
                        // 확인버튼 리스너
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                            @Override

                            public void onClick(final SweetAlertDialog sDialog) {
//                                ApiService service = ServiceGenerator.createService(ApiService.class);
//                                Call<Boolean> call = service.request_festival(Owner.getInstance().getId(), item.getId());
//                                call.enqueue(new Callback<Boolean>() {
//                                    @Override
//                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                                        Boolean actionCheck = response.body();
//                                        if(actionCheck) {
//                                            Toast.makeText(getContext(), "입점 신청 완료", Toast.LENGTH_SHORT).show();
//                                            sDialog.dismiss();
//                                        }
//                                        else {
//                                            Toast.makeText(getContext(), "입점 신청 실패", Toast.LENGTH_SHORT).show();
//                                            sDialog.dismiss();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<Boolean> call, Throwable t) {
//                                        Log.d("FOLING", t.toString());
//                                    }
//                                });
                            }

                        })

                        .show();
            }
        });
/*
     dn
*/
        // set custom btn handler for list item from that item
        if (item.akgetRequestBtnClickListener() != null) {
            //viewHolder.contentRequestBtn.setOnClickListener(item.akgetRequestBtnClickListener());
            Log.d("클릭", "null아님");
        } else {
            // (optionally) add "default" handler if no handler found in item
            //viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
            Log.d("클릭", "null아야");

        }


        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;

    }


    // View lookup cache
    private static class ViewHolder {
        TextView year;
        TextView contentRequestBtn;
        TextView festive;
        TextView place;
        TextView start_date;
        TextView end_date;
        TextView title_recruitment_truck;
        TextView title_cost;
        TextView title_deadline;
        TextView festive_content_view;
        TextView recruitment_truck;
        TextView request_truck;
        TextView food_category;
        TextView deadline;
        ImageView festival_image;

    }
}