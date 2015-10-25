package vavan.com.rus_eng_translator;

/*
*   Application provide translating service for many languages using Yandex.translate api.
*
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

    /*final static String TAG_TRANSLATE = "TAG_TRANS";
    final static String TAG_HISTORY = "TAG_HIST";

    FrameLayout container;
    FragmentManager myFragmentManager;
    TranslateFragment translateFragment;
    HistoryFragment historyFragment;

    Button btTransFragment,btHistoryFragment;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager)findViewById(R.id.vpContainer);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Fragment.instantiate(this, TranslateFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, HistoryFragment.class.getName()));

        pager.setAdapter(new PagerAdapter(getSupportFragmentManager(),fragments));
        pager.setCurrentItem(0);


    }




}
