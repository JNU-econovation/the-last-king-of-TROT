package nodeal.example.you_tube_recyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.trotwithtabs.DBOpenHelper;
import com.example.trotwithtabs.GenreDetailView;
import com.example.trotwithtabs.GenreInfoList;
import com.example.trotwithtabs.GenreItem;
import com.example.trotwithtabs.R;
import com.example.trotwithtabs.SingerDetail;
import com.example.trotwithtabs.SingerInfoList;
import com.example.trotwithtabs.SongJjimList;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nodeal.example.you_tube_recyclerview.ui.YoutubeAdapter;
import nodeal.example.you_tube_recyclerview.ui.YoutubeContent;


public class YoutubeGenre extends Fragment {

    private static final String TAG = "YoutubeID";
    String genreId;
    ArrayList<GenreInfoList> list;
    ArrayList<SongJjimList> songJjimList;
    ViewGroup rootView;
    ListView listView;
    int firstPosition;

    DBOpenHelper helper;
    SQLiteDatabase db;
    int i = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(R.layout.youtube_genre, container, false);
        listView = (ListView) rootView.findViewById(R.id.listViewGenreYoutube);
        try {
            helper = new DBOpenHelper(this.getContext());
            db = helper.getWritableDatabase();

            if (getArguments() != null) {
                genreId = getArguments().getString("Id");
                list = getArguments().getParcelableArrayList("list");
                firstPosition = getArguments().getInt("firstPosition");
                Log.d(TAG, genreId);
            }
            List<YoutubeContent> contents = new ArrayList<>();
            contents.add(new YoutubeContent(genreId));

            RecyclerView recyclerView = rootView.findViewById(R.id.main_recycler_view2);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(new YoutubeAdapter(contents));

            GenreDetailAdapter adapter = new GenreDetailAdapter();

            for (int i = 0; i < 15; i++) {
                adapter.addItem(new GenreItem(list.get(i).title));
            }

            listView.setAdapter(adapter);
            listView.post(new Runnable() {
                @Override
                public void run() {
                    if (null != listView) {
                        listView.clearFocus();
                        listView.requestFocusFromTouch();
                        listView.setSelection(firstPosition);
                    }
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listView.setSelection(position);

                    List<YoutubeContent> contents2 = new ArrayList<>();
                    contents2.add(new YoutubeContent(list.get(position).Id));

                    RecyclerView recyclerView = rootView.findViewById(R.id.main_recycler_view2);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(new YoutubeAdapter(contents2));
                }
            });
        } catch (Exception e) {
            System.err.println("오류 있음 " + e.getMessage() + e.getCause());
        }
        return rootView;
    }

    class GenreDetailAdapter extends BaseAdapter {
        ArrayList<GenreItem> items = new ArrayList<GenreItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(GenreItem item) {
            items.add(item);
            this.notifyDataSetChanged();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            GenreDetailView view = new GenreDetailView(getContext());

            GenreItem item = items.get(position);
            view.setName(item.getName());

            ImageView imageView = view.findViewById(R.id.imageView);
            String imageUrl = list.get(position).thumbnail;
            ImageLoadTask task = new ImageLoadTask(imageUrl,imageView);
            task.execute();

            final Button button = (Button) view.findViewById(R.id.button);

            songJjimList = helper.selectSongJjim();
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < songJjimList.size(); j++) {
                    if (songJjimList.get(j).Id == list.get(i).Id) {
                        button.setText("취소");
                    } else {
                        button.setText("찜");
                    }
                }
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        i += 1;
                        if (button.getText().equals("찜")) {
                            helper.insertSongJjim(list.get(position).Id, list.get(position).title, list.get(position).thumbnail);
                            Log.d("DB", "노래 찜 추가됨");
                            button.setText("취소");
                        } else {
                            button.setText("찜");
                        }
                    } catch (Exception e) {
                        System.err.println("오류 있음 " + e.getMessage() + e.getCause());
                    }
                }
            });

            Button button2 = (Button) view.findViewById(R.id.button2);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        FeedTemplate params = FeedTemplate
                                .newBuilder(ContentObject.newBuilder(list.get(position).title,
                                        list.get(position).thumbnail,
                                        LinkObject.newBuilder().setWebUrl("https://www.youtube.com/watch?v="+list.get(position).Id)
                                                .setMobileWebUrl("https://www.youtube.com/watch?v="+list.get(position).Id).build())
                                        .setDescrption("이미지를 클릭하면 해당 영상의 유튜브 링크로 연결됩니다.")
                                        .build())
                                .build();

                        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
                        serverCallbackArgs.put("user_id", "${current_user_id}");
                        serverCallbackArgs.put("product_id", "${shared_product_id}");


                        KakaoLinkService.getInstance().sendDefault(getContext(), params, new ResponseCallback<KakaoLinkResponse>() {
                            @Override
                            public void onFailure(ErrorResult errorResult) {
                            }

                            @Override
                            public void onSuccess(KakaoLinkResponse result) {
                            }
                        });

                    } catch (Exception e) {
                        System.err.println("오류 있음 " + e.getMessage() + e.getCause());
                    }
                }
            });

            return view;
        }
    }
    private class ImageLoadTask extends AsyncTask<Void,Void, Bitmap> {

        private String urlStr;
        private ImageView imageView;
        private HashMap<String, Bitmap> bitmapHash = new HashMap<String, Bitmap>();

        public ImageLoadTask(String urlStr, ImageView imageView) {
            this.urlStr = urlStr;
            this.imageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            try {
                if (bitmapHash.containsKey(urlStr)) {
                    Bitmap oldbitmap = bitmapHash.remove(urlStr);
                    if(oldbitmap != null) {
                        oldbitmap.recycle();
                        oldbitmap = null;
                    }
                }
                URL url = new URL(urlStr);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                bitmapHash.put(urlStr,bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            imageView.setImageBitmap(bitmap);
            imageView.invalidate();
        }
    }
}
