package nodeal.example.you_tube_recyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trotwithtabs.R;

import java.util.ArrayList;
import java.util.List;

import nodeal.example.you_tube_recyclerview.ui.YoutubeAdapter;
import nodeal.example.you_tube_recyclerview.ui.YoutubeContent;



public class YoutubeSinger extends Fragment {

    private static final String TAG = "YoutubeID";
    String singerId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.youtube_singer, container, false);

        try{
            if(getArguments() != null) {
               singerId = getArguments().getString("Id");
                Log.d(TAG,singerId);
            }
        List<YoutubeContent> contents = new ArrayList<>();
        contents.add(new YoutubeContent(singerId));

        RecyclerView recyclerView = rootView.findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new YoutubeAdapter(contents));
    }catch(Exception e){
            System.err.println("오류 있음 "+e.getMessage()+e.getCause());
        }
        return rootView;
    }
}
