package com.example.juan.flashcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    private static final String ERROR_MESSAGE = "Must enter both Question and Answer!";
    protected EditText editQuestionView;
    protected EditText editAnswerView;
    /*
    protected EditText editChoiceOneView;
    protected EditText editChoiceTwoView;
    protected EditText editChoiceThreeView;
    */

    protected String question;
    protected String answer;
    protected Toast error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        //populate text fields if edit button was clicked
        if ( getIntent().getIntExtra(MainActivity.requestCodeKey, MainActivity.ADD_CARD_REQUEST_CODE) ==
                MainActivity.EDIT_CARD_REQUEST_CODE ) {
            handleEdits();
        }

        //don't update question and answer fields if cancel is clicked
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

        handleSave();
    }

    private void handleEdits() {
        editQuestionView = findViewById(R.id.editQuestion);
        editAnswerView = findViewById(R.id.editAnswer);

        question = getIntent().getStringExtra(MainActivity.questionKey);
        answer = getIntent().getStringExtra(MainActivity.answerKey);
        editQuestionView.setText(question, TextView.BufferType.EDITABLE);
        editAnswerView.setText(answer, TextView.BufferType.EDITABLE);

        //move cursor to end of text
        editQuestionView.setSelection(question.length());
        editAnswerView.setSelection(answer.length());
    }

    private void handleSave() {
        //update question and answer if save is clicked
        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editQuestionView = findViewById(R.id.editQuestion);
                editAnswerView = findViewById(R.id.editAnswer);
                question = editQuestionView.getText().toString();
                answer = editAnswerView.getText().toString();

                //display toast error message if input empty
                if (question.length() < 1 || answer.length() < 1) {
                    error = Toast.makeText(getApplicationContext(), ERROR_MESSAGE, Toast.LENGTH_SHORT);
                    View toast = error.getView();
                    toast.setBackgroundColor(getResources().getColor(R.color.toast, null));
                    error.show();
                }
                else {
                    Intent data = new Intent();
                    data.putExtra(MainActivity.questionKey, question);
                    data.putExtra(MainActivity.answerKey, answer);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }
}
