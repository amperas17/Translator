package vavan.com.rus_eng_translator;

/*
*   Application provide translating service for many languages using Yandex.translate api.
*   There are two fragments, that are shown with the help of ViewPager
 */

import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager)findViewById(R.id.vpContainer);

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(Fragment.instantiate(this, TranslateFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, HistoryFragment.class.getName()));

        pager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments));
        pager.setCurrentItem(0);


    }




}
