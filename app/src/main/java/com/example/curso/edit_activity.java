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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
@RequiresApi(api = Build.VERSION_CODES.O)
public class edit_activity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static final String TAG = "MainActivity";
    private TextView mDisplayDate, mDisplayHour ,location, meetingName;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mHourSetListener;
    int hour, minutes;
    EditText inputName, inputType, inputLocation;
    //***************************************************
    private String itemName = "com.example.curso.ITEM_NAME";
    private String locationVal = "com.example.curso.LOCATION";
    private String dateVal = "com.example.curso.DATE";
    private String timeVal = "com.example.curso.TIME";
    private int idMeeting = 0;
    private String typeMeeting =  "com.example.curso.ITEM_TYPE";;

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
       // inputType = (Spinner)findViewById(R.id.ea_type_spinner);

        // Get the Intent that started this activity and extract the string
        //This is the new content, only for edit view
        itemName = intent.getStringExtra(MainActivity.ITEM_NAME);
        locationVal = intent.getStringExtra(MainActivity.LOCATION);
        dateVal = intent.getStringExtra(MainActivity.MEETING_DATE);
        timeVal = intent.getStringExtra(MainActivity.MEETING_TIME);
        idMeeting = intent.getIntExtra(String.valueOf(MainActivity.ID),0 );
        typeMeeting = intent.getStringExtra(MainActivity.ITEM_TYPE);

        // Capture the layout's TextView and set the string as its text

        meetingName.setText(itemName);
        mDisplayDate.setText(dateVal);
        mDisplayHour.setText(timeVal);
        location.setText(locationVal);
        Spinner spinner = (Spinner) findViewById(R.id.ea_type_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        if (typeMeeting != null) {
            int spinnerPosition = adapter.getPosition(typeMeeting);
            spinner.setSelection(spinnerPosition);
        }
        //**********************************
        // *****************

        //***************************************************


        mDisplayHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] timePart = timeVal.split(":");
                hour = Integer.parseInt(timePart[0]);
                String[] minutesTemp = timePart[1].split(" ");
                minutes =Integer.parseInt(minutesTemp[0]);

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
    public boolean validate() {
        boolean returningBol = true;
        String fieldName = itemName;
        // String fieldType = inputType.getText().toString();
        mDisplayDate = (TextView)findViewById(R.id.ea_input_date);
        mDisplayHour = (TextView)findViewById(R.id.ea_input_hour);
        inputName = (EditText)findViewById(R.id.ea_name_input);
        // inputType = (EditText)findViewById(R.id.ea_type_input);
        inputLocation = (EditText)findViewById(R.id.ea_input_location);
        String dateField = mDisplayDate.getText().toString();
        String timeField = mDisplayHour.getText().toString();
        String fieldLocation = locationVal;

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
        Toast.makeText(edit_activity.this,  " Saved" , Toast.LENGTH_LONG).show();
        Gson gson = new Gson();
        Spinner spinner = (Spinner) findViewById(R.id.ea_type_spinner);
        String jsonFileString = utils.getJson(getApplicationContext(), "todoListNew.json");
        Type listToDo = new TypeToken<ArrayList<ToDo>>() { }.getType();
        ArrayList<ToDo> records =  new ArrayList<ToDo>();
        int lastId = 1;
        if(jsonFileString.length() > 0) {
            records = gson.fromJson(jsonFileString, listToDo);
            for (ToDo item : records) {

                if(item.Id == idMeeting ){
                    item.itemName = meetingName.getText().toString();
                    item.meetingLocation = location.getText().toString();
                    item.meetingDate = mDisplayDate.getText().toString();
                    item.meetingTime = mDisplayHour.getText().toString();
                    item.itemType =  spinner.getSelectedItem().toString();
                }

            }
        }


        //
        ToDo newItem = new ToDo(lastId, itemName, spinner.getSelectedItem().toString(), locationVal, dateVal,timeVal);
       // records.add(newItem);
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

    public void Delete(){
        Toast.makeText(edit_activity.this,  " Deleted" , Toast.LENGTH_LONG).show();
        Gson gson = new Gson();
        Spinner spinner = (Spinner) findViewById(R.id.ea_type_spinner);
        String jsonFileString = utils.getJson(getApplicationContext(), "todoListNew.json");
        Type listToDo = new TypeToken<ArrayList<ToDo>>() { }.getType();
        ArrayList<ToDo> records =  new ArrayList<ToDo>();
        int lastId = 1;
        if(jsonFileString.length() > 0) {
            records = gson.fromJson(jsonFileString, listToDo);
            for (ToDo item : records) {

                if(item.Id == idMeeting ){
                    records.remove(item);
                }

            }
        }


        //
        ToDo newItem = new ToDo(lastId, itemName, spinner.getSelectedItem().toString(), locationVal, dateVal,timeVal);
        // records.add(newItem);
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
    public void Edit(View view) {

        if (validate()) {
            Save();
            Return(view);
        }else{
            Toast.makeText(edit_activity.this,  " Error, fill all the form" , Toast.LENGTH_LONG).show();
        }

    }

    public void Delete(View view) {



            Delete();
            Return(view);


    }
}