package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.mypage;

    public class User {

        private static User user = null;
        private static String USER_NAME;
        private static String USER_ID;
        private static String USER_PASSWORD;
        private static double USER_X;
        private static double USER_Y;
        private static String FIRST_FACEBOOK_LOGIN;
        private static String FACEBOOK_LOGIN;

        public User()
        {

        }

        public static synchronized User getInstance()
        {
            if(user == null){}
            try{
                if(user==null)
                    user = new User();
                return user;
            }
            finally {}
        }

        public static String getFacebookLogin()
        {
            return FACEBOOK_LOGIN;
        }

        public static void setFacebookLogin(String facebookLogin)
        {
            FACEBOOK_LOGIN = facebookLogin;
        }

        public static String getUserName()
        {
            return USER_NAME;
        }

        public static void setUserName(String userName)
        {
            USER_NAME = userName;
        }

        public static String getUserId()
        {
            return USER_ID;
        }

        public static void setUserId(String userId)
        {
            USER_ID = userId;
        }

        public static String getUserPassword()
        {
            return USER_PASSWORD;
        }

        public static void setUserPassword(String userPassword)
        {
            USER_PASSWORD = userPassword;
        }

        public static double getUserX()
        {
            return USER_X;
        }

        public static void setUserX(double userX)
        {
            USER_X = userX;
        }

        public static double getUserY()
        {
            return USER_Y;
        }

        public static void setUserY(double userY)
        {
            USER_Y = userY;
        }

        public static String getFirstFacebookLogin()
        {
            return FIRST_FACEBOOK_LOGIN;
        }

        public static void setFirstFacebookLogin(String firstFacebookLogin)
        {
            FIRST_FACEBOOK_LOGIN = firstFacebookLogin;
        }
    }
