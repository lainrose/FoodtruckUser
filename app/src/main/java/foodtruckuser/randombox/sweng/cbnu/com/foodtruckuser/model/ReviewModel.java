package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hyunjung on 2016-11-01.
 */

public class ReviewModel {
    public static ArrayList<ReviewModel> REVIEW_LIST;

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("rating")
    private float rating;
    @SerializedName("image")
    private Image image;
    @SerializedName("client")
    private Client client;

    public static synchronized ArrayList<ReviewModel> getInstanceList() {
        if (REVIEW_LIST == null) {
            REVIEW_LIST  = new ArrayList<ReviewModel>();
        }
        return REVIEW_LIST;
    }

    public JsonObject parseToJsonObject(String client_id, String foodtruck_id, String title, String content, float rating/*, Image image*/) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("client_id", client_id);
        jsonObject.addProperty("foodtruck_id", foodtruck_id);
        jsonObject.addProperty("title", title);
        jsonObject.addProperty("content", content);
        jsonObject.addProperty("rating", rating);
        //jsonObject.addProperty("image", image);
        return jsonObject;
    }


    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setRating(float rating) { this.rating = rating; }
    public void setImage(Image image) { this.image = image; }
    public void setClient(Client client) { this.client = client; }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public float getRating() { return rating; }
    public String getImage() { return image.getUrl(); }
    public Client getClient() { return client; }

    public String getClientNickname() { return (getClient().getNickName()); }
    public String getClientImage() { return (getClient().getImage().getUrl()); }

    private class Client {
        @SerializedName("nickName")
        private String nickName;
        @SerializedName("image")
        private Image image;

        public Client(String nickName, Image image) {
            this.nickName = nickName;
            this.image = image;
        }

        public void setNickName(String nickName) { this.nickName = nickName; }
        public void setImage(Image image) { this.image = image; }

        public String getNickName() { return nickName; }
        public Image getImage() { return image; }
    }

}

class Image{
    @SerializedName("url")
    private String url;

    public Image(String url){
        this.url = url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}

