package com.example.trotv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);

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



    }
    class SingerAdapter extends BaseAdapter{
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
            SingerItemView view = new SingerItemView(getApplicationContext());

            SingerItem item = items.get(position);
            view.setName(item.getName());
            return view;
        }
    }
}