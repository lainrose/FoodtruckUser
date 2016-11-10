package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model;

/**
 * Created by SONU on 25/09/15.
 */
public class MenuModel {

    // Getter and Setter model for recycler view items
    private String title;
    private int image;

    public MenuModel(String title, int image) {

        this.title = title;

        this.image = image;
    }

    public String getTitle() {
        return title;
    }



    public int getImage() {
        return image;
    }
}
