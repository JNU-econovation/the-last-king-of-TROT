package com.example.trotwithtabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import nodeal.example.you_tube_recyclerview.YoutubeSinger;

public class ComedyDetail extends Fragment {
    MainActivity activity;
    Context context;

    DBOpenHelper helper;
    SQLiteDatabase db;

    String title;
    String Id;
    String thumbnail;
    ArrayList<SingerInfoList> list;
    ArrayList<SongJjimList> songJjimList;
    int i = 0;

    private static final String TAG = "comedy";

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
            helper = new DBOpenHelper(this.getContext());
            db = helper.getWritableDatabase();

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
                    bundle.putParcelableArrayList("list",(ArrayList<? extends Parcelable>) list);
                    bundle.putInt("firstPosition",position);
                    YoutubeSinger.setArguments(bundle);

                    ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, YoutubeSinger).addToBackStack(null).commit();

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
            SingerDetailView view = new SingerDetailView(getContext());

            SingerItem item = items.get(position);
            view.setName(item.getName());

            ImageView imageView = view.findViewById(R.id.imageView);
            String imageUrl = list.get(position).thumbnail;
            ImageLoadTask task = new ImageLoadTask(imageUrl, imageView);
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

        private void getApplicationContext() {
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
