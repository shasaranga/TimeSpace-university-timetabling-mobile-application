package com.example.studenttimetable.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.studenttimetable.Model.Booking;
import com.example.studenttimetable.R;
import com.example.studenttimetable.Utils.LetterImageView;

import java.util.ArrayList;
import java.util.List;

public class LecturerBookingAdapter extends ArrayAdapter {
    private int resource;
    private LayoutInflater layoutInflater;
    private List<String> classNames = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    public LecturerBookingAdapter(Context context, int resource, List<String> objects, List<Booking> bookingList) {
        super(context, resource,objects);
        this.resource = resource;
        this.classNames = objects;
        this.bookings = bookingList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LecturerBookingAdapter.ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new LecturerBookingAdapter.ViewHolder();
            convertView = layoutInflater.inflate(resource,null);
            viewHolder.ivLogo = (LetterImageView)convertView.findViewById(R.id.ivBookingLetter);
            viewHolder.tvClassName = (TextView)convertView.findViewById(R.id.tvClassName);

            viewHolder.tvBookingDate = (TextView)convertView.findViewById(R.id.tvBookingDate);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (LecturerBookingAdapter.ViewHolder)convertView.getTag();
        }
        viewHolder.ivLogo.setOval(true);
        viewHolder.ivLogo.setLetter(classNames.get(position).charAt(0));
        viewHolder.tvClassName.setText(classNames.get(position));

        if(!classNames.isEmpty()){
            if(classNames.get(0).equals("No Booking Available!")){
                viewHolder.tvBookingDate.setText("");
            }else{
                viewHolder.tvBookingDate.setText(bookings.get(position).getStartDateLocalString()+" - " + bookings.get(position).getEndDateLocalString());
            }
        }else{
            System.out.println("Viewholder Class Names list is empty!");
        }

        return convertView;
    }

    class ViewHolder{
        private LetterImageView ivLogo;
        private TextView tvClassName;
        private TextView tvBookingDate;
    }
}
