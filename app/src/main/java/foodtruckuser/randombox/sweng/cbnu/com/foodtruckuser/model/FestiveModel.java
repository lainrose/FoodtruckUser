package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model;

import android.view.View;

import java.util.ArrayList;

/**
 * Simple POJO model for example
 */
public class FestiveModel {

    private String year;
    private String pledgePrice;
    private String festive_title;
    private String place;
    private int requestsCount;
    private String start_date;
    private String end_date;
    private String festive_content_view;
    private int recruitment_truck;
    private int request_truck;
    private String food_category;
    private String deadline;

    private View.OnClickListener requestBtnClickListener;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPledgePrice() {
        return pledgePrice;
    }

    public void setPledgePrice(String pledgePrice) {
        this.pledgePrice = pledgePrice;
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

    public int getRequestsCount() {
        return requestsCount;
    }

    public void setRequestsCount(int requestsCount) {
        this.requestsCount = requestsCount;
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

    public String getFestive_content_view() {
        return festive_content_view;
    }

    public void setFestive_content_view(String festive_content_view) {
        this.festive_content_view = festive_content_view;
    }

    public int getRecruitment_truck() {
        return recruitment_truck;
    }

    public void setRecruitment_truck(int recruitment_truck) {
        this.recruitment_truck = recruitment_truck;
    }

    public int getRequest_truck() {
        return request_truck;
    }

    public void setRequest_truck(int request_truck) {
        this.request_truck = request_truck;
    }

    public String getFood_category() {
        return food_category;
    }

    public void setFood_category(String food_category) {
        this.food_category = food_category;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FestiveModel item = (FestiveModel) o;

        if (requestsCount != item.requestsCount) return false;
        if (year != null ? !year.equals(item.year) : item.year != null) return false;
        if (pledgePrice != null ? !pledgePrice.equals(item.pledgePrice) : item.pledgePrice != null)
            return false;
        if (festive_title != null ? !festive_title.equals(item.festive_title) : item.festive_title != null)
            return false;
        if (place != null ? !place.equals(item.place) : item.place != null)
            return false;
        if (start_date != null ? !start_date.equals(item.start_date) : item.start_date != null)
            return !(end_date != null ? !end_date.equals(item.end_date) : item.end_date != null);

        if (recruitment_truck != item.recruitment_truck) return false;
        if (request_truck != item.request_truck) return false;
        if (festive_content_view != null ? !festive_content_view.equals(item.festive_content_view) : item.festive_content_view != null)
            return false;
        if (food_category != null ? !food_category.equals(item.food_category) : item.food_category != null)
            return false;
        if (deadline != null ? !deadline.equals(item.deadline) : item.deadline != null)
            return false;


        return false;
    }

    @Override
    public int hashCode() {
        int result = year != null ? year.hashCode() : 0;
        result = 31 * result + (pledgePrice != null ? pledgePrice.hashCode() : 0);
        result = 31 * result + (festive_title != null ? festive_title.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + requestsCount;
        result = 31 * result + (start_date != null ? start_date.hashCode() : 0);
        result = 31 * result + (end_date != null ? end_date.hashCode() : 0);
        result = 31 * result + recruitment_truck;
        result = 31 * result + request_truck;
        result = 31 * result + (festive_content_view != null ? festive_content_view.hashCode() : 0);
        result = 31 * result + (food_category != null ? food_category.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);

        return result;
    }


}