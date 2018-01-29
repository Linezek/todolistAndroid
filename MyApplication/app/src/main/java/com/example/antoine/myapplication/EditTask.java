package com.example.antoine.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditTask extends AppCompatActivity {
    String messageText;
    String contentText;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_layout);
        Intent intent = getIntent();
        contentText = intent.getStringExtra(IntentCodes.INTENT_CONTENT_DATA);
        messageText = intent.getStringExtra(IntentCodes.INTENT_MESSAGE_DATA);
        position = intent.getIntExtra(IntentCodes.INTENT_ITEM_POSITION, -1);
        EditText messageData = (EditText)findViewById(R.id.title);
        EditText contentData = (EditText)findViewById(R.id.content);
        messageData.setText(messageText);
        contentData.setText(contentText);
    }

    public void saveButtonClick(View v) {
        String changedMessageText = ((EditText)findViewById(R.id.title)).getText().toString();
        String changedContentText = ((EditText)findViewById(R.id.content)).getText().toString();
        Intent intent = new Intent();
        intent.putExtra(IntentCodes.INTENT_CHANGED_MESSAGE, changedMessageText);
        intent.putExtra(IntentCodes.INTENT_CHANGED_CONTENT, changedContentText);
        intent.putExtra(IntentCodes.INTENT_ITEM_POSITION, position);
        setResult(IntentCodes.INTENT_RESULT_CODE_TWO, intent);
        finish();
    }
}
