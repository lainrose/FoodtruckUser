package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model;

import android.view.View;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Simple POJO model for example
 */
public class FestiveModel {

    private String year;

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String festive_title;
    @SerializedName("place")
    private String place;
    @SerializedName("start_date")
    private String start_date;
    @SerializedName("end_date")
    private String end_date;

    @SerializedName("limit_num_of_application")
    private String recruitment_truck;
    private String title_cost;
    @SerializedName("applicant_end")
    private String deadline;

    @SerializedName("condition")
    private String festive_content_view;
    @SerializedName("applicant_num")
    private int request_truck;
    @SerializedName("support_type")
    private String support_elec;
    @SerializedName("image")
    private Image image;

    private View.OnClickListener requestBtnClickListener;

    public FestiveModel(String year,String festive_title, String place,  String end_date ,String start_date ,String festive_content_view,String recruitment_truck,int request_truck,String food_category,String deadline,String title_cost) {
        this.year = year;
        this.festive_title = festive_title;
        this.place = place;
        this.start_date = start_date;
        this.end_date = end_date;

        this.setRecruitment_truck(recruitment_truck);
        this.title_cost = title_cost;
        this.deadline= deadline;

        this.festive_content_view = festive_content_view;
        this.request_truck = request_truck;
        this.setSupport_elec(food_category);

    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getImage() { return image.getUrl(); }

    public void setImage(Image image) { this.image = image; }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFestive_title() {
        return festive_title;
    }

    public void setFestive_title(String festive_title) {
        this.festive_title = festive_title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }


    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }


    public String getFestive_content_view() { return festive_content_view;}

    public void setFestive_content_view(String festive_content_view) {this.festive_content_view = festive_content_view;}

    public int getRequest_truck() {return request_truck;}

    public void setRequest_truck(int request_truck) {this.request_truck = request_truck;}

    public String getDeadline() {return deadline;}

    public void setDeadline(String deadline) {this.deadline = deadline;}

    public View.OnClickListener akgetRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public String getTitle_cost() {return title_cost;}

    public void setTitle_cost(String title_cost) {this.title_cost = title_cost;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FestiveModel item = (FestiveModel) o;

        if (year != null ? !year.equals(item.year) : item.year != null) return false;
        if (festive_title != null ? !festive_title.equals(item.festive_title) : item.festive_title != null)
            return false;
        if (place != null ? !place.equals(item.place) : item.place != null)
            return false;
        if (start_date != null ? !start_date.equals(item.start_date) : item.start_date != null)
            return !(end_date != null ? !end_date.equals(item.end_date) : item.end_date != null);

        if (getRecruitment_truck() != item.getRecruitment_truck()) return false;
        if (request_truck != item.request_truck) return false;
        if (festive_content_view != null ? !festive_content_view.equals(item.festive_content_view) : item.festive_content_view != null)
            return false;
        if (deadline != null ? !deadline.equals(item.deadline) : item.deadline != null)
            return false;


        return  false;
    }

    public String getRecruitment_truck() {
        return recruitment_truck;
    }

    public void setRecruitment_truck(String recruitment_truck) {
        this.recruitment_truck = recruitment_truck;
    }

    public String getSupport_elec() {
        return support_elec;
    }

    public void setSupport_elec(String support_elec) {
        this.support_elec = support_elec;
    }

    class Image {
        String url;

        public Image(String url) { this.url = url; }
        public void setUrl(String url) { this.url = url; }
        public String getUrl() { return url; }
    }
}