package com.example.juan.flashcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int questionId = R.id.flashcard_question;
    private static int answerId = R.id.flashcard_answer;
    private static int choiceOneId = R.id.choice1;
    private static int choiceTwoId = R.id.choice2;
    private static int choiceThreeId = R.id.choice3;
    private static int addButtonId = R.id.add;
    protected static String questionKey = "question";
    protected static String answerKey = "answer";
    private boolean displayingAnswers = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setQuestionListener();
        setChoiceListeners();
        setResetListener();
        setDisplayListener();
        setAddButtonListener();
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
                String question = ((TextView) findViewById(R.id.flashcard_question)).getText().toString();
                String answer = ((TextView) findViewById(R.id.flashcard_answer)).getText().toString();
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
            ((TextView) findViewById(questionId)).setText(question);
            ((TextView) findViewById(answerId)).setText(answer);
        }
    }
}
