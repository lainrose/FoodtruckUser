package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public final class PrefHelper {

  private  final String PREF_NAME = "PrefName";

  private static PrefHelper mInstance = null;
  private SharedPreferences mSharedPreference;

  private static final String PREF_FACEBOOK_LOGIN = "facebook_login";   //페이스북 로그인
  private static final String PREF_USER_ID = "user_id"; //유저 아이디
  private static final String PREF_USER_PASSWORD = "user_password" ; //유저 패스워드
  private static final String PREF_USER_NAME = "user_name";    //유저 y좌표
  private static final String PREF_USER_X= "x";    //유저 x좌표
  private static final String PREF_USER_Y = "y";    //유저 y좌표
  private static final String PREF_LIKED_TRUCK_ID = "liked_truck_id"; //좋아요 누른 푸드트럭
  private  final String PREF_EMAIL_AUTO_LOGIN = "emailAutoLogin";
  private final String PREF_DISTANCE = "distance";


  private PrefHelper(Context context)
  {
    this.mSharedPreference= PreferenceManager.getDefaultSharedPreferences(context);

    //this.mSharedPreference= context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
  }

  public static synchronized PrefHelper getInstance(Context context)
  {
    if (mInstance == null) {}
    try{
      if (mInstance == null)
        mInstance = new PrefHelper(context);
      return mInstance;
    }
    finally {}
  }


  public static String getPrefLikedTruckId() {
    return PREF_LIKED_TRUCK_ID;
  }

  public String getPrefFacebookLogin() {
     return getString(PREF_FACEBOOK_LOGIN,"LOGOUT");
  }
  public void setPrefFacebookLogin(String paramString) {
    setString(PREF_FACEBOOK_LOGIN, paramString);
  }

  public Boolean getPrefEmailAutoLogin() {
      return getBoolean(getPREF_EMAIL_AUTO_LOGIN(), false);
    }
  public void setPrefEmailAutoLogin(Boolean paramBoolean) {
    setBoolean(getPREF_EMAIL_AUTO_LOGIN(), paramBoolean);
  }


  public int getPrefDistance() {
    return getInt(PREF_DISTANCE);
  }
  public void setPrefDistance(int paramInt) {
    setInt(PREF_DISTANCE, paramInt);
  }

  public String getPrefUserId() {
    return getString(PREF_USER_ID, "");
  }
  public void setPrefUserId(String paramString) {
    setString(PREF_USER_ID, paramString);
  }

  public String getPrefUserName() {
    return getString(PREF_USER_NAME,"");
  }
  public void setPrefUserName(String paramString) {
    setString(PREF_USER_NAME, paramString);
  }

  public String getPrefUserPassword() {
    return getString(PREF_USER_PASSWORD,"");
  }
  public void setPrefUserPassword(String paramString) {
    setString(PREF_USER_PASSWORD, paramString);
  }

  public  String getPrefUserX() {
    return getString(PREF_USER_X,"");
  }
  public void setPrefUserX(String paramString) {
    setString(PREF_USER_X, paramString);
  }

  public  String getPrefUserY() {
    return getString(PREF_USER_Y,"");
  }
  public void setPrefUserY(String paramString) {
    setString(PREF_USER_Y, paramString);
  }

  public Set<String> getLikedTruckId() {return getStringSet(getPrefLikedTruckId());}
  public void setLikedTruckid(Set<String> paramStringSet) {setStringSet(getPrefLikedTruckId(), paramStringSet);}



  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    return this.mSharedPreference.getBoolean(paramString, paramBoolean);
  }

  public double getDouble(String paramString)
  {

    return Double.longBitsToDouble(this.mSharedPreference.getLong(paramString, Double.doubleToRawLongBits(-1.0D)));
  }

  public float getFloat(String paramString, float paramFloat)
  {
    return this.mSharedPreference.getFloat(paramString, paramFloat);
  }

  public int getInt(String paramString)
  {
    return this.mSharedPreference.getInt(paramString, -1);
  }

  public long getLong(String paramString, long paramLong)
  {
    try {
      paramLong =this.mSharedPreference.getLong(paramString, paramLong);
      return paramLong;
    }
    catch (ClassCastException e) {
      return 0L;
    }

  }

  public String getString(String paramString1, String paramString2)
  {
    return this.mSharedPreference.getString(paramString1, paramString2);
  }

  public Set<String> getStringSet(String paramString)
  {
    return this.mSharedPreference.getStringSet(paramString, new HashSet());
  }

  public boolean setBoolean(String paramString, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
    localEditor.putBoolean(paramString, paramBoolean);
    return localEditor.commit();
  }

  public boolean setDouble(String paramString, double paramDouble)
  {
    SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
    localEditor.putLong(paramString, Double.doubleToRawLongBits(paramDouble));
    return localEditor.commit();
  }

  public boolean setFloat(String paramString, float paramFloat)
  {
    SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
    localEditor.putFloat(paramString, paramFloat);
    return localEditor.commit();
  }

  public boolean setInt(String paramString, int paramInt)
  {
    SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
    localEditor.putInt(paramString, paramInt);
    return localEditor.commit();
  }

  public boolean setLong(String paramString, long paramLong)
  {
    SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
    localEditor.putLong(paramString, paramLong);
    return localEditor.commit();
  }

  public void setPreference(SharedPreferences paramSharedPreferences)
  {
    this.mSharedPreference = paramSharedPreferences;
  }

  public boolean setString(String paramString1, String paramString2)
  {
    SharedPreferences.Editor localEditor = mSharedPreference.edit();
    localEditor.putString(paramString1, paramString2);
    return localEditor.commit();
  }

  public boolean setStringSet(String paramString, Set<String> paramSet)
  {
    SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
    localEditor.putStringSet(paramString, paramSet);
    return localEditor.commit();
  }

  public String getPREF_EMAIL_AUTO_LOGIN() {
    return PREF_EMAIL_AUTO_LOGIN;
  }
}