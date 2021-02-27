package com.example.studenttimetable.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.studenttimetable.Model.Module;
import com.example.studenttimetable.R;
import com.example.studenttimetable.Utils.LetterImageView;

import java.util.ArrayList;
import java.util.List;

public class ModuleAdapter extends ArrayAdapter {
    private int resource;
    private LayoutInflater layoutInflater;
    private List<String> moduleNamesList = new ArrayList<>();
    private List<Module> moduleList = new ArrayList<>();
    public ModuleAdapter(Context context, int resource, List<String> objects, List<Module> courseList) {
        super(context, resource,objects);
        this.resource = resource;
        this.moduleNamesList = objects;
        this.moduleList = courseList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ModuleAdapter.ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ModuleAdapter.ViewHolder();
            convertView = layoutInflater.inflate(resource,null);
            viewHolder.ivLogo = (LetterImageView)convertView.findViewById(R.id.ivIconLetter);
            viewHolder.tvClassName = (TextView)convertView.findViewById(R.id.tvTitleName);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ModuleAdapter.ViewHolder)convertView.getTag();
        }
        viewHolder.ivLogo.setOval(true);
        viewHolder.ivLogo.setLetter(moduleNamesList.get(position).charAt(0));
        viewHolder.tvClassName.setText(moduleNamesList.get(position));


        return convertView;
    }

    class ViewHolder{
        private LetterImageView ivLogo;
        private TextView tvClassName;
    }
}