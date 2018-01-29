package com.example.antoine.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditField extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_layout);
    }

    public void saveButtonClick(View v) {
        String messageText = ((EditText)findViewById(R.id.title)).getText().toString();
        String contentText = ((EditText)findViewById(R.id.content)).getText().toString();
        if (messageText.equals("")) {
            Toast toast= Toast.makeText(getApplicationContext(),
                    "The task can't be empty !", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(IntentCodes.INTENT_MESSAGE_FIELD, messageText);
            intent.putExtra(IntentCodes.INTENT_CONTENT_FIELD, contentText);
            setResult(IntentCodes.INTENT_RESULT_CODE, intent);
            finish();
        }
    }
}
