package com.tahsin.calendarview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    private static TextView today;
    private static Date date;
    private static Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = (Button) findViewById(R.id.button2);
        today = (TextView) findViewById(R.id.today);
        date = new Date();

        today.setText("Today is " + date.toString());
        bt.setOnClickListener(e->
        {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        });
    }
}
