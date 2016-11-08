package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Ui.join.intro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class IntroAdapter extends FragmentPagerAdapter {
    private final int NUM_ITEMS = 5;
    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    public int getCount() {
        return NUM_ITEMS;
    }
    public Fragment getItem(int position) {
        if (position == 0)
            return IntroOne.newInstance();
        else if (position == 1)
            return IntroTwo.newInstance();
        else if (position == 2)
            return IntroThree.newInstance();
        else if (position == 3)
            return IntroFour.newInstance();
        else if (position == 4)
            return IntroFive.newInstance();
        return null;
    }
}
