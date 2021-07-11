package com.example.curso;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.content.Intent;
import java.lang.reflect.Type;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    private TextView textview1, textview2, textview3, textview4;
    private ListView listview1, listview2, listview3, listview4;
    public static final int ID = 0;
    public static final String ITEM_NAME = "com.example.curso.ITEM_NAME";
    public static final String LOCATION = "com.example.myfirstapp.LOCATION";
    public static final String MEETING_DATE = "com.example.myfirstapp.DATE";
    public static final String MEETING_TIME = "com.example.myfirstapp.TIME";
    public static final int ITEM_TYPE = 0;

    private String items_name [] = {"Read Scripture", "Make Assignment", "Call bank", "Finish app"};
    private String items_date [] = {"17/06/2021", "22/06/2021", "25/07/2021", "01/08/2021"};
    private String doc_name [] = {"Doctor Allcome", "Doctor Pleaseno"};
    private String doc_date [] = {"25/12/2021", "16/07/2021"};
    private String meeting_name [] = {"Scrum", "Cry Session", "Unemployment Office"};
    private String meeting_date [] = {"24/06/2021", "22/06/2021", "25/06/2021"};
    private String meeting_time [] = {"20:00", "16:19", "09:00"};
    private String jsonData = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Loading Json File
        String jsonFileString = utils.getJsonFromAssets(getApplicationContext(), "todolist.json");
        Gson gson = new Gson();
        Type listToDo = new TypeToken<ArrayList<ToDo>>() { }.getType();
        ArrayList<ToDo> records = gson.fromJson(jsonFileString, listToDo);
        Log.i("data", jsonFileString);
        //Read the arraylist to filter the data for each type of meeting
        ArrayList<ToDo> homework = new ArrayList<ToDo> () ;
        ArrayList <String> homeworkName = new ArrayList<String> () ;
        ArrayList<ToDo> doctorAppointment = new ArrayList<ToDo> () ; ;
        ArrayList <String> doctorAppointmentName = new ArrayList<String> () ;
        ArrayList<ToDo> meeting  =new ArrayList<ToDo> () ;;
        ArrayList <String> meetingName = new ArrayList<String> () ;
        for(ToDo item : records) {
            switch (item.itemType){

                case 1:
                    homework.add(item);
                    //Create the list for the listview, with the names only
                    homeworkName.add(item.itemName);
                    break;
                case 2:
                    doctorAppointment.add(item);
                    //Create the list for the listview, with the names only
                    doctorAppointmentName.add(item.itemName);
                    break;
                case 3:
                    meeting.add(item);
                    //Create the list for the listview, with the names only
                    meetingName.add(item.itemName);
                    break;
            }
        }


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        textview1 = (TextView)findViewById(R.id.textView1);
        textview2 = (TextView)findViewById(R.id.textView2);
        textview3 = (TextView)findViewById(R.id.textView3);
        //textview4 = (TextView)findViewById(R.id.textView4);
        listview1 = (ListView)findViewById(R.id.listView1);
        listview2 = (ListView)findViewById(R.id.listView2);
        listview3 = (ListView)findViewById(R.id.listView3);
        //listview4 = (ListView)findViewById(R.id.listView4);

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_homework, homeworkName);
        listview1.setAdapter(adapter);


        ArrayAdapter <String> adapter2 = new ArrayAdapter<String>(this, R.layout.list_item_2, doctorAppointmentName);
        listview2.setAdapter(adapter2);

        ArrayAdapter <String> adapter3 = new ArrayAdapter<String>(this, R.layout.list_item_2, meetingName);
        listview3.setAdapter(adapter3);

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //textview1.setText(listview1.getItemAtPosition(position) + " Due:" + homework.get(position).meetingDate );
              //  Toast.makeText(MainActivity.this, listview1.getItemAtPosition(position) + " Due:" + homework.get(position).meetingDate, Toast.LENGTH_LONG).show();
                //That is a testing to see if the Android Studio is sending the news to the repository
                editItem(view,homework.get(position).Id, homework.get(position).itemName,homework.get(position).itemType,
                        homework.get(position).meetingLocation , homework.get(position).meetingDate, homework.get(position).meetingTime );

            }
        });

        // for doc appointments list
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // textview2.setText(listview2.getItemAtPosition(position) + " at: " + doctorAppointment.get(position).meetingDate );
                //That is a testing to see if the Android Studio is sending the news to the repository
                editItem(view,doctorAppointment.get(position).Id, doctorAppointment.get(position).itemName,doctorAppointment.get(position).itemType,
                        doctorAppointment.get(position).meetingLocation , doctorAppointment.get(position).meetingDate, doctorAppointment.get(position).meetingTime );
            }
        });

        // for meeting
        listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //textview3.setText(listview3.getItemAtPosition(position) + " at: " + meeting.get(position).meetingDate +
               //         " at: " + meeting.get(position).meetingTime);
                //That is a testing to see if the Android Studio is sending the news to the repository
                editItem(view,meeting.get(position).Id, meeting.get(position).itemName,meeting.get(position).itemType,
                        meeting.get(position).meetingLocation , meeting.get(position).meetingDate, meeting.get(position).meetingTime );
            }
        });
    }
    /** Called when the user taps an item */
    public void editItem(View view ,int Id, String itemName, int itemType, String meetingLocation , String meetingDate, String meetingTime ) {
        Intent intent = new Intent(this, edit_activity.class);
        intent.putExtra(ITEM_NAME, itemName);
        intent.putExtra(LOCATION, meetingLocation);
        intent.putExtra(String.valueOf(ITEM_TYPE), itemType);
        intent.putExtra(String.valueOf(ID), Id);
        intent.putExtra(MEETING_DATE, meetingDate);
        intent.putExtra(MEETING_TIME, meetingTime);
        startActivity(intent);
        finish();
    }
    public void Add(View view) {
        Intent AddActivity = new Intent(this, create_activity.class);
        startActivity(AddActivity);
        finish();

    }
}