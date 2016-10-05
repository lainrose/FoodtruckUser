package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.join;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.main.MainActivity;

import me.relex.circleindicator.CircleIndicator;


public class IntroMain extends FragmentActivity {
    IntroAdapter adapter;
    ViewPager pager;
    Context context = this;
    Sqlite sqlite = new Sqlite(context);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intromain);

        sqlite.getWritableDatabase();
        sqlite.InsertData();

        String[] arrData = sqlite.SelectData("1");
        if (arrData[0].length() > 0) {
            String Flag = arrData[1];
            if (Flag.equals("1")) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        }

        adapter = new IntroAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
    }
}