package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model;


import com.google.gson.annotations.SerializedName;

public class MenuModel {

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