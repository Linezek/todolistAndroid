package com.example.antoine.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> titleList;
    private ArrayList<String> contentList;
    private ArrayList<String> dateList;
    private ArrayAdapter<String> arrayAdapter;
    private String messageText;
    private String contentText;
    private String dateText;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        titleList = new ArrayList<>();
        contentList = new ArrayList<>();
        dateList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EditTask.class);
                intent.putExtra(IntentCodes.INTENT_MESSAGE_DATA, titleList.get(i).toString());
                intent.putExtra(IntentCodes.INTENT_CONTENT_DATA, contentList.get(i).toString());
                intent.putExtra(IntentCodes.INTENT_DATE_DATA, dateList.get(i).toString());
                intent.putExtra(IntentCodes.INTENT_ITEM_POSITION, i);
                startActivityForResult(intent, IntentCodes.INTENT_REQUEST_CODE_TWO);
            }
        });
        getDataWhenOpeningApp("titles.txt", titleList);
        getDataWhenOpeningApp("content.txt", contentList);
        getDataWhenOpeningApp("dates.txt", dateList);
    }

    private void getDataWhenOpeningApp(String name, ArrayList<String> list) {
        try {
            Scanner sc = new Scanner(openFileInput(name));
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                list.add(data);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void recordDataWhenClosingApp(String name, ArrayList<String> list) {
        try {
            PrintWriter pw = new PrintWriter(openFileOutput(name, Context.MODE_PRIVATE));
            for (String data : list) {
                pw.println(data);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("DEBUG:", "BackPressed");
        recordDataWhenClosingApp("titles.txt", titleList);
        recordDataWhenClosingApp("content.txt", contentList);
        recordDataWhenClosingApp("dates.txt", dateList);
        finish();
    }

    public void onClick(View v)  {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, EditField.class);
        startActivityForResult(intent, IntentCodes.INTENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == IntentCodes.INTENT_REQUEST_CODE) {
            messageText = data.getStringExtra(IntentCodes.INTENT_MESSAGE_FIELD);
            contentText = data.getStringExtra(IntentCodes.INTENT_CONTENT_FIELD);
            dateText = data.getStringExtra(IntentCodes.INTENT_DATE_FIELD);
            titleList.add(messageText);
            contentList.add(contentText);
            dateList.add(dateText);
            arrayAdapter.notifyDataSetChanged();
        } else if (resultCode == IntentCodes.INTENT_REQUEST_CODE_TWO) {
            messageText = data.getStringExtra(IntentCodes.INTENT_CHANGED_MESSAGE);
            contentText = data.getStringExtra(IntentCodes.INTENT_CHANGED_CONTENT);
            dateText = data.getStringExtra(IntentCodes.INTENT_CHANGED_DATE);
            position = data.getIntExtra(IntentCodes.INTENT_ITEM_POSITION, -1);
            titleList.remove(position);
            contentList.remove(position);
            dateList.remove(position);
            Log.d("DEBUG:", "INTENT_REQUEST_CODE_TWO");
            Log.d("DEBUG", messageText);
            titleList.add(position, messageText);
            contentList.add(position, contentText);
            dateList.add(position, dateText);
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
