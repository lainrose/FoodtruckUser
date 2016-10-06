package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.mypage;

    public class User {
        private static User user = null;

        public static String USER_NAME;
        public static String USER_ID;
        public static String USER_PASSWORD;
        public static double X;
        public static double Y;
        public static String FIRSTFACEBOOKLOGIN;
        public static String FACEBOOKLOGIN;

        public User()
        {

        }

        public static String getUserName() {
            return USER_NAME;
        }

        public static void setUserName(String userName) {
            USER_NAME = userName;
        }

        public static String getUserId() {
            return USER_ID;
        }

        public static void setUserId(String userId) {
            USER_ID = userId;
        }

        public static String getUserPassword() {
            return USER_PASSWORD;
        }

        public static void setUserPassword(String userPassword) {
            USER_PASSWORD = userPassword;
        }

        public static double getX() {
            return X;
        }

        public static void setX(double x) {
            X = x;
        }

        public static double getY() {
            return Y;
        }

        public static void setY(double y) {
            Y = y;
        }

        public static String getFIRSTFACEBOOKLOGIN() {
            return FIRSTFACEBOOKLOGIN;
        }

        public static void setFIRSTFACEBOOKLOGIN(String FIRSTFACEBOOKLOGIN) {
            User.FIRSTFACEBOOKLOGIN = FIRSTFACEBOOKLOGIN;
        }

        public static String getFACEBOOKLOGIN() {
            return FACEBOOKLOGIN;
        }

        public static void setFACEBOOKLOGIN(String FACEBOOKLOGIN) {
            User.FACEBOOKLOGIN = FACEBOOKLOGIN;
        }

        public static synchronized User getInstance()
        {
            if(user==null){user = new User();}
            return user;

        }




}
