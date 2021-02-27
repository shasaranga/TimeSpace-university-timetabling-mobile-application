package com.example.studenttimetable.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studenttimetable.R;

public class PreferencesOptionsAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private ImageView imageView;
    private TextView title, description;
    private String [] titleArray, descriptionArray;
    private String userType;
    private boolean isSubView =false;
    public PreferencesOptionsAdapter(Context context, String[] titleArray, String[] descriptionArray, String userType, boolean isSubView){
        this.mContext = context;
        this.titleArray = titleArray;
        this.descriptionArray = descriptionArray;
        this.userType = userType;
        this.isSubView = isSubView;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return titleArray.length;
    }

    @Override
    public Object getItem(int position) {
        return titleArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.options_single_item, null);
        }
        title = (TextView) convertView.findViewById(R.id.tvOptionMain);
        description = (TextView) convertView.findViewById(R.id.tvDescription);
        imageView = (ImageView) convertView.findViewById(R.id.ivMain);

        title.setText(titleArray[position]);
        description.setText(descriptionArray[position]);

        if(userType.equals("STUDENT")){
            if(titleArray[position].equalsIgnoreCase("Search Lecturer Timetable")){
                imageView.setImageResource(R.drawable.lecturer_timetable);

            }else if(titleArray[position].equalsIgnoreCase("Search Student Timetable")){
                imageView.setImageResource(R.drawable.student_timetable);

            }
        }else if(userType.equals("LECTURER")){
            if(titleArray[position].equalsIgnoreCase("Search Lecturer Timetable")){
                imageView.setImageResource(R.drawable.lecturer_timetable);

            }else if(titleArray[position].equalsIgnoreCase("Search Student Timetable")){
                imageView.setImageResource(R.drawable.student_timetable);

            }else if(titleArray[position].equalsIgnoreCase("Book a Classroom")){
                imageView.setImageResource(R.drawable.booking);

            }else if(titleArray[position].equalsIgnoreCase("Change / Delete Booking")){
                imageView.setImageResource(R.drawable.manage_booking);
            }
        }else if(userType.equals("ADMIN")){
            if(isSubView == false){
                if(titleArray[position].equalsIgnoreCase("Search Lecturer Timetable")){
                    imageView.setImageResource(R.drawable.lecturer_timetable);

                }else if(titleArray[position].equalsIgnoreCase("Search Student Timetable")){
                    imageView.setImageResource(R.drawable.student_timetable);

                }else if(titleArray[position].equalsIgnoreCase("Book a Classroom")){
                    imageView.setImageResource(R.drawable.booking);

                }else if(titleArray[position].equalsIgnoreCase("Change / Delete Booking")){
                    imageView.setImageResource(R.drawable.manage_booking);
                }else if(titleArray[position].equalsIgnoreCase("Manage Courses")){
                    imageView.setImageResource(R.drawable.courses);
                }else if(titleArray[position].equalsIgnoreCase("Manage Badges")){
                    imageView.setImageResource(R.drawable.badges);
                }else if(titleArray[position].equalsIgnoreCase("Manage Classrooms")){
                    imageView.setImageResource(R.drawable.classrooms);
                }
            }else{
                if(titleArray[position].equalsIgnoreCase("Add New Course")){
                    imageView.setImageResource(R.drawable.add_new_course);
                }else if(titleArray[position].equalsIgnoreCase("Add New Module")){
                    imageView.setImageResource(R.drawable.add_module);
                }else if(titleArray[position].equalsIgnoreCase("Edit / Delete a Course")){
                    imageView.setImageResource(R.drawable.manage_booking);
                }else if(titleArray[position].equalsIgnoreCase("Edit / Delete a Module")){
                    imageView.setImageResource(R.drawable.manage_booking);
                }else if(titleArray[position].equalsIgnoreCase("Add New Badge")){
                    imageView.setImageResource(R.drawable.new_badge);
                }else if(titleArray[position].equalsIgnoreCase("Edit / Delete a Badge")){
                    imageView.setImageResource(R.drawable.preference);
                }else if(titleArray[position].equalsIgnoreCase("Add New Classroom")){
                    imageView.setImageResource(R.drawable.new_badge);
                }else if(titleArray[position].equalsIgnoreCase("Edit / Delete a Classroom")){
                    imageView.setImageResource(R.drawable.preference);
                }
            }


        }


        return convertView;
    }
}