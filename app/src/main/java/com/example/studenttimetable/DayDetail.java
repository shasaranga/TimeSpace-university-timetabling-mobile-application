package com.example.studenttimetable;

import android.os.Bundle;
import android.widget.ListView;

import com.example.studenttimetable.Adapters.DayDetailAdapter;
import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.SharedPrefManager;
import com.example.studenttimetable.Model.Timetable;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DayDetail extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;


    private String[] PreferredTime;

    private BackgroundTask backgroundTask =null;
    private static List<Timetable> timetable = new ArrayList<>();


    public boolean isCalled =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_detail);
        timetable = SharedPrefManager.getInstance(getApplicationContext()).getRecordList();;
        System.out.println("***********  TIME TABLE ***********");
        System.out.println("SEARCH RECORDS : "+TimetableSearchActivity.recordlist.size());
        backgroundTask = new BackgroundTask(getApplicationContext());
        setupUIViews();
        initToolbar();
        setUpListView();

    }
    private void setupUIViews(){
        listView = (ListView)findViewById(R.id.lvDayDetail);
        toolbar = (Toolbar) findViewById(R.id.ToolbarDayDetail);

    }
    private void initToolbar(){
        toolbar = (Toolbar)findViewById(R.id.ToolbarDayDetail);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(WeekActivity.sharedPreferences.getString(WeekActivity.selectedDay,null));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    private void setUpListView(){


        String selectedDay = WeekActivity.sharedPreferences.getString(WeekActivity.selectedDay, null);


        if(selectedDay.equalsIgnoreCase("Monday")){

            timetable = backgroundTask.getMondayRecords(timetable);


        }else if(selectedDay.equalsIgnoreCase("Tuesday")){
            timetable = backgroundTask.getTuesdayRecords(timetable);

        }else if(selectedDay.equalsIgnoreCase("Wednesday")){
            timetable = backgroundTask.getWednesdayRecords(timetable);

        }else if(selectedDay.equalsIgnoreCase("Thursday")){
            timetable = backgroundTask.getThursdayRecords(timetable);

        }else if(selectedDay.equalsIgnoreCase("Friday")){
            timetable = backgroundTask.getFridayRecords(timetable);

        }else{
            System.out.println("INSIDE ELSE");
            timetable = new ArrayList<>();
//            Timetable record = new Timetable("No Scheduled Lecture ");
//            timetable.add(record);
        }
        DayDetailAdapter dayDetailAdapter = new DayDetailAdapter(this,timetable ,PreferredTime);
        listView.setAdapter(dayDetailAdapter);

    }


//    public class TimetableQueryTask extends AsyncTask<URL,Void,List<Timetable>>{
//
//        @RequiresApi(api = Build.VERSION_CODES.N)
//        @Override
//        protected List<Timetable> doInBackground(URL... urls) {
//            URL selectedUrl = urls[0];
//            String result = null;
//            try{
//                result = ApiUtil.getJson(selectedUrl);
//            } catch (IOException e) {
//                Log.d("Error", e.getMessage());
//            }
//           // JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
//            List<Timetable> timetables = new Gson().fromJson(result, new TypeToken<List<Timetable>>() {}.getType());
//            timetables.forEach(System.out::println);
//            return timetables;
//        }
//
//     //   @Override
////        protected void onPostExecute(List<Timetable>  timetable) {
////            super.onPostExecute(timetable);
//////            TextView tvResult = (TextView) findViewById(R.id.tvResponse);
//////            tvResult.setText(result);
////        }
//
//    }
}
