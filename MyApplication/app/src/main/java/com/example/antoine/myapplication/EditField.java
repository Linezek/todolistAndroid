package com.example.antoine.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditField extends AppCompatActivity {

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_layout);
        mDisplayDate = (TextView)findViewById(R.id.date);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(EditField.this,
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

    public void saveButtonClick(View v) {
        String messageText = ((EditText)findViewById(R.id.title)).getText().toString();
        String contentText = ((EditText)findViewById(R.id.content)).getText().toString();
        String dateText = ((TextView)findViewById(R.id.date)).getText().toString();
        if (messageText.equals("") || contentText.equals("") || dateText.equals("")) {
            Toast toast= Toast.makeText(getApplicationContext(),
                    "Missing info !", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(IntentCodes.INTENT_MESSAGE_FIELD, messageText);
            intent.putExtra(IntentCodes.INTENT_CONTENT_FIELD, contentText);
            intent.putExtra(IntentCodes.INTENT_DATE_FIELD, mDisplayDate.getText());
            setResult(IntentCodes.INTENT_RESULT_CODE, intent);
            finish();
        }
    }
}
