package com.example.trotwithtabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nodeal.example.you_tube_recyclerview.YoutubeJjim;

public class Jjim extends Fragment {
    MainActivity activity;
    Context context;

    ArrayList<SongJjimList> list;
    ArrayList<SongJjimList> songJjimList;
    ArrayList<SingerJjimList> singerJjimList;
    ArrayList<SingerInfoList> singerInfoList;
    ArrayList<SingerInfoList> singerInfoList2;
    ListView listView;
    String singerName;
    public boolean isCheck[] = new boolean[15];

    DBOpenHelper helper;
    SQLiteDatabase db;

    private static final String TAG = "singer";

    Fragment YoutubeJjim = new YoutubeJjim();

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.jjim, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        Button songJjimBtn = (Button) rootView.findViewById(R.id.button1);
        Button singerJjimBtn = (Button) rootView.findViewById(R.id.button2);

        helper = new DBOpenHelper(this.getContext());
        db = helper.getWritableDatabase();

        list = new ArrayList<>();

        songJjimList = helper.selectSongJjim();
        singerJjimList = helper.selectSingerJjim();

        getSongJjimList();

        songJjimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSongJjimList();
            }
        });

        singerJjimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSingerJjimList();
            }
        });

        return rootView;
    }

    public void getSongJjimList() {
        try {
            SingerDetailAdapter adapter = new SingerDetailAdapter();

            for (int i = 0; i < songJjimList.size(); i++) {
                adapter.addItem(new SingerItem(songJjimList.get(i).title));
            }

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Id", songJjimList.get(position).Id);
                    bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) songJjimList);
                    bundle.putInt("firstPosition", position);
                    YoutubeJjim.setArguments(bundle);

                    ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, YoutubeJjim).addToBackStack(null).commit();
                }
            });
        } catch (Exception e) {
            System.err.println("오류 있음 " + e.getMessage() + e.getCause());
        }
    }

    public void getSingerJjimList() {
        try {
            SingerItemAdapter adapter = new SingerItemAdapter();

            for (int i = 0; i < singerJjimList.size(); i++) {
                adapter.addItem(new SingerItem(singerJjimList.get(i).name));
            }

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Id", singerJjimList.get(position).name);
                    bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) singerJjimList);
                    bundle.putInt("firstPosition", position);
                    YoutubeJjim.setArguments(bundle);
                    singerName=singerJjimList.get(position).name;

                    ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, YoutubeJjim).addToBackStack(null).commit();
                }
            });
        } catch (Exception e) {
            System.err.println("오류 있음 " + e.getMessage() + e.getCause());
        }
    }

    class SingerDetailAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(SingerItem item) {
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.singer_detail, parent, false);
            }
            Button button2 = (Button) v.findViewById(R.id.button2);
            TextView textView = (TextView) v.findViewById(R.id.textView);
            ImageView imageView=(ImageView) v.findViewById(R.id.imageView);
            CheckBox favoriteBtn = (CheckBox) v.findViewById(R.id.favorite);

            SingerItem item = items.get(position);
            textView.setText(item.getName());

            songJjimList = helper.selectSongJjim();

            for (int i = 0; i < songJjimList.size(); i++) {
                for (int j = 0; j < songJjimList.size(); j++) {
                    if (songJjimList.get(i).Id.equals(songJjimList.get(j).Id)) {
                        isCheck[j] = true;
                    } else if (isCheck[j] == true) {
                        isCheck[j] = true;
                    } else if (isCheck[j] == false && !songJjimList.get(i).Id.equals(songJjimList.get(j).Id)){
                        isCheck[j] = false;
                    }
                }
            }

            if (item != null) {
                if (favoriteBtn != null) {
                    favoriteBtn.setChecked(false);
                    CheckBox cbox = (CheckBox)(v.findViewById(R.id.favorite));
                    cbox.setChecked(isCheck[position]);
                    cbox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isCheck[position]) {
                                isCheck[position] = false;
                                helper.deleteSongJjim(songJjimList.get(position).Id);
                                //resetSongJjimList();
                            } else{
                                isCheck[position] = true;
                                helper.insertSongJjim(songJjimList.get(position).Id, songJjimList.get(position).title, songJjimList.get(position).thumbnail);
                            }
                        }
                    });
                    favoriteBtn.setChecked(isCheck[position]);
                }
            }

            String imageUrl = songJjimList.get(position).thumbnail;
            ImageLoadTask task = new ImageLoadTask(imageUrl,imageView);
            task.execute();

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        FeedTemplate params = FeedTemplate
                                .newBuilder(ContentObject.newBuilder(songJjimList.get(position).title,
                                        songJjimList.get(position).thumbnail,
                                        LinkObject.newBuilder().setWebUrl("https://www.youtube.com/watch?v="+songJjimList.get(position).Id)
                                                .setMobileWebUrl("https://www.youtube.com/watch?v="+songJjimList.get(position).Id).build())
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

            return v;
        }

        private void getApplicationContext() {
        }
    }
/*
    private void resetSongJjimList() {
        SingerDetailAdapter adapter = (SingerDetailAdapter) listView.getAdapter();
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    private void resetSingerJjimList() {
        SingerItemAdapter adapter = (SingerItemAdapter) listView.getAdapter();
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }
*/
    class SingerItemAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(SingerItem item) {
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.singer_item, parent, false);
            }
            TextView textView = (TextView) v.findViewById(R.id.textView);
            CheckBox favoriteBtn = (CheckBox) v.findViewById(R.id.favorite);

            SingerItem item = items.get(position);
            textView.setText(item.getName());

            singerJjimList = helper.selectSingerJjim();

            for (int i = 0; i < singerJjimList.size(); i++) {
                for (int j = 0; j < singerJjimList.size(); j++) {
                    if (singerJjimList.get(i).name.equals(singerJjimList.get(j).name)) {
                        isCheck[j] = true;
                    } else if (isCheck[j] == true) {
                        isCheck[j] = true;
                    } else if (isCheck[j] == false && !singerJjimList.get(i).name.equals(singerJjimList.get(j).name)){
                        isCheck[j] = false;
                    }
                }
            }

            if (item != null) {
                if (favoriteBtn != null) {
                    favoriteBtn.setChecked(false);
                    CheckBox cbox = (CheckBox)(v.findViewById(R.id.favorite));
                    cbox.setChecked(isCheck[position]);
                    cbox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isCheck[position]) {
                                isCheck[position] = false;
                                helper.deleteSingerJjim(singerJjimList.get(position).name);
                                //resetSingerJjimList();
                            } else{
                                isCheck[position] = true;
                                helper.insertSingerJjim(singerJjimList.get(position).name);
                            }
                        }
                    });
                    favoriteBtn.setChecked(isCheck[position]);
                }
            }

            return v;
        }

        private void getApplicationContext() {
        }
    }

    private ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    //썸네일
    private class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

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
                    if (oldbitmap != null) {
                        oldbitmap.recycle();
                        oldbitmap = null;
                    }
                }
                URL url = new URL(urlStr);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                bitmapHash.put(urlStr, bitmap);

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
