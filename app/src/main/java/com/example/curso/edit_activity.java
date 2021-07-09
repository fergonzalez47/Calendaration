package com.example.curso;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Timer;

public class edit_activity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mDisplayDate, mDisplayHour;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mHourSetListener;
    int hour, minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();

        mDisplayDate = (TextView)findViewById(R.id.ea_input_date);
        mDisplayHour = (TextView)findViewById(R.id.ea_input_hour);

        mDisplayHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timedialog = new TimePickerDialog(
                        edit_activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        minutes = minute;

                        Calendar calendarHour = Calendar.getInstance();
                        calendarHour.set(0,0,0, hour, minutes);

                        mDisplayHour.setText(DateFormat.format("HH:mm aa", calendarHour));
                    }
                },12, 0, false
                );

                timedialog.updateTime(hour, minutes);
                timedialog.show();
            }
        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        edit_activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }
    // Method to call receive the main activity

    public void Return(View view) {
        Intent goBack = new Intent(this, MainActivity.class);
        startActivity(goBack);

    }
}