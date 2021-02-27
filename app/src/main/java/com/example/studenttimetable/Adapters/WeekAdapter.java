package com.example.studenttimetable.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.studenttimetable.R;
import com.example.studenttimetable.Utils.LetterImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WeekAdapter extends ArrayAdapter {
    private int resource;
    private LayoutInflater layoutInflater;
    private String[] week = new String[]{};
    public WeekAdapter(Context context, int resource, String[] objects) {
        super(context, resource,objects);
        this.resource = resource;
        this.week = objects;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(resource,null);
            viewHolder.ivLogo = (LetterImageView)convertView.findViewById(R.id.ivLetter);
            viewHolder.tvWeek = (TextView)convertView.findViewById(R.id.tvMain);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.ivLogo.setOval(true);
        viewHolder.ivLogo.setLetter(week[position].charAt(0));
        viewHolder.tvWeek.setText(week[position]);
        return convertView;
    }

    class ViewHolder{
        private LetterImageView ivLogo;
        private TextView tvWeek;
    }
}
