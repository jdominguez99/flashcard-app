package com.example.juan.flashcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int questionId = R.id.flashcard_question;
    private static final int answerId = R.id.flashcard_answer;
    private static final int choiceOneId = R.id.choice1;
    private static final int choiceTwoId = R.id.choice2;
    private static final int choiceThreeId = R.id.choice3;
    private static final int addButtonId = R.id.add;
    private static final int editButtonId = R.id.edit;
    protected static String questionKey = "question";
    protected static String answerKey = "answer";
    protected static boolean clickedEdit = false;

    private boolean displayingAnswers = false;
    private TextView questionView;
    private TextView answerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setQuestionListener();
        setChoiceListeners();
        setResetListener();
        setDisplayListener();
        setAddButtonListener();
        setEditButtonListener();
    }
    private void setQuestionListener() {
        findViewById(questionId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(questionId).setVisibility(View.INVISIBLE);
                findViewById(answerId).setVisibility(View.VISIBLE);
                setAnswerListener();
            }
        });
    }
    //need to have way to set it as right answer later
    private void setChoiceListeners() {
        findViewById(choiceOneId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(choiceOneId).setBackgroundColor(getResources().getColor(R.color.wrong, null));
                findViewById(choiceTwoId).setBackgroundColor(getResources().getColor(R.color.right, null));
            }
        });

        findViewById(choiceTwoId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(choiceTwoId).setBackgroundColor(getResources().getColor(R.color.right, null));
            }
        });

        findViewById(choiceThreeId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(choiceThreeId).setBackgroundColor(getResources().getColor(R.color.wrong, null));
                findViewById(choiceTwoId).setBackgroundColor(getResources().getColor(R.color.right, null));
            }
        });
    }

    private void setAnswerListener() {
        findViewById(answerId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(answerId).setVisibility(View.INVISIBLE);
                findViewById(questionId).setVisibility(View.VISIBLE);
            }
        });
    }

    private void setResetListener() {
        findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(answerId).setVisibility(View.INVISIBLE);
                findViewById(questionId).setVisibility(View.VISIBLE);
                findViewById(choiceOneId).setBackgroundColor(getResources().getColor(R.color.choiceBg, null));
                findViewById(choiceTwoId).setBackgroundColor(getResources().getColor(R.color.choiceBg, null));
                findViewById(choiceThreeId).setBackgroundColor(getResources().getColor(R.color.choiceBg, null));
            }
        });
    }

    private void setDisplayListener() {
        findViewById(R.id.toggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (displayingAnswers) {
                    findViewById(R.id.toggle).setBackgroundResource(R.drawable.display);
                    findViewById(choiceOneId).setVisibility(View.INVISIBLE);
                    findViewById(choiceTwoId).setVisibility(View.INVISIBLE);
                    findViewById(choiceThreeId).setVisibility(View.INVISIBLE);
                    displayingAnswers = false;
                }
                else {
                    findViewById(R.id.toggle).setBackgroundResource(R.drawable.hide);
                    findViewById(choiceOneId).setVisibility(View.VISIBLE);
                    findViewById(choiceTwoId).setVisibility(View.VISIBLE);
                    findViewById(choiceThreeId).setVisibility(View.VISIBLE);
                    displayingAnswers = true;
                }
            }
        });
    }

    private void setAddButtonListener() {
        findViewById(addButtonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                questionView = findViewById(questionId);
                answerView = findViewById(answerId);
                String question = questionView.getText().toString();
                String answer = answerView.getText().toString();
                intent.putExtra(questionKey, question);
                intent.putExtra(answerKey, answer);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
    }

    private void setEditButtonListener() {
        findViewById(editButtonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedEdit = true;
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                questionView = findViewById(questionId);
                answerView = findViewById(answerId);
                String question = questionView.getText().toString();
                String answer = answerView.getText().toString();
                intent.putExtra(questionKey, question);
                intent.putExtra(answerKey, answer);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            String question = data.getExtras().getString(questionKey);
            String answer = data.getExtras().getString(answerKey);
            questionView = findViewById(questionId);
            answerView = findViewById(answerId);
            questionView.setText(question);
            answerView.setText(answer);
        }
    }
}
