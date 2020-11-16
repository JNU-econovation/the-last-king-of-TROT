package project.ggim.listview;

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
        adapter.addItem(new GenreItem("트로트 메들리"));
        adapter.addItem(new GenreItem("댄스 트로트"));
        adapter.addItem(new GenreItem("전설의 트로트"));
        adapter.addItem(new GenreItem("최신트로트"));
        adapter.addItem(new GenreItem("보이스트롯"));
        adapter.addItem(new GenreItem("미스트롯 송가인"));
        adapter.addItem(new GenreItem("미스터트롯"));
        adapter.addItem(new GenreItem("7080 메들리"));
        adapter.addItem(new GenreItem("7080 인기가수"));

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GenreItem item = (GenreItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택: " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    class GenreAdapter extends BaseAdapter {
        ArrayList<GenreItem> items = new ArrayList<GenreItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(GenreItem item) {
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
            GenreItemView view = null;
            if (convertView == null) {
                view = new GenreItemView(getApplicationContext());
            } else {
                view = (GenreItemView) convertView;
            }

            GenreItem item = items.get(position);
            view.setName(item.getName());
            return view;
        }
    }

}