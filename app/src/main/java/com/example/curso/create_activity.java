package com.example.curso;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
       // inputType = (EditText)findViewById(R.id.ea_type_input);
        inputLocation = (EditText)findViewById(R.id.ea_input_location);
        Spinner spinner = (Spinner) findViewById(R.id.ea_type_spinner);
    // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.types_array, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
        //**********************************
        // *****************


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
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month , day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }
    // Method to call receive the main activity

    public boolean validate() {
        boolean returningBol = true;
        String fieldName = inputName.getText().toString();
       // String fieldType = inputType.getText().toString();


        String fieldLocation = inputLocation.getText().toString();
        String dateField = mDisplayDate.getText().toString();
        String timeField = mDisplayHour.getText().toString();
        if (fieldName.isEmpty()) {
            returningBol = false;

            inputName.setError("You must fill this field");
        }

        if (fieldLocation.isEmpty()) {
            returningBol = false;

            inputLocation.setError("You must fill this field");

        }
        if (dateField.equals("Date")) {
            returningBol = false;

            mDisplayDate.setError("You must fill this field");

        }
        if (timeField.equals("Hour")) {
            returningBol = false;

            mDisplayHour.setError("You must fill this field");

        }
        return returningBol;
    }

    public void Return(View view) {
        Intent goBack = new Intent(this, MainActivity.class);
        startActivity(goBack);

    }
    public void Save(){
        Toast.makeText(create_activity.this,  " Saved" , Toast.LENGTH_LONG).show();
        Gson gson = new Gson();
        Spinner spinner = (Spinner) findViewById(R.id.ea_type_spinner);
        String jsonFileString = utils.getJson(getApplicationContext(), "todoListNew.json");
        Type listToDo = new TypeToken<ArrayList<ToDo>>() { }.getType();
        ArrayList<ToDo> records =  new ArrayList<ToDo>();
        int lastId = 1;
        if(jsonFileString.length() > 0) {
            records = gson.fromJson(jsonFileString, listToDo);
            lastId =records.size() + 1;
        }else{
            records =  new ArrayList<ToDo>();
        }
        //
        ToDo newItem = new ToDo(lastId, inputName.getText().toString(), spinner.getSelectedItem().toString(), inputLocation.getText().toString(),mDisplayDate.getText().toString(), mDisplayHour.getText().toString());
        records.add(newItem);
        String filePath= "todoListNew.json";
        String todoData = gson.toJson(records);

        try{
            //Get your FilePath and use it to create your File
            String yourFilePath = this.getFilesDir() + "/" + filePath;
            File yourFile = new File(yourFilePath);
//Create your FileOutputStream, yourFile is part of the constructor
            FileOutputStream fileOutputStream = new FileOutputStream(yourFile);
//Convert your JSON String to Bytes and write() it
            fileOutputStream.write(todoData.getBytes());
//Finally flush and close your FileOutputStream
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Create(View view) {

        if (validate()) {
            Save();
            Return(view);
        }else{
            Toast.makeText(create_activity.this,  " Error, fill all the form" , Toast.LENGTH_LONG).show();
        }

    }
}