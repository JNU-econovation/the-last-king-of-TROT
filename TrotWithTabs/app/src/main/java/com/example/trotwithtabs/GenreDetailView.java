package com.example.trotwithtabs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class GenreDetailView extends LinearLayout {
    TextView textView;

    public GenreDetailView(Context context) {
        super(context);

        init(context);
    }

    public GenreDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.genre_detail, this, true);

        textView = (TextView) findViewById(R.id.textView);
    }

    public void setName(String name){
        textView.setText(name);
    }

}
