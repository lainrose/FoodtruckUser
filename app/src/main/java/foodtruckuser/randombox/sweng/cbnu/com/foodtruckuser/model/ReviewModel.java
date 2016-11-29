package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model;


public class ReviewModel {

    // Getter and Setter model for recycler view items
    private int centerimage;
    private int bottomimage;
    private int userImage;
    private String userText;
    private int likesCount=0 ;
    private boolean isLiked=false;
    private String reviewText;

    public int getCenterimage() {
        return centerimage;
    }

    public void setCenterimage(int centerimage) {
        this.centerimage = centerimage;
    }

    public int getBottomimage() {
        return bottomimage;
    }

    public void setBottomimage(int bottomimage) {
        this.bottomimage = bottomimage;
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
