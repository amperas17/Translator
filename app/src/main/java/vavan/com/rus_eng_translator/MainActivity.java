package vavan.com.rus_eng_translator;

/*
*   Application provide translating service for many languages using Yandex.translate api.
*
 */

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


public class MainActivity extends AppCompatActivity {

    final static String TAG_TRANSLATE = "TAG_TRANS";
    final static String TAG_HISTORY = "TAG_HIST";

    FrameLayout container;
    FragmentManager myFragmentManager;
    TranslateFragment translateFragment;
    HistoryFragment historyFragment;

    Button btTransFragment,btHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (FrameLayout)findViewById(R.id.container);
        myFragmentManager = getFragmentManager();
        translateFragment = new TranslateFragment();
        historyFragment = new HistoryFragment();

        btTransFragment = (Button)findViewById(R.id.btTransFragment);
        btHistoryFragment = (Button)findViewById(R.id.btHistoryFragment);

        btTransFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TranslateFragment fragment = (TranslateFragment)myFragmentManager
                        .findFragmentByTag(TAG_TRANSLATE);

                if (fragment == null){
                    //Bundle bundle = new Bundle();
                    FragmentTransaction fragmentTransaction = myFragmentManager
                            .beginTransaction();
                    fragmentTransaction.replace(R.id.container,translateFragment,TAG_TRANSLATE);
                    fragmentTransaction.commit();
                }
            }
        });

        btHistoryFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HistoryFragment fragment = (HistoryFragment)myFragmentManager
                        .findFragmentByTag(TAG_HISTORY);

                if (fragment == null){
                    //Bundle bundle = new Bundle();
                    FragmentTransaction fragmentTransaction = myFragmentManager
                            .beginTransaction();
                    fragmentTransaction.replace(R.id.container,historyFragment,TAG_HISTORY);
                    fragmentTransaction.commit();
                }
            }
        });




        if (savedInstanceState == null){
            // при первом запуске программы
            FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();

            // добавляем в контейнер при помощи метода add()
            fragmentTransaction.add(R.id.container, translateFragment, TAG_TRANSLATE);
            fragmentTransaction.commit();
        }


    }




}
