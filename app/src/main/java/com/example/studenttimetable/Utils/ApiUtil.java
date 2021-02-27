package com.example.studenttimetable.Utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiUtil {

    //http://192.168.1.4:8080/StudentTimetableSite_war_exploded/CourseTimetableServlet?courseName=1&badge=1&action=search&device=Mobile&viewType=commonStudentView
    public static final String BASE_API_URL = "http://192.168.1.2:8080/StudentTimetableSite_war_exploded/";
    private ApiUtil(){

    }
    //function to build and return the URL with title
    public static URL buildUrl(String title){
        String fullUrl = BASE_API_URL + title;
        URL url = null;
        try {
            url = new URL(fullUrl);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return url;
    }

    public static String getJson(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream stream = connection.getInputStream();
            //scanner buffers data and encode the character to UTF 16 which is the Android format
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            boolean hasData = scanner.hasNext();
            if (hasData) {
                return scanner.next();
            } else {
                return null;
            }
        }
        catch (Exception e)
        {
            Log.d("Error",e.toString());
            return null;
        }
        finally {
            connection.disconnect();
        }
    }


}
