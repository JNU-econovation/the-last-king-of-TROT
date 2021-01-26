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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;

import org.json.JSONArray;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nodeal.example.you_tube_recyclerview.YoutubeGenre;

public class GenreDetail extends Fragment {
    MainActivity activity;
    Context context = getActivity();

    DBOpenHelper helper;
    SQLiteDatabase db;

    ArrayList<GenreInfoList> list;
    ArrayList<SongJjimList> songJjimList;

    private static final String TAG = "genre";

    Fragment YoutubeGenre = new YoutubeGenre();
    boolean[] isJjim;
    int getPosition;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.genre, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listViewGenre);
        try{
            helper = new DBOpenHelper(this.getContext());
            db = helper.getWritableDatabase();

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
                    bundle.putParcelableArrayList("list",(ArrayList<? extends Parcelable>) list);
                    bundle.putInt("firstPosition",position);
                    YoutubeGenre.setArguments(bundle);

                    ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, YoutubeGenre).addToBackStack(null).commit();
                }
            });

            isJjim = new boolean[15];

        }catch(Exception e){
            System.err.println("오류 있음 "+e.getMessage()+e.getCause());
        }

        return rootView;
    }
    public class GenreDetailAdapter extends BaseAdapter {
        ArrayList<GenreItem> items = new ArrayList<GenreItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(GenreItem item){
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
            final GenreDetailView view = new GenreDetailView(getContext());
            final Context context = parent.getContext();
            View v = convertView;
            final ViewHolder viewHolder;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.genre_detail, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.button1 = (Button) v.findViewById(R.id.button);
                viewHolder.button2 = (Button) v.findViewById(R.id.button2);
                viewHolder.textView = (TextView) v.findViewById(R.id.textView);
                viewHolder.imageView=(ImageView) v.findViewById(R.id.imageView);
                viewHolder.favoriteBtn = (CheckBox) v.findViewById(R.id.favorite);
                v.setTag(viewHolder);

                songJjimList = helper.selectSongJjim();

                for (int i = 0; i < songJjimList.size(); i++) {
                    for (int j = 0; j < list.size(); j++) {
                        if (songJjimList.get(i).state == 1) {
                            isJjim[j] = true;
                            Log.d("test", String.valueOf(isJjim[j]));
                        } else {
                            isJjim[j] = false;
                            Log.d("test", String.valueOf(isJjim[j]));
                        }
                    }
                }

                viewHolder.favoriteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CheckBox selectedBtn = (CheckBox) view;
                        if (selectedBtn.isChecked()) {
                            helper.insertSongJjim(list.get(position).Id, list.get(position).title, list.get(position).thumbnail);
                            Log.d("DB", "노래 찜 추가됨");
                            selectedBtn.setChecked(true);
                        }
                    }
                });

                if(viewHolder.favoriteBtn != null) {
                    viewHolder.favoriteBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            getPosition = (Integer) buttonView.getTag();
                            //arrayList.get(Integer.valueOf(position)).setSelected(buttonView.isChecked());
                        }
                    });
                }

            } else {
                viewHolder = (ViewHolder) v.getTag();
            }

            //viewHolder.favoriteBtn.setChecked(getPosition);

            GenreItem item = items.get(position);
            viewHolder.textView.setText(item.getName());

            //ImageView imageView = view.findViewById(R.id.imageView);
            String imageUrl = list.get(position).thumbnail;
            ImageLoadTask task = new ImageLoadTask(imageUrl,viewHolder.imageView);
            task.execute();



            //final Button button = (Button) view.findViewById(R.id.button);



            /*
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < songJjimList.size(); j++) {
                    if (list.get(i).title.equals(songJjimList.get(j).title)) {
                        viewHolder.button1.setText("취소");
                        Log.d("dbtest", "취소");
                    }else{
                        viewHolder.button1.setText("찜");
                    }
                }
            }

             */

            viewHolder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (viewHolder.button1.getText().equals("찜")) {
                            helper.insertSongJjim(list.get(position).Id, list.get(position).title, list.get(position).thumbnail);
                            Log.d("DB", "노래 찜 추가됨");
                            viewHolder.button1.setText("취소");
                        } else {
                            helper.deleteSongJjim(list.get(position).Id);
                        }

                    } catch (Exception e) {
                        System.err.println("오류 있음 " + e.getMessage() + e.getCause());
                    }
                }
            });

            //Button button2 = (Button) view.findViewById(R.id.button2);
            viewHolder.button2.setOnClickListener(new View.OnClickListener() {
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

            return v;
        }
        private void getApplicationContext() {
        }

        private void setStringArrayPref(Context context, String key, ArrayList<String> values) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = prefs.edit();
            JSONArray a = new JSONArray();
            for (int i = 0; i < values.size(); i++) {
                a.put(values.get(i));
            }
            if (!values.isEmpty()) {
                editor.putString(key, a.toString());
            } else {
                editor.putString(key, null);
            }
            editor.apply();
        }
    }

    public class ViewHolder {
        Button button1;
        Button button2;
        TextView textView;
        ImageView imageView;
        CheckBox favoriteBtn;
    }




    public static class ImageLoadTask extends AsyncTask<Void,Void, Bitmap> {

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
