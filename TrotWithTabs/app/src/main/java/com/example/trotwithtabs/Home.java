package com.example.trotwithtabs;

import android.app.AlertDialog;
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
import android.content.DialogInterface;

public class Home extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.home, container, false);
        final String[][] TrotSchedule = new String[13][32];
        TrotSchedule[1]= new String[]{"","","","","","","","","","", "",
                "","","","","","","","","", "",
                "","","","","",
                "","","오후 10:00 미스트롯2 (TV조선)", "오후 10:00 사랑의 콜센타 (TV조선)\n오후 10:30 트롯 전국체전 (KBS2)", "", ""};
        TrotSchedule[2]= new String[]{"","오후 10:00 가요무대 (KBS1)","","오후 10:00 뽕숭아학당 (TV조선)","오후 10:00 미스트롯2 (TV조선)","오후 10:00 사랑의 콜센타 (TV조선)\n오후 10:30 트롯 전국체전 (KBS2)",
                "","","오후 10:00 가요무대 (KBS1)","", "오후 10:00 뽕숭아학당 (TV조선)",
                "오후 10:00 미스트롯2 (TV조선)", "오후 10:00 사랑의 콜센타 (TV조선)\n오후 10:30 트롯 전국체전 (KBS2)","","","오후 10:00 가요무대 (KBS1)",
                "","오후 10:00 뽕숭아학당 (TV조선)","오후 10:00 미스트롯2 (TV조선)","오후 10:00 사랑의 콜센타 (TV조선)\n오후 10:30 트롯 전국체전 (KBS2)", "",
                "","오후 10:00 가요무대 (KBS1)","","오후 10:00 뽕숭아학당 (TV조선)","오후 10:00 미스트롯2 (TV조선)",
                "오후 10:00 사랑의 콜센타 (TV조선)\n오후 10:30 트롯 전국체전 (KBS2)","","","","",""};
        TrotSchedule[3]= new String[]{"","","","","","","","","","", "", "","","","","","","","","", "", "","","","","", "","","", "", "", ""};
        TrotSchedule[4]= new String[]{"","","","","","","","","","", "", "","","","","","","","","", "", "","","","","", "","","", "", "", ""};
        TrotSchedule[5]= new String[]{"","","","","","","","","","", "", "","","","","","","","","", "", "","","","","", "","","", "", "", ""};
        TrotSchedule[6]= new String[]{"","","","","","","","","","", "", "","","","","","","","","", "", "","","","","", "","","", "", "", ""};
        TrotSchedule[7]= new String[]{"","","","","","","","","","", "", "","","","","","","","","", "", "","","","","", "","","", "", "", ""};
        TrotSchedule[8]= new String[]{"","","","","","","","","","", "", "","","","","","","","","", "", "","","","","", "","","", "", "", ""};
        TrotSchedule[9]= new String[]{"","","","","","","","","","", "", "","","","","","","","","", "", "","","","","", "","","", "", "", ""};
        TrotSchedule[10]= new String[]{"","","","","","","","","","", "", "","","","","","","","","", "", "","","","","", "","","", "", "", ""};
        TrotSchedule[11]= new String[]{"","","","","","","","","","", "", "","","","","","","","","", "", "","","","","", "","","", "", "", ""};
        TrotSchedule[12]= new String[]{"","","","","","","","","","", "", "","","","","","","","","", "", "","","","","", "","","", "", "", ""};



        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());

        CalendarView calendarView = rootView.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
               builder.setTitle(month+1+"월"+" "+dayOfMonth+"일"+" "+"편성 정보");
               builder.setMessage(TrotSchedule[month+1][dayOfMonth]);
               builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                   }
               });
               builder.show();
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
