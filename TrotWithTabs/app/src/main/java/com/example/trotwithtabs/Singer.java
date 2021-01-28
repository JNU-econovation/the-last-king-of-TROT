package com.example.trotwithtabs;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Singer extends Fragment {
    MainActivity activity;
    Context context;

    DBOpenHelper helper;
    SQLiteDatabase db;

    private static final String TAG = "singer";

    private String result;
    int singerPosition;
    String[] list_singer = {"임영웅","정동원","이찬원","영탁","김호중","장민호","김희재","조명섭","송가인","나훈아","장윤정","김용임","강진","금잔디","김연자","남진","박상철","박현빈","설운도","송대관","심수봉","신유","요요미","이미자","정미애","조항조","주현미","진성","태진아","하춘화","현철","홍자","홍진영"};
    ArrayList<SingerInfoList> singerInfoList;
    ArrayList<SingerInfoList> singerInfoList2;
    String singerName;

    ArrayList<SingerJjimList> singerJjimList;
    public boolean isCheck[] = new boolean[50];
    int i = 0;
    int querySize;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

        activity = (MainActivity) getActivity();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        querySize=50;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.singer, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listViewSinger);

        SingerAdapter adapter = new SingerAdapter();

        helper = new DBOpenHelper(this.getContext());
        db = helper.getWritableDatabase();

        for (int i = 0; i < list_singer.length; i++) {
            adapter.addItem(new SingerItem(list_singer[i]));
        }

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                singerPosition= ReturnSingerPosition(position);
                YoutubeAsyncTask youtubeAsyncTask = new YoutubeAsyncTask();
                youtubeAsyncTask.execute();
                Log.d(TAG,list_singer[singerPosition]);
            }
        });

        return rootView;
    }

    public int ReturnSingerPosition(int position){
        return position;
    }

    private class YoutubeAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
                final JsonFactory JSON_FACTORY = new JacksonFactory();
                final long NUMBER_OF_VIDEOS_RETURNED = querySize;

                YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName("youtube-search-sample").build();

                YouTube.Search.List search = youtube.search().list("id,snippet");

                search.setKey(getString(R.string.youtube_api_key));

                singerName=list_singer[singerPosition];
                search.setQ(singerName);
                search.setOrder("relevance"); //date relevance

                search.setType("video");

                search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
                search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
                SearchListResponse searchResponse = search.execute();

                List<SearchResult> searchResultList = searchResponse.getItems();

                if (searchResultList != null) {
                    prettyPrint(searchResultList.iterator(), singerName);
                }
            } catch (GoogleJsonResponseException e) {
                System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                        + e.getDetails().getMessage());
                System.err.println("There was a service error 2: " + e.getLocalizedMessage() + " , " + e.toString());
            } catch (IOException e) {
                System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
            } catch (Throwable t) {
                t.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Fragment singerDetailFragment = new SingerDetail();
            singerInfoList = singerInfoList2;

            Bundle bundle=new Bundle();
            bundle.putParcelableArrayList("singerInfoList",(ArrayList<? extends Parcelable>) singerInfoList);
            singerDetailFragment.setArguments(bundle);
            Log.d(TAG,singerInfoList.get(0).title);

            ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, singerDetailFragment).addToBackStack(null).commit();
        }

        public void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {
            if (!iteratorSearchResults.hasNext()) {
                System.out.println(" There aren't any results for your query.");
            }

            StringBuilder sb = new StringBuilder();

            singerInfoList2 = new ArrayList<>();

            while (iteratorSearchResults.hasNext()) {
                SearchResult singleVideo = iteratorSearchResults.next();
                ResourceId rId = singleVideo.getId();

                if (rId.getKind().equals("youtube#video")) {
                    Thumbnail thumbnail = (Thumbnail) singleVideo.getSnippet().getThumbnails().get("default");
                    singerInfoList2.add(new SingerInfoList(singleVideo.getSnippet().getTitle(),rId.getVideoId(),thumbnail.getUrl()));
                    Log.d(TAG,singerInfoList2.get(0).title);
                }

            }

            result = sb.toString();
        }
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
                for (int j = 0; j < list_singer.length; j++) {
                    if (singerJjimList.get(i).name.equals(list_singer[j])) {
                        isCheck[j] = true;
                    } else if (isCheck[j] == true) {
                        isCheck[j] = true;
                    } else if (isCheck[j] == false && !singerJjimList.get(i).name.equals(list_singer[j])){
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
                                helper.deleteSingerJjim(list_singer[position]);
                                Toast.makeText(getContext(), "찜을 취소했습니다.", Toast.LENGTH_SHORT).show();
                            } else{
                                isCheck[position] = true;
                                helper.insertSingerJjim(list_singer[position]);
                                Toast.makeText(getContext(), list_singer[position] + "을(를) 찜했습니다.", Toast.LENGTH_SHORT).show();
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

}