package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.mypage;

    public class FoodTruck {

        private static FoodTruck ft = null;
        private static String FT_ID;
        private static String FT_NAME;
        private static String FT_CATEGORY;
        private static String FT_TAG;
        private static String FT_AVERAGE;
        private static Boolean FT_LIKE = false;
        private static String FT_START;
        private static String FT_PAYMENT;
        private static int FT_IMAGE;
        private static String FT_X;
        private static String FT_Y;


        public static String getFtId() {
            return FT_ID;
        }

        public static void setFtId(String ftId) {
            FT_ID = ftId;
        }

        public static String getFtName() {
            return FT_NAME;
        }

        public static void setFtName(String ftName) {
            FT_NAME = ftName;
        }

        public static String getFtCategory() {
            return FT_CATEGORY;
        }

        public static void setFtCategory(String ftCategory) {
            FT_CATEGORY = ftCategory;
        }

        public static String getFtTaag() {
            return FT_TAG;
        }

        public static void setFtTaag(String ftTaag) {
            FT_TAG = ftTaag;
        }

        public static String getFtAverage() {
            return FT_AVERAGE;
        }

        public static void setFtAverage(String ftAverage) {
            FT_AVERAGE = ftAverage;
        }

        public static Boolean getFtLike() {
            return FT_LIKE;
        }

        public static void setFtLike(Boolean ftLike) {
            FT_LIKE = ftLike;
        }

        public static String getFtStart() {
            return FT_START;
        }

        public static void setFtStart(String ftStart) {
            FT_START = ftStart;
        }

        public static String getFtPayment() {
            return FT_PAYMENT;
        }

        public static void setFtPayment(String ftPayment) {
            FT_PAYMENT = ftPayment;
        }

        public static int getFtImage() {
            return FT_IMAGE;
        }

        public static void setFtImage(int ftImage) {
            FT_IMAGE = ftImage;
        }

        public static String getFtX() {
            return FT_X;
        }

        public static void setFtX(String ftX) {
            FT_X = ftX;
        }

        public static String getFtY() {
            return FT_Y;
        }

        public static void setFtY(String ftY) {
            FT_Y = ftY;
        }
    }
