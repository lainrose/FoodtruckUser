package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    //싱글톤
    public volatile static UserModel USER_INFO;
    private UserModel() { }

    public static UserModel getInstance(){
        if(USER_INFO == null){ //있는지 체크 없으면
            USER_INFO = new UserModel(); //생성한뒤
        }
        return USER_INFO; //성성자를 넘긴다.
    }

    @SerializedName("id")
    private String USER_ID;
    @SerializedName("email")
    private String USER_MAIL;
    @SerializedName("memberShipGrade")
    private String memberShip_grade;
    @SerializedName("nickName")
    private String USER_NAME;
    @SerializedName("positionX")
    private double USER_X;
    @SerializedName("positionY")
    private double USER_Y;
    @SerializedName("phone_number")
    private String USER_PHONE;
    @SerializedName("password")
    private String USER_PASSWORD;
    @SerializedName("updated_at")
    private String updated_at = "";
    @SerializedName("created_at")
    private String created_at = "";

    private static UserModel user = null;
    private static String FIRST_FACEBOOK_LOGIN;
    private static String FACEBOOK_LOGIN;

    public UserModel(String id, String email, String memberShip_grade, String nickName, double positionX, double positionY, String phone_number, String password_digest, String updated_at, String created_at) {
        this.setUserId(id);
        this.setUserMail(email);
        this.setMemberShip_grade(memberShip_grade);
        this.setUserName(nickName);
        this.setUserX(positionX);
        this.setUserY(positionY);
        this.setUserPhone(phone_number);
        this.setUserPassword(password_digest);
        this.setUpdated_at(updated_at);
        this.setCreated_at(created_at);
    }

    public static String getFacebookLogin() {
        return FACEBOOK_LOGIN;
    }

    public static void setFacebookLogin(String facebookLogin) {
        FACEBOOK_LOGIN = facebookLogin;
    }

    public static String getFirstFacebookLogin() {
        return FIRST_FACEBOOK_LOGIN;
    }

    public static void setFirstFacebookLogin(String firstFacebookLogin) {
        FIRST_FACEBOOK_LOGIN = firstFacebookLogin;
    }

    public  String getUserMail() {
        return USER_MAIL;
    }

    public  void setUserMail(String userMail) {
        USER_MAIL = userMail;
    }

    public String getUserId() {
        return USER_ID;
    }

    public  void setUserId(String userId) {
        USER_ID = userId;
    }

    public  String getUserName() {
        return USER_NAME;
    }

    public  void setUserName(String userName) {
        USER_NAME = userName;
    }

    public  double getUserX() {
        return USER_X;
    }

    public  void setUserX(double userX) {
        USER_X = userX;
    }

    public  double getUserY() {
        return USER_Y;
    }

    public  void setUserY(double userY) {
        USER_Y = userY;
    }

    public  String getUserPhone() {
        return USER_PHONE;
    }

    public  void setUserPhone(String userPhone) {
        USER_PHONE = userPhone;
    }

    public  String getUserPassword() {
        return USER_PASSWORD;
    }

    public  void setUserPassword(String userPassword) {
        USER_PASSWORD = userPassword;
    }

    public static UserModel getUser() {
        return user;
    }

    public static void setUser(UserModel user) {
        UserModel.user = user;
    }

    public String getMemberShip_grade() {
        return memberShip_grade;
    }

    public void setMemberShip_grade(String memberShip_grade) {
        this.memberShip_grade = memberShip_grade;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
