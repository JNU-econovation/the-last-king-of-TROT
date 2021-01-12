package com.example.trotwithtabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import nodeal.example.you_tube_recyclerview.YoutubeSinger;

public class Popular extends Fragment {
    MainActivity activity;
    Context context;

    ArrayList<SingerInfoList> list;

    private static final String TAG = "singer";

    Fragment YoutubeSinger = new YoutubeSinger();

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

        //메시지 송수신에 필요한 객체 초기화
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //XML, java 연결
        //XML이 메인에 직접 붙으면 true, 프래그먼트에 붙으면 false
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.singer, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listViewSinger);
        try{

            if(getArguments() != null) {
                list = getArguments().getParcelableArrayList("singerInfoList");
                Log.d(TAG,list.get(0).title);
            }

            SingerDetailAdapter adapter = new SingerDetailAdapter();

            for (int i = 0; i < 15; i++) {
                adapter.addItem(new SingerItem(list.get(i).title));
            }

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Bundle bundle=new Bundle();
                    bundle.putString("Id", list.get(position).Id);
                    YoutubeSinger.setArguments(bundle);

                    ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, YoutubeSinger).commit();

                }
            });
        }catch(Exception e){
            System.err.println("오류 있음 "+e.getMessage()+e.getCause());
        }

        return rootView;
    }
    class SingerDetailAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(SingerItem item){
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SingerDetailView view = new SingerDetailView(getContext());

            SingerItem item = items.get(position);
            view.setName(item.getName());
            return view;
        }

        private void getApplicationContext() {
        }
    }
}
