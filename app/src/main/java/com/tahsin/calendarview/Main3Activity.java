package com.tahsin.calendarview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;

public class Main3Activity extends AppCompatActivity
{
    private static Date date;
    private static Calendar cal;
    private static int d;
    private static int m;
    private static int y;
    private static int last_day_of_month;
    private static SharedPreferences sharedPref;
    private static String[] strArr;
    private static ArrayAdapter adapter;
    private static ListView listView;
    private static SwipeRefreshLayout refresher;
    private static Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //For saving and loading data across activities
        sharedPref = getSharedPreferences("Events_Of_Month" ,MODE_PRIVATE);
        date = new Date();
        refresher = (SwipeRefreshLayout) findViewById(R.id.refresher);
        delete = (Button) findViewById(R.id.delete);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            //Calculating Current time and date
            cal = Calendar.getInstance();
            cal.setTime(date);
            d = cal.get(Calendar.DAY_OF_MONTH);
            m = cal.get(Calendar.MONTH);
            y = cal.get(Calendar.YEAR);
            //For month of 30 days or 31 days
            if(m == 0 || m == 2 || m == 4 || m == 6 || m == 7 || m == 9 || m == 11)
                last_day_of_month = 31;
            else
                last_day_of_month = 30;
        }
        strArr = new String[last_day_of_month - d + 1];
        //strArr = new String[last_day_of_month];

        for(int i = d, j = 0; i <= last_day_of_month; i++, j++)
        {
            String key = Integer.toString(i) + Integer.toString(m + 1) + Integer.toString(y);
            String data = sharedPref.getString(key, "");
            strArr[j] = Integer.toString(i) + "/" + Integer.toString(m + 1) + "/" + Integer.toString(y) + " " + data;
        }

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, R.layout.list_view_layout, strArr);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapter, view, pos, id)->
        {
            String key = Integer.toString(d + pos) + Integer.toString(m + 1) + Integer.toString(y);
            sharedPref.edit().remove(key).commit();
            Toast.makeText(Main3Activity.this, "Event deleted, swipe down to refresh", Toast.LENGTH_LONG).show();
        });

        refresher.setOnRefreshListener(()->
        {
            for(int i = d, j = 0; i <= last_day_of_month; i++, j++)
            {
                String key = Integer.toString(i) + Integer.toString(m + 1) + Integer.toString(y);
                String data = sharedPref.getString(key, "");
                strArr[j] = Integer.toString(i) + "/" + Integer.toString(m + 1) + "/" + Integer.toString(y) + " " + data;
            }
            adapter.notifyDataSetChanged();
            refresher.setRefreshing(false);
        });

        delete.setOnClickListener(e->
        {
            sharedPref.edit().clear().commit();
            Toast.makeText(Main3Activity.this, "All events are deleted, swipe down to refresh", Toast.LENGTH_LONG).show();
        });
    }
}
