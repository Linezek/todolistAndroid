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
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ListView listView;
    private Spinner spinner;
    private ArrayList<String> titleList;
    private ArrayList<String> contentList;
    private ArrayList<String> dateList;
    private ArrayList<String> titleListFinish;
    private ArrayList<String> contentListFinish;
    private ArrayList<String> dateListFinish;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayAdapter<String> categoryAdapter;
    private String messageText;
    private String contentText;
    private String dateText;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        spinner = (Spinner)findViewById(R.id.Spinner);
        titleList = new ArrayList<>();
        contentList = new ArrayList<>();
        dateList = new ArrayList<>();
        titleListFinish = new ArrayList<>();
        contentListFinish = new ArrayList<>();
        dateListFinish = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList);
        categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.category));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(categoryAdapter);
        spinner.setOnItemSelectedListener(this);
        listView.setAdapter(arrayAdapter);
        getDataWhenOpeningApp("titles.txt", titleList);
        getDataWhenOpeningApp("content.txt", contentList);
        getDataWhenOpeningApp("dates.txt", dateList);
        getDataWhenOpeningApp("titlesFinish.txt", titleListFinish);
        getDataWhenOpeningApp("contentFinish.txt", contentListFinish);
        getDataWhenOpeningApp("datesFinish.txt", dateListFinish);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).toString().equals("To Do")) {
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList);
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
        } else {
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleListFinish);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, EditTaskDone.class);
                    intent.putExtra(IntentCodes.INTENT_MESSAGE_DATA, titleListFinish.get(i).toString());
                    intent.putExtra(IntentCodes.INTENT_CONTENT_DATA, contentListFinish.get(i).toString());
                    intent.putExtra(IntentCodes.INTENT_DATE_DATA, dateListFinish.get(i).toString());
                    intent.putExtra(IntentCodes.INTENT_ITEM_POSITION, i);
                    startActivityForResult(intent, IntentCodes.INTENT_REQUEST_CODE_TWO);
                }
            });
        }
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        recordDataWhenClosingApp("titlesFinish.txt", titleListFinish);
        recordDataWhenClosingApp("contentFinish.txt", contentListFinish);
        recordDataWhenClosingApp("datesFinish.txt", dateListFinish);
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
            Log.d("DEBUG", "IN INTENT_REQUEST_CODE");
            messageText = data.getStringExtra(IntentCodes.INTENT_MESSAGE_FIELD);
            contentText = data.getStringExtra(IntentCodes.INTENT_CONTENT_FIELD);
            dateText = data.getStringExtra(IntentCodes.INTENT_DATE_FIELD);
            titleList.add(messageText);
            contentList.add(contentText);
            dateList.add(dateText);
        } else if (resultCode == IntentCodes.INTENT_REQUEST_CODE_TWO) {
            Log.d("DEBUG", "IN INTENT_REQUEST_CODE_TWO ");
            messageText = data.getStringExtra(IntentCodes.INTENT_CHANGED_MESSAGE);
            contentText = data.getStringExtra(IntentCodes.INTENT_CHANGED_CONTENT);
            dateText = data.getStringExtra(IntentCodes.INTENT_CHANGED_DATE);
            position = data.getIntExtra(IntentCodes.INTENT_ITEM_POSITION, -1);
            titleList.remove(position);
            contentList.remove(position);
            dateList.remove(position);
            titleList.add(position, messageText);
            contentList.add(position, contentText);
            dateList.add(position, dateText);
        } else if (resultCode == IntentCodes.INTENT_REQUEST_CODE_THREE) {
            Log.d("DEBUG", "IN INTENT_REQUEST_CODE_THREE ");
            messageText = data.getStringExtra(IntentCodes.INTENT_CHANGED_MESSAGE);
            contentText = data.getStringExtra(IntentCodes.INTENT_CHANGED_CONTENT);
            dateText = data.getStringExtra(IntentCodes.INTENT_CHANGED_DATE);
            position = data.getIntExtra(IntentCodes.INTENT_ITEM_POSITION, -1);
            titleListFinish.add(position, messageText);
            contentListFinish.add(position, contentText);
            dateListFinish.add(position, dateText);
            titleList.remove(position);
            contentList.remove(position);
            dateList.remove(position);
        } else if (resultCode == IntentCodes.INTENT_REQUEST_CODE_FOUR) {
            Log.d("DEBUG", "IN INTENT_REQUEST_CODE_THREE ");
            messageText = data.getStringExtra(IntentCodes.INTENT_CHANGED_MESSAGE);
            contentText = data.getStringExtra(IntentCodes.INTENT_CHANGED_CONTENT);
            dateText = data.getStringExtra(IntentCodes.INTENT_CHANGED_DATE);
            position = data.getIntExtra(IntentCodes.INTENT_ITEM_POSITION, -1);
            titleList.remove(position);
            contentList.remove(position);
            dateList.remove(position);
            arrayAdapter.notifyDataSetChanged();
        } else if (resultCode == IntentCodes.INTENT_REQUEST_CODE_FIVE) {
            Log.d("DEBUG", "IN INTENT_REQUEST_CODE_THREE ");
            messageText = data.getStringExtra(IntentCodes.INTENT_CHANGED_MESSAGE);
            contentText = data.getStringExtra(IntentCodes.INTENT_CHANGED_CONTENT);
            dateText = data.getStringExtra(IntentCodes.INTENT_CHANGED_DATE);
            position = data.getIntExtra(IntentCodes.INTENT_ITEM_POSITION, -1);
            titleListFinish.remove(position);
            contentListFinish.remove(position);
            dateListFinish.remove(position);
            arrayAdapter.notifyDataSetChanged();
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
