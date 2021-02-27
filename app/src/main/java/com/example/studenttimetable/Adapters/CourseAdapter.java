package com.example.studenttimetable.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.studenttimetable.Model.Course;
import com.example.studenttimetable.R;
import com.example.studenttimetable.Utils.LetterImageView;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends ArrayAdapter {
    private int resource;
    private LayoutInflater layoutInflater;
    private List<String> courseNameList = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();
    public CourseAdapter(Context context, int resource, List<String> objects, List<Course> courseList) {
        super(context, resource,objects);
        this.resource = resource;
        this.courseNameList = objects;
        this.courseList = courseList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CourseAdapter.ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new CourseAdapter.ViewHolder();
            convertView = layoutInflater.inflate(resource,null);
            viewHolder.ivLogo = (LetterImageView)convertView.findViewById(R.id.ivIconLetter);
            viewHolder.tvClassName = (TextView)convertView.findViewById(R.id.tvTitleName);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (CourseAdapter.ViewHolder)convertView.getTag();
        }
        viewHolder.ivLogo.setOval(true);
        viewHolder.ivLogo.setLetter(courseNameList.get(position).charAt(0));
        viewHolder.tvClassName.setText(courseNameList.get(position));


        return convertView;
    }

    class ViewHolder{
        private LetterImageView ivLogo;
        private TextView tvClassName;
    }
}