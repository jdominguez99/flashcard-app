package com.example.juan.flashcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(MainActivity.questionKey, getIntent().getStringExtra(MainActivity.questionKey));
                data.putExtra(MainActivity.answerKey, getIntent().getStringExtra(MainActivity.answerKey));
                setResult(RESULT_OK, data);
                finish();
            }
        });

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = ((EditText) findViewById(R.id.editQuestion)).getText().toString();
                String answer = ((EditText) findViewById(R.id.editAnswer)).getText().toString();
                Intent data = new Intent();
                data.putExtra(MainActivity.questionKey, question);
                data.putExtra(MainActivity.answerKey, answer);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
