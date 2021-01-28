package com.trot.trotwithtabs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class GenreItemView extends LinearLayout {
    TextView textView;

    public GenreItemView(Context context) {
        super(context);

        init(context);
    }

    public GenreItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.genre_item, this, true);

        textView = (TextView) findViewById(R.id.textView);
    }

    public void setName(String name) {
        textView.setText(name);
    }
}