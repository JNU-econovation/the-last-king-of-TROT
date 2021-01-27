package com.example.trotwithtabs;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Home extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.home, container, false);
        final String[][] TrotSchedule = new String[3][32];
        TrotSchedule[1]= new String[]{"","","","","","","","","","",
                "","","","","","","","","","",
                "","","","","","뽕숭아학당 오후 8시 TV조선", "사랑의 콜센타 오후 10시","뽕숭아학당 오후 8시 TV조선","뽕숭아학당 오후 8시 TV조선","사랑의 콜센타 오후 10시","사랑의 콜센타 오후 10시",""};
        TrotSchedule[2]= new String[]{"","","","","","","","","","",
                "","","","","","","","","","",
                "","","","","","", "","","","","",""};

        final TextView textViewCalendar = rootView.findViewById(R.id.textViewCalendar);
        CalendarView calendarView = rootView.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String textCalendar =month+1+"월"+" "+dayOfMonth+"일"+" "+"편성 정보" +"\n"+TrotSchedule[month+1][dayOfMonth];
                //특정 글자 진하게
                SpannableString spannableString = new SpannableString(textCalendar);
                String word = month+1+"월"+" "+dayOfMonth+"일"+" "+"편성 정보";
                int start = textCalendar.indexOf(word);
                int end = start + word.length();
                spannableString.setSpan(new StyleSpan(Typeface.BOLD),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textViewCalendar.setText(spannableString);
            }
        });

        TextView textViewInfo = rootView.findViewById(R.id.textViewInform);
        String textInform="행사 및 정보"+"\n"+"송가인 영화, 설 명절 극장 개봉 확정"+"\n"+"영탁, 발라드 트로트로 컴백 중비중";
        //특정 글자 진하게
        SpannableString spannableString2 = new SpannableString(textInform);
        String word2 ="행사 및 정보";
        int start2 = textInform.indexOf(word2);
        int end2 = start2 + word2.length();
        spannableString2.setSpan(new StyleSpan(Typeface.BOLD),start2,end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewInfo.setText(spannableString2);

        return rootView;
    }

}
