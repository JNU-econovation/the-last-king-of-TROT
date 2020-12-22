package com.example.trotwithtabs;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Home fragment0;
    Genre fragment1;
    Popular fragment2;
    Singer fragment3;
    Comedy fragment4;
    Jjim fragment5;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment0 = new Home();
        fragment1 = new Genre();
        fragment2 = new Popular();
        fragment3 = new Singer();
        fragment4 = new Comedy();
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
                    selected = fragment0;
                }else if(position==1){
                    selected = fragment1;
                }else if(position==2){
                    selected = fragment2;
                }else if(position==3){
                    selected = fragment3;
                }else if(position==4){
                    selected = fragment4;
                }else if(position==5){
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