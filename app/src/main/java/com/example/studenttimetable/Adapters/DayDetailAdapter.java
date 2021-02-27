package com.example.studenttimetable.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.studenttimetable.Model.Timetable;
import com.example.studenttimetable.R;
import com.example.studenttimetable.Utils.LetterImageView;

import java.util.ArrayList;
import java.util.List;

public class DayDetailAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private TextView subject, time;
   // private String[] subjectArray;
    private List<Timetable> subjectArray = new ArrayList<>();
    private String[] timeArray;
    private LetterImageView letterImageView;

    public DayDetailAdapter(Context context,List<Timetable> subjectArray, String[] timeArray){
        mContext = context;

        this.subjectArray = subjectArray;
        this.timeArray = timeArray;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return subjectArray.size();
    }

    @Override
    public Object getItem(int position) {
        return subjectArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.day_detail_single_item, null);
        }

        subject = (TextView)convertView.findViewById(R.id.tvSubjectDayDetails);
        time = (TextView)convertView.findViewById(R.id.tvTimeDayDetail);
        letterImageView = (LetterImageView)convertView.findViewById(R.id.ivDayDetails);

        subject.setText(subjectArray.get(position).getModuleName());
        time.setText(subjectArray.get(position).getStartTimeString()+" - "+subjectArray.get(position).getEndTimeString());

        letterImageView.setOval(true);
        letterImageView.setLetter(subjectArray.get(position).getModuleName().charAt(0));
       // letterImageView.setLetter(subjectArray[position].charAt(0));
        return convertView;

    }
}

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()){
//            case android.R.id.home : {
//                onBackPressed();
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}