package com.example.curso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textview1, textview2, textview3, textview4;
    private ListView listview1, listview2, listview3, listview4;

    private String items_name [] = {"Read Scripture", "Make Assignment", "Call bank", "Finish app"};
    private String items_date [] = {"17/06/2021", "22/06/2021", "25/07/2021", "01/08/2021"};
    private String doc_name [] = {"Doctor Allcome", "Doctor Pleaseno"};
    private String doc_date [] = {"25/12/2021", "16/07/2021"};
    private String meeting_name [] = {"Scrum", "Cry Session", "Unemployment Office"};
    private String meeting_date [] = {"24/06/2021", "22/06/2021", "25/06/2021"};
    private String meeting_time [] = {"20:00", "16:19", "09:00"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        textview1 = (TextView)findViewById(R.id.textView1);
        textview2 = (TextView)findViewById(R.id.textView2);
        textview3 = (TextView)findViewById(R.id.textView3);
        textview4 = (TextView)findViewById(R.id.textView4);
        listview1 = (ListView)findViewById(R.id.listView1);
        listview2 = (ListView)findViewById(R.id.listView2);
        listview3 = (ListView)findViewById(R.id.listView3);
        listview4 = (ListView)findViewById(R.id.listView4);

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_homework, items_name);
        listview1.setAdapter(adapter);


        ArrayAdapter <String> adapter2 = new ArrayAdapter<String>(this, R.layout.list_item_2, doc_name);
        listview2.setAdapter(adapter2);

        ArrayAdapter <String> adapter3 = new ArrayAdapter<String>(this, R.layout.list_item_2, meeting_name);
        listview3.setAdapter(adapter3);

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textview1.setText(listview1.getItemAtPosition(position) + " Due:" + items_date[position] );
                //That is a testing to see if the Android Studio is sending the news to the repository


            }
        });

        // for doc appointments list
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textview2.setText(listview2.getItemAtPosition(position) + " at: " + doc_date[position] );
                //That is a testing to see if the Android Studio is sending the news to the repository

            }
        });

        // for meeting
        listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textview3.setText(listview3.getItemAtPosition(position) + " at: " + meeting_date[position] +
                        " at: " + meeting_time[position]);
                //That is a testing to see if the Android Studio is sending the news to the repository

            }
        });
    }

    public void Add(View view) {
        Intent AddActivity = new Intent(this, edit_activity.class);
        startActivity(AddActivity);

    }
}