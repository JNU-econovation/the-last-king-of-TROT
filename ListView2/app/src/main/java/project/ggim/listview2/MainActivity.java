package project.ggim.listview2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GenreAdapter adapter;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);

        adapter = new GenreAdapter();
        adapter.addItem(new GenreSongItem("영탁 - 막걸리 한 잔"));
        adapter.addItem(new GenreSongItem("이찬원 - 진또배기"));
        adapter.addItem(new GenreSongItem("영탁 - 넌 나의 20대였어"));
        adapter.addItem(new GenreSongItem("정동원 - 사모"));
        adapter.addItem(new GenreSongItem("임영웅 - 넌 감동이었어"));
        adapter.addItem(new GenreSongItem("이찬원, 옥진욱 - 남자라는 이유로"));
        adapter.addItem(new GenreSongItem("장민호, 솔지 - 밤이면 밤마다"));
        adapter.addItem(new GenreSongItem("장민호 - 처음 그 느낌처럼"));
        adapter.addItem(new GenreSongItem("이찬원 - 손가락하트"));
        adapter.addItem(new GenreSongItem("김희재 - 우린 너무 쉽게 헤어졌어요"));

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GenreSongItem item = (GenreSongItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택: " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    class GenreAdapter extends BaseAdapter {
        ArrayList<GenreSongItem> items = new ArrayList<GenreSongItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(GenreSongItem item) {
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

        //실무에서는 아래처럼 쓰면 부족하다
        //만약 천 개의 아이템이 있을 때 모든 아이템이 다 로딩되면 메모리가 엄청 많이 쓰일 것
        //마찬가지로 뷰 객체가 너무 많이 만들어지면 메모리를 엄청 많이 소모할 수 있다 - 스크롤할 때 리스트뷰가 끊어지는 느낌이 남
        //그래서 화면에 보이지 않는 것들은 재사용하게 하여(convertView) new를 만들 필요가 없게 해주면 된다
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GenreSongItemView view = null;
            if (convertView == null) {
                view = new GenreSongItemView(getApplicationContext());
            } else {
                view = (GenreSongItemView) convertView;
            }

            GenreSongItem item = items.get(position);
            view.setName(item.getName());
            return view;
        }
    }

}