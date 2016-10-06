package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public final class PrefHelper extends AbstractPrefWrpper{


  private static final String PREF_NAME = "PrefName";
  private static PrefHelper mInstance = null;
  private SharedPreferences mSharedPreference;

  public static final String PREF_FIRSTFACEBOOKLOGIN = "first_facebook_login";    //처음 로그인
  public static final String PREF_FACEBOOKLOGIN = "facebook_login";   //페이스북 로그인
  public static final String PREF_USERID = "user_id"; //유저 아이디
  public static final String PREF_USERNAME = "user_name";    //유저 y좌표
  public static final String PREF_USERX= "x";    //유저 x좌표
  public static final String PREF_USERY = "y";    //유저 y좌표

  private PrefHelper(Context context)
  {
    super.setPreference(this.mSharedPreference);
    this.mSharedPreference= context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
  }

  public static PrefHelper getInstance(Context context)
  {
    if (mInstance == null) {}
    try{
      if (mInstance == null)
        mInstance = new PrefHelper(context);
      return mInstance;
    }
    finally {}
  }


  public void setFirstFacebookLogin(String paramString)
  {
    setString("first_facebook_login", paramString);
  }

  public void setUserEmail(String paramString)
  {
    setString("user_email", paramString);
  }

  public void setUserId(String paramString)
  {
    setString("user_id", paramString);
  }

  public void setUserName(String paramString)
  {
    setString("user_name", paramString);
  }

  public void setUserNickname(String paramString)
  {
    setString("user_nickname", paramString);
  }

  public void setUserPassword(String paramString)
  {
    setString("user_password", paramString);
  }

  public void setUserPhone(String paramString)
  {
    setString("user_phone", paramString);
  }


  public String getUserEmail()
  {
    return getString("user_email", "");
  }
  public int getUserId()
  {
    return getInt("user_id");
  }
  public String getUserName()
  {
    return getString("user_name", "");
  }

  public String getUserNickname()
  {
    return getString("user_nickname", "");
  }

  public String getUserPassword()
  {
    return getString("user_password", "");
  }

  public String getUserPhone()
  {
    return getString("user_phone", "");
  }
  public String getPrefFirstfacebooklogin() {
    return getString(PREF_FIRSTFACEBOOKLOGIN, "LOGOUT");
  }

  public String getPrefFacebooklogin() {
    return getString(PREF_FACEBOOKLOGIN, "");
  }



  protected boolean getBoolean(String paramString, boolean paramBoolean)
  {
    return this.mSharedPreference.getBoolean(paramString, paramBoolean);
  }

  protected double getDouble(String paramString)
  {

    return Double.longBitsToDouble(this.mSharedPreference.getLong(paramString, Double.doubleToRawLongBits(-1.0D)));
  }

  protected float getFloat(String paramString, float paramFloat)
  {
    return this.mSharedPreference.getFloat(paramString, paramFloat);
  }

  protected int getInt(String paramString)
  {
    return this.mSharedPreference.getInt(paramString, -1);
  }

  protected long getLong(String paramString, long paramLong)
  {
    try
    {
      paramLong =this.mSharedPreference.getLong(paramString, paramLong);
      return paramLong;
    }
    catch (ClassCastException e)
    {
      return 0L;
    }

  }

  protected String getString(String paramString1, String paramString2)
  {
    return this.mSharedPreference.getString(paramString1, paramString2);
  }

  protected Set<String> getStringSet(String paramString)
  {
    return this.mSharedPreference.getStringSet(paramString, new HashSet());
  }

  protected boolean setBoolean(String paramString, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
    localEditor.putBoolean(paramString, paramBoolean);
    return localEditor.commit();
  }

  protected boolean setDouble(String paramString, double paramDouble)
  {
    SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
    localEditor.putLong(paramString, Double.doubleToRawLongBits(paramDouble));
    return localEditor.commit();
  }

  protected boolean setFloat(String paramString, float paramFloat)
  {
    SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
    localEditor.putFloat(paramString, paramFloat);
    return localEditor.commit();
  }

  protected boolean setInt(String paramString, int paramInt)
  {
    SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
    localEditor.putInt(paramString, paramInt);
    return localEditor.commit();
  }

  protected boolean setLong(String paramString, long paramLong)
  {
    SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
    localEditor.putLong(paramString, paramLong);
    return localEditor.commit();
  }

  public void setPreference(SharedPreferences paramSharedPreferences)
  {
    this.mSharedPreference = paramSharedPreferences;
  }

  protected boolean setString(String paramString1, String paramString2)
  {
    SharedPreferences.Editor localEditor = mSharedPreference.edit();
    localEditor.putString(paramString1, paramString2);
    return localEditor.commit();
  }

  protected boolean setStringSet(String paramString, Set<String> paramSet)
  {
    SharedPreferences.Editor localEditor = this.mSharedPreference.edit();
    localEditor.putStringSet(paramString, paramSet);
    return localEditor.commit();
  }


}