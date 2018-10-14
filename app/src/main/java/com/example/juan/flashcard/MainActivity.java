package com.example.juan.flashcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static int questionId = R.id.flashcard_question;
    private static int answerId = R.id.flashcard_answer;
    private static int choiceOneId = R.id.choice1;
    private static int choiceTwoId = R.id.choice2;
    private static int choiceThreeId = R.id.choice3;
    private boolean displayingAnswers = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setQuestionListener();
        setChoiceListeners();
        setResetListener();
        setDisplayListener();
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
}
