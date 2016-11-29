package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MenuModel {
    public static ArrayList<MenuModel> MENU_INFO_LIST;
    // Getter and Setter model for recycler view items
    @SerializedName("name")
    private String title;
    @SerializedName("price")
    private int price;
    @SerializedName("image")
    private MenuUrlModel image;

    public MenuModel(String title,int price, MenuUrlModel image) {
        this.title = title;
        this.price = price;
        this.image = image;
    }


    public static synchronized ArrayList<MenuModel> getInstanceList() { //싱글톤
        if (MENU_INFO_LIST == null) {
            MENU_INFO_LIST  = new ArrayList<MenuModel>();
        }
        return MENU_INFO_LIST;
    }

    public int getPrice(){return price;}
    public String getTitle() {return title;}
    public MenuUrlModel getImage() {
        return image;
    }

    public class MenuUrlModel {
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
    }
}