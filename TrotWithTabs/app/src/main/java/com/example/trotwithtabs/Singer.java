package com.example.trotwithtabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Singer extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.singer, container, false);

        String[] list_singer = {"임영웅","정동원","이찬원","영탁","김호중","장민호","김희재","조명섭","송가인","나훈아","장윤정"};
        ListView listView = (ListView) rootView.findViewById(R.id.listViewSinger);

        SingerAdapter adapter = new SingerAdapter();
        adapter.addItem(new SingerItem("임영웅"));
        adapter.addItem(new SingerItem("정동원"));
        adapter.addItem(new SingerItem("이찬원"));
        adapter.addItem(new SingerItem("영탁"));
        adapter.addItem(new SingerItem("김호중"));
        adapter.addItem(new SingerItem("장민호"));
        adapter.addItem(new SingerItem("김희재"));
        adapter.addItem(new SingerItem("조명섭"));
        adapter.addItem(new SingerItem("송가인"));
        adapter.addItem(new SingerItem("나훈아"));
        adapter.addItem(new SingerItem("장윤정"));
        adapter.addItem(new SingerItem("nct"));

        listView.setAdapter(adapter);



        return rootView;
    }
    class SingerAdapter extends BaseAdapter {
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
            SingerItemView view = new SingerItemView(getContext());

            SingerItem item = items.get(position);
            view.setName(item.getName());
            return view;
        }

        private void getApplicationContext() {
        }
    }

}