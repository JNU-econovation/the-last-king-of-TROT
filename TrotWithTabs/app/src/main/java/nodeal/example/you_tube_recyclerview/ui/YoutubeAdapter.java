package nodeal.example.you_tube_recyclerview.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trotwithtabs.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.List;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeViewHolder> {
    private List<YoutubeContent> contents;

    public YoutubeAdapter(@NonNull List<YoutubeContent> contents) {
        this.contents = contents;
    }

    @NonNull
    @Override
    public YoutubeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_you_tube_content, parent, false);

        return new YoutubeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeViewHolder holder, final int position) {
        holder.youTubePlayerView.setEnableAutomaticInitialization(false);
        holder.youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(contents.get(position).videoId, 0);
            }
        }, false);
    }

    @Override
    public int getItemCount() {
        return (contents != null) ? contents.size() : 0;
    }
}
