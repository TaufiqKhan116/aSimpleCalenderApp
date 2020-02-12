package com.tahsin.calendarview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class Main2Activity extends AppCompatActivity
{
    private static CalendarView calendarView;
    private static TextView label;
    private static EditText event;
    private static Button addEvent;
    private static Button List;
    private static SharedPreferences sharedPref;
    private static String key;
    private static Date date;
    private static Calendar cal;
    private static int d, m, y;
    private static boolean prev = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        label = (TextView) findViewById(R.id.textView);
        addEvent = (Button) findViewById(R.id.add);
        List = (Button) findViewById(R.id.List);
        event = (EditText) findViewById(R.id.editText);

        sharedPref = getSharedPreferences("Events_Of_Month", MODE_PRIVATE);
        date = new Date();
        cal = Calendar.getInstance();


        cal.setTime(date);
        d = cal.get(Calendar.DAY_OF_MONTH);
        m = cal.get(Calendar.MONTH)+1;
        y = cal.get(Calendar.YEAR);


        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth)->
        {
            String storedEvent;

            if(dayOfMonth < d || (month + 1) < m || year < y)
                prev = true;
            else
                prev = false;
            label.setText("Add an Event at  " + dayOfMonth + "/" + (month + 1) + "/" + year + " :");
            key = Integer.toString(dayOfMonth) + Integer.toString(month + 1) + Integer.toString(year);
            storedEvent = sharedPref.getString(key, "");
            if(storedEvent != "" && !prev)
            {
                Toast.makeText(Main2Activity.this, storedEvent, Toast.LENGTH_LONG).show();
            }
        });
        addEvent.setOnClickListener(e->
        {
            if(prev)
            {
                Toast.makeText(Main2Activity.this, "You are not supposed to time travel", Toast.LENGTH_LONG).show();
                prev = false;
            }
            else
            {
                String eventData;
                eventData = event.getText().toString();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(key, eventData);
                editor.commit();
                event.setText("");
                Toast.makeText(Main2Activity.this, "Event added", Toast.LENGTH_SHORT).show();
            }
        });
        List.setOnClickListener(e->
        {
            Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
            startActivity(intent);
        });
    }
}
