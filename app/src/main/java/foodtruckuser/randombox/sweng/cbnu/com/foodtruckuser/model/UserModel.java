package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Model;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("id")
    private static String USER_ID;
    @SerializedName("email")
    private static String USER_MAIL;
    @SerializedName("memberShipGrade")
    private String memberShip_grade;
    @SerializedName("nickName")
    private static String USER_NAME;
    @SerializedName("positionX")
    private static double USER_X;
    @SerializedName("positionY")
    private static double USER_Y;
    @SerializedName("phone_number")
    private static String USER_PHONE;
    @SerializedName("password_digest")
    private static String USER_PASSWORD;
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

    public UserModel() {

    }

    public static synchronized UserModel getInstance() {
        if(getUser() == null){}
        try{
            if(getUser() ==null)
                setUser(new UserModel());
            return getUser();
        }
        finally {}
    }

    public static String getFacebookLogin()
        {
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

    public static String getUserMail() {
        return USER_MAIL;
    }

    public static void setUserMail(String userMail) {
        USER_MAIL = userMail;
    }

    public static String getUserId() {
        return USER_ID;
    }

    public static void setUserId(String userId) {
        USER_ID = userId;
    }

    public static String getUserName() {
        return USER_NAME;
    }

    public static void setUserName(String userName) {
        USER_NAME = userName;
    }

    public static double getUserX() {
        return USER_X;
    }

    public static void setUserX(double userX) {
        USER_X = userX;
    }

    public static double getUserY() {
        return USER_Y;
    }

    public static void setUserY(double userY) {
        USER_Y = userY;
    }

    public static String getUserPhone() {
        return USER_PHONE;
    }

    public static void setUserPhone(String userPhone) {
        USER_PHONE = userPhone;
    }

    public static String getUserPassword() {
        return USER_PASSWORD;
    }

    public static void setUserPassword(String userPassword) {
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
