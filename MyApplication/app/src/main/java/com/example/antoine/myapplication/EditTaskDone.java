package com.example.antoine.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class EditTaskDone extends AppCompatActivity {
    private String messageText;
    private String contentText;
    private String dateText;
    private int position;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_edit_done_layout);
        Intent intent = getIntent();
        contentText = intent.getStringExtra(IntentCodes.INTENT_CONTENT_DATA);
        messageText = intent.getStringExtra(IntentCodes.INTENT_MESSAGE_DATA);
        dateText = intent.getStringExtra(IntentCodes.INTENT_DATE_DATA);
        position = intent.getIntExtra(IntentCodes.INTENT_ITEM_POSITION, -1);
        EditText messageData = (EditText)findViewById(R.id.title);
        EditText contentData = (EditText)findViewById(R.id.content);
        TextView dateData = (TextView)findViewById(R.id.date);
        messageData.setText(messageText);
        contentData.setText(contentText);
        dateData.setText(dateText);
        mDisplayDate = (TextView)findViewById(R.id.date);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(EditTaskDone.this,
                        R.style.Theme_AppCompat_DayNight_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                Log.d("DEBUG", "onDateSet: " + day + "/" + month  + "/" + year);
                String date = day + "/" + month  + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }

    public void deleteButtonClick(View v) {
        String changedMessageText = ((EditText)findViewById(R.id.title)).getText().toString();
        String changedContentText = ((EditText)findViewById(R.id.content)).getText().toString();
        String changedDateText = ((TextView)findViewById(R.id.date)).getText().toString();
        Intent intent = new Intent();
        intent.putExtra(IntentCodes.INTENT_CHANGED_MESSAGE, changedMessageText);
        intent.putExtra(IntentCodes.INTENT_CHANGED_CONTENT, changedContentText);
        intent.putExtra(IntentCodes.INTENT_CHANGED_DATE, changedDateText);
        intent.putExtra(IntentCodes.INTENT_ITEM_POSITION, position);
        setResult(IntentCodes.INTENT_RESULT_CODE_FIVE, intent);
        finish();
    }
}
