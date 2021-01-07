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

import nodeal.example.you_tube_recyclerview.YoutubeGenre;
import nodeal.example.you_tube_recyclerview.YoutubeGenre;

public class GenreDetail extends Fragment {
    MainActivity activity;
    Context context;

    String title;
    String Id;
    String thumbnail;
    ArrayList<GenreInfoList> list;

    private static final String TAG = "genre";

    Fragment YoutubeGenre = new YoutubeGenre();

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

        activity = (MainActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.genre, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listViewGenre);
        try{

            if(getArguments() != null) {
                list = getArguments().getParcelableArrayList("genreInfoList");
                Log.d(TAG,list.get(0).title);
            }

            GenreDetailAdapter adapter = new GenreDetailAdapter();

            for (int i = 0; i < 15; i++) {
                adapter.addItem(new GenreItem(list.get(i).title));
            }

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Bundle bundle=new Bundle();
                    bundle.putString("Id", list.get(position).Id);
                    YoutubeGenre.setArguments(bundle);

                    ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, YoutubeGenre).commit();

                }
            });
        }catch(Exception e){
            System.err.println("오류 있음 "+e.getMessage()+e.getCause());
        }

        return rootView;
    }
    class GenreDetailAdapter extends BaseAdapter {
        ArrayList<GenreItem> items = new ArrayList<GenreItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(GenreItem item){
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
            GenreDetailView view = new GenreDetailView(getContext());

            GenreItem item = items.get(position);
            view.setName(item.getName());
            return view;
        }

        private void getApplicationContext() {
        }
    }
}
