package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

public class Utill {

    private static Utill utill = null;
    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static synchronized Utill getInstance()
    {
        if(utill == null){}
        try{
            if(utill==null)
                utill = new Utill();
            return utill;
        }
        finally {}
    }

    public static String[] Colors = {
            "#F44336", "#E91E63", "#9C27B0", "#2196F3", "#03A9F4", "#00BCD4", "#009688", "#4CAF50",
            "#8BC34A", "#CDDC39", "#FFEB3B", "#FFC107", "#FF9800", "#FF5722", "#795548", "#9E9E9E", "#607D8B"
    };

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
    public static boolean isAndroid4(){
        return android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB;
    }
    public static int getRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        return Color.parseColor(Colors[p]);
    }
    public void MoveAcitivity(Context context, final Class<? extends Activity> ActivityToOpen, String parm) {
        Intent intent = new Intent(context, ActivityToOpen);
        intent.putExtra("Key", parm);
        context.startActivity(intent);
    }
    public void MoveAcitivity(Context context, final Class<? extends Activity> ActivityToOpen, int parm) {
        Intent intent = new Intent(context, ActivityToOpen);
        intent.putExtra("Key", parm);
        context.startActivity(intent);
    }
    public void MoveAcitivity(Context context, final Class<? extends Activity> ActivityToOpen, Double parm, Double parm1) {
        Intent intent = new Intent(context, ActivityToOpen);
        intent.putExtra("Key", parm);
        intent.putExtra("Key1", parm1);
        context.startActivity(intent);
    }
    public void MoveAcitivity(Context context, final Class<? extends Activity> ActivityToOpen) {
        Intent intent = new Intent(context, ActivityToOpen);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public void MoveAcitivity(Context context, String phoneText) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+phoneText));
        context.startActivity(callIntent);
    }
}
