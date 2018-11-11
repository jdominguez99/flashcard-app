package com.example.juan.flashcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int questionId = R.id.flashcard_question;
    private static final int answerId = R.id.flashcard_answer;
    private static final int choiceOneId = R.id.choice1;
    private static final int choiceTwoId = R.id.choice2;
    private static final int choiceThreeId = R.id.choice3;
    private static final int addButtonId = R.id.add;
    private static final int editButtonId = R.id.edit;
    private static final int nextButtonId = R.id.next;
    private static final int deleteButtonId = R.id.delete_card;
    private static final int prevButtonId = R.id.previous;
    protected static final String questionKey = "question";
    protected static final String answerKey = "answer";
    protected static final String requestCodeKey = "requestCode";
    protected static final int ADD_CARD_REQUEST_CODE = 100;
    protected static final int EDIT_CARD_REQUEST_CODE = 200;

    private boolean displayingAnswers = false;
    private TextView questionView;
    private TextView answerView;
    private FlashcardDatabase flashcardDatabase;
    private List<Flashcard> allFlashCards;
    private int databaseSize;
    private int currDisplayIdx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up the database
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashCards = flashcardDatabase.getAllCards();
        databaseSize = allFlashCards.size();
        // check for existing flashcards before setting q/a to default
        if ( databaseSize > 0 ) {
            questionView = findViewById(questionId);
            answerView = findViewById(answerId);
            //update this later to a random question and answer
            questionView.setText(allFlashCards.get(0).getQuestion());
            answerView.setText(allFlashCards.get(0).getAnswer());
        }

        setQuestionListener();
        setChoiceListeners();
        setResetListener();
        setDisplayListener();
        setAddButtonListener();
        setEditButtonListener();
        setPrevButtonListener();
        setNextButtonListener();
        setDeleteButtonListener();

    }

    private void setQuestionListener() {
        findViewById(questionId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility();
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
                toggleVisibility();
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
                //if currently displaying answers set to invisible
                if (displayingAnswers) {
                    findViewById(R.id.toggle).setBackgroundResource(R.drawable.display);
                    findViewById(choiceOneId).setVisibility(View.INVISIBLE);
                    findViewById(choiceTwoId).setVisibility(View.INVISIBLE);
                    findViewById(choiceThreeId).setVisibility(View.INVISIBLE);
                    displayingAnswers = false;
                }
                else { //set to visible if currently not displaying answers
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
                MainActivity.this.startActivityForResult(intent, ADD_CARD_REQUEST_CODE);
            }
        });
    }

    private void setEditButtonListener() {
        findViewById(editButtonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                questionView = findViewById(questionId);
                answerView = findViewById(answerId);
                String question = questionView.getText().toString();
                String answer = answerView.getText().toString();
                intent.putExtra(questionKey, question);
                intent.putExtra(answerKey, answer);
                MainActivity.this.startActivityForResult(intent, EDIT_CARD_REQUEST_CODE);
            }
        });
    }

    private void setPrevButtonListener() {
        findViewById(prevButtonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currDisplayIdx--;
                if (currDisplayIdx < 0) {
                    currDisplayIdx = databaseSize - 1;
                }
                System.out.format("Showing current index: %d%n", currDisplayIdx);
                questionView = findViewById(questionId);
                answerView = findViewById(answerId);

                questionView.setText(allFlashCards.get(currDisplayIdx).getQuestion());
                answerView.setText(allFlashCards.get(currDisplayIdx).getAnswer());

                if (answerView.getVisibility() == View.VISIBLE) {
                    toggleVisibility();
                }
            }
        });
    }

    private void setNextButtonListener() {
        findViewById(nextButtonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currDisplayIdx++;
                // check if we're viewing the last flashcard in database
                if (currDisplayIdx > databaseSize - 1 ) {
                    currDisplayIdx = 0;
                }

                if ( databaseSize > 0 ) {

                    questionView = findViewById(questionId);
                    answerView = findViewById(answerId);

                    questionView.setText(allFlashCards.get(currDisplayIdx).getQuestion());
                    answerView.setText(allFlashCards.get(currDisplayIdx).getAnswer());

                    //if looking at current answer, set the next
                    // answer to invisible and display the question
                    if (answerView.getVisibility() == View.VISIBLE) {
                        toggleVisibility();
                    }
                }
            }
        });
    }

    private void setDeleteButtonListener() {
        findViewById(deleteButtonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionView = findViewById(questionId);
                answerView = findViewById(answerId);

                flashcardDatabase.deleteCard(questionView.getText().toString());
                allFlashCards = flashcardDatabase.getAllCards();
                databaseSize = allFlashCards.size();
                currDisplayIdx--;

                //display a default state if cards depleted
                if (allFlashCards.isEmpty()) {
                    questionView.setText(R.string.defaultQ);

                    answerView.setText(R.string.defaultA);

                }
                else {
                    if (currDisplayIdx < 0) {
                        currDisplayIdx = databaseSize - 1;
                    }
                    questionView.setText(allFlashCards.get(currDisplayIdx).getQuestion());
                    answerView.setText(allFlashCards.get(currDisplayIdx).getAnswer());

                }
                // toggle visibility if currently looking at answer
                if (answerView.getVisibility() == View.VISIBLE) {
                    toggleVisibility();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String question = data.getExtras().getString(questionKey);
        String answer = data.getExtras().getString(answerKey);
        questionView = findViewById(questionId);
        answerView = findViewById(answerId);

        //handle new cards
        if (requestCode == ADD_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
            questionView.setText(question);
            answerView.setText(answer);

            //add the new card to the database
            flashcardDatabase.insertCard(new Flashcard(question, answer));
            allFlashCards = flashcardDatabase.getAllCards();
            databaseSize = allFlashCards.size();
            currDisplayIdx = databaseSize - 1;
        }
        //handle edits
        else if (requestCode == EDIT_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
            questionView.setText(question);
            answerView.setText(answer);
            Flashcard card = allFlashCards.get(currDisplayIdx);
            card.setQuestion(question);
            card.setAnswer(answer);
            flashcardDatabase.updateCard(card);
        }
    }

    // toggles visibility of the question and answer fields
    private void toggleVisibility() {
        if (findViewById(questionId).getVisibility() == View.VISIBLE) {
            findViewById(questionId).setVisibility(View.INVISIBLE);
            findViewById(answerId).setVisibility(View.VISIBLE);
        }
        else {
            findViewById(questionId).setVisibility(View.VISIBLE);
            findViewById(answerId).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra(requestCodeKey, requestCode);
        super.startActivityForResult(intent, requestCode);
    }
}
