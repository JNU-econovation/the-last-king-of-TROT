package com.example.trotwithtabs;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    static final String[] Singer_List = {"임영웅", "정동원", "이찬원","영탁","김호중","장민호","김희재","조명섭","송가인","나훈아","장윤정"} ;
    Home fragment1;
    Jjim fragment5;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment1 = new Home();
        fragment5 = new Jjim();

        getSupportFragmentManager().beginTransaction().add(R.id.container,fragment1).commit();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("홈"));
        tabs.addTab(tabs.newTab().setText("장르"));
        tabs.addTab(tabs.newTab().setText("인기"));
        tabs.addTab(tabs.newTab().setText("가수"));
        tabs.addTab(tabs.newTab().setText("예능"));
        tabs.addTab(tabs.newTab().setText("찜"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position =  tab.getPosition();

                Fragment selected = null;
                if(position == 0) {
                    selected = fragment1;
                } else if(position==5){
                    selected = fragment5;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}