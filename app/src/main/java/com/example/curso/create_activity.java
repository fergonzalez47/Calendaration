package com.example.curso;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class create_activity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mDisplayDate, mDisplayHour ;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mHourSetListener;
    int hour, minutes;
    EditText inputName, inputType, inputLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Intent intent = getIntent();

        mDisplayDate = (TextView)findViewById(R.id.ea_input_date);
        mDisplayHour = (TextView)findViewById(R.id.ea_input_hour);
        inputName = (EditText)findViewById(R.id.ea_name_input);
        inputType = (EditText)findViewById(R.id.ea_type_input);
        inputLocation = (EditText)findViewById(R.id.ea_input_location);

        //***************************************************


        mDisplayHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timedialog = new TimePickerDialog(
                        create_activity.this, new TimePickerDialog.OnTimeSetListener() {
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
                        create_activity.this,
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

    public boolean validate() {
        boolean returningBol = true;
        String fieldName = inputName.getText().toString();
        String fieldType = inputType.getText().toString();
        String fieldLocation = inputLocation.getText().toString();

        if (fieldName.isEmpty()) {
            returningBol = false;
            inputName.setError("You must fill this field");
        }
        if (fieldType.isEmpty()) {
            returningBol = false;
            inputType.setError("You must fill this field");
        }
        if (fieldLocation.isEmpty()) {
            returningBol = false;
            inputLocation.setError("You must fill this field");
        }
        return returningBol;
    }

    public void Return(View view) {
        Intent goBack = new Intent(this, MainActivity.class);
        startActivity(goBack);

    }

    public void Create(View view) {
        if (validate()) {
            Return(view);

            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        }

    }
}