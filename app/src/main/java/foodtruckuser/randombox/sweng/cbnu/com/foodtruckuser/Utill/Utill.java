package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by son on 2016-11-05.
 */
public class Utill {

    private static Utill utill = null;

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

    public static int getRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        return Color.parseColor(Colors[p]);
    }
}
