package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.preference;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPrefWrpper
{
  private SharedPreferences mSharedPreference;
  
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
    Editor localEditor = this.mSharedPreference.edit();
    localEditor.putBoolean(paramString, paramBoolean);
    return localEditor.commit();
  }
  
  protected boolean setDouble(String paramString, double paramDouble)
  {
    Editor localEditor = this.mSharedPreference.edit();
    localEditor.putLong(paramString, Double.doubleToRawLongBits(paramDouble));
    return localEditor.commit();
  }
  
  protected boolean setFloat(String paramString, float paramFloat)
  {
    Editor localEditor = this.mSharedPreference.edit();
    localEditor.putFloat(paramString, paramFloat);
    return localEditor.commit();
  }
  
  protected boolean setInt(String paramString, int paramInt)
  {
    Editor localEditor = this.mSharedPreference.edit();
    localEditor.putInt(paramString, paramInt);
    return localEditor.commit();
  }
  
  protected boolean setLong(String paramString, long paramLong)
  {
    Editor localEditor = this.mSharedPreference.edit();
    localEditor.putLong(paramString, paramLong);
    return localEditor.commit();
  }
  
  public void setPreference(SharedPreferences paramSharedPreferences)
  {
    this.mSharedPreference = paramSharedPreferences;
  }

  protected boolean setStringSet(String paramString, Set<String> paramSet)
  {
    Editor localEditor = this.mSharedPreference.edit();
    localEditor.putStringSet(paramString, paramSet);
    return localEditor.commit();
  }
}