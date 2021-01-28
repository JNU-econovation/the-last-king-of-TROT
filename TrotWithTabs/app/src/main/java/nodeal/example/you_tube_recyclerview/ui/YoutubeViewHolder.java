package nodeal.example.you_tube_recyclerview.ui;

import android.view.View;

import com.trot.trotwithtabs.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class YoutubeViewHolder extends RecyclerView.ViewHolder {

    YouTubePlayerView youTubePlayerView;

    public YoutubeViewHolder(@NonNull View itemView) {
        super(itemView);

        youTubePlayerView = itemView.findViewById(R.id.card_content_player_view);
    }
}
