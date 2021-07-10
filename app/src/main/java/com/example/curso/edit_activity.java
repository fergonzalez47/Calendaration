package com.example.curso;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.O)
public class edit_activity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static final String TAG = "MainActivity";
    private TextView mDisplayDate, mDisplayHour ,location, meetingName;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mHourSetListener;
    int hour, minutes;

    //***************************************************
    private String itemName = "com.example.curso.ITEM_NAME";
    private String locationVal = "com.example.curso.ITEM_NAME";
    private String dateVal = "com.example.curso.ITEM_NAME";
    private String timeVal = "com.example.curso.ITEM_NAME";
    private int idMeeting = 0;
    private int typeMeeting = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();

        mDisplayDate = (TextView)findViewById(R.id.ea_input_date);
        mDisplayHour = (TextView)findViewById(R.id.ea_input_hour);
        meetingName = (TextView)findViewById(R.id.ea_name_input);
        location = (TextView)findViewById(R.id.ea_input_location);
       // idMeeting = (TextView)findViewById(R.id.ea_input_date);
       // typeMeeting = (TextView)findViewById(R.id.ea_input_hour);

        // Get the Intent that started this activity and extract the string
        //This is the new content, only for edit view
        itemName = intent.getStringExtra(MainActivity.ITEM_NAME);
        locationVal = intent.getStringExtra(MainActivity.LOCATION);
        dateVal = intent.getStringExtra(MainActivity.MEETING_DATE);
        timeVal = intent.getStringExtra(MainActivity.MEETING_TIME);
        idMeeting = intent.getIntExtra(String.valueOf(MainActivity.ID),0 );
        typeMeeting = intent.getIntExtra(String.valueOf(MainActivity.ITEM_TYPE),0);

        // Capture the layout's TextView and set the string as its text

        meetingName.setText(itemName);
        mDisplayDate.setText(dateVal);
        mDisplayHour.setText(timeVal);
        location.setText(locationVal);
        //***************************************************


        mDisplayHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] timePart = timeVal.split(":");
                hour = Integer.parseInt(timePart[0]);
                minutes =Integer.parseInt(timePart[1]);

                TimePickerDialog timedialog = new TimePickerDialog(
                        edit_activity.this, mHourSetListener ,hour, minutes, false
                );

                timedialog.updateTime(hour, minutes);
                timedialog.show();
            }
        });
        mHourSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                minutes = minute;

                Calendar calendarHour = Calendar.getInstance();
                calendarHour.set(0,0,0, hour, minutes);

                mDisplayHour.setText(DateFormat.format("HH:mm aa", calendarHour));
            }
        };

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] datePart = dateVal.split("/");
                int year = Integer.parseInt(datePart[2]);
                int month = Integer.parseInt(datePart[1])-1;
                int day = Integer.parseInt(datePart[0]);

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