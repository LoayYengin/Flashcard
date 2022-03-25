package com.example.flashcard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final int ADD_CARD_REQUEST_CODE = 100;
    private final int EDIT_CARD_REQUEST_CODE = 200;

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int cardIndex = 0;
    Flashcard cardToEdit;
    boolean isShowingAnswers = true;

    TextView flashcardQuestion;
    TextView flashcardAnswer;
    TextView wrongAnswer1;
    TextView wrongAnswer2;
    TextView correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get question and answer views from xml
        flashcardQuestion = findViewById(R.id.flashcard_question_textview);
        flashcardAnswer = findViewById(R.id.flashcard_answer_textview);

        wrongAnswer1 = findViewById(R.id.wrong_answer1_textview);
        wrongAnswer2 = findViewById(R.id.wrong_answer2_textview);
        correctAnswer = findViewById(R.id.correct_answer_textview);
        ImageView toggleChoices = findViewById(R.id.toggle_choices_visibility_imageView);

        ImageView addButton = findViewById(R.id.add_card_imageView);
        ImageView editButton = findViewById(R.id.edit_card_imageView);
        ImageView nextButton = findViewById(R.id.next_card_imageView);
        ImageView deleteButton = findViewById(R.id.trash_card_imageView);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext()); // initialize db (or 'this' instead of getApplicationsContext())
        allFlashcards = flashcardDatabase.getAllCards(); // retrieve all flashcard objects from db and store in list of flashcards

        // if flashcard list has cards in it set the question and answer text to be displayed
        if (allFlashcards != null && allFlashcards.size() > 0) {
            System.out.println(allFlashcards.size());
            flashcardQuestion.setText(allFlashcards.get(0).getQuestion());
            flashcardAnswer.setText(allFlashcards.get(0).getAnswer());
            wrongAnswer1.setText(allFlashcards.get(0).getWrongAnswer1());
            wrongAnswer2.setText(allFlashcards.get(0).getWrongAnswer2());
            correctAnswer.setText(allFlashcards.get(0).getAnswer());
        } else {
            flashcardDatabase.initFirstCard();
        }

        /*
         * Lambda function can be used instead of initializing a new View.OnClickListener() and then overriding the onCLick method
         * similar to methods but don't need a name and can be implemented in the body of a method
         **/

        // hide Question and display Answer on click
        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("yengin", "onCLick method was clicked");
//                Toast.makeText(MainActivity.this, "Display answer!", Toast.LENGTH_SHORT).show();
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);
            }
        });
        // using lambda:
//        flashcardQuestion.setOnClickListener(view -> {
//            Log.i("yengin", "onCLick method was clicked");
////            Toast.makeText(MainActivity.this, "Display answer!", Toast.LENGTH_SHORT).show();
//            flashcardQuestion.setVisibility(View.INVISIBLE);
//            flashcardAnswer.setVisibility(View.VISIBLE);
//        });
        //******************************************


        // hide answer and display question on click
        flashcardAnswer.setOnClickListener(view -> {
            Log.i("yengin", "onCLick method was clicked");
//                Toast.makeText(MainActivity.this, "Display question!", Toast.LENGTH_SHORT).show();
            showQuestion();
        });

        wrongAnswer1.setOnClickListener(view -> {
            wrongAnswer1.setBackgroundColor(getResources().getColor(R.color.red_200, null));
            wrongAnswer1.setTextColor(getResources().getColor(R.color.black));

            correctAnswer.setBackgroundColor(getResources().getColor(R.color.green_200, null));
            correctAnswer.setTextColor(getResources().getColor(R.color.black));
        });

        wrongAnswer2.setOnClickListener(view -> {
            wrongAnswer2.setBackgroundColor(getResources().getColor(R.color.red_200, null));
            wrongAnswer2.setTextColor(getResources().getColor(R.color.black));

            correctAnswer.setBackgroundColor(getResources().getColor(R.color.green_200, null));
            correctAnswer.setTextColor(getResources().getColor(R.color.black));
        });

        correctAnswer.setOnClickListener(view -> {
            correctAnswer.setBackgroundColor(getResources().getColor(R.color.green_200, null));
            correctAnswer.setTextColor(getResources().getColor(R.color.black));
        });


        // toggle image to display/hide multiple choice answers
        toggleChoices.setOnClickListener(view -> {
            if (isShowingAnswers) {
                toggleChoices.setImageResource(R.drawable.ic_iconmonstr_eye_8);
                wrongAnswer1.setVisibility(View.INVISIBLE);
                wrongAnswer2.setVisibility(View.INVISIBLE);
                correctAnswer.setVisibility(View.INVISIBLE);
            } else {
                toggleChoices.setImageResource(R.drawable.ic_iconmonstr_eye_6);
                wrongAnswer1.setVisibility(View.VISIBLE);
                wrongAnswer2.setVisibility(View.VISIBLE);
                correctAnswer.setVisibility(View.VISIBLE);
            }
            isShowingAnswers = !isShowingAnswers;
        });

        // navigate to AddCardActivity via addCard button
        addButton.setOnClickListener(view -> {
            Intent toAddCard = new Intent(MainActivity.this, AddCardActivity.class);
            if (toAddCard != null) {
//                MainActivity.this.startActivity(toAddCard);
                startActivityForResult(toAddCard, 100); // create an activity with with the "Intent" of expecting data in return from AddCardActivity
            }
        });

        // navigate to AddCardActivity via editCard button
        editButton.setOnClickListener(view -> {
            // get current card object from card list
            for (int i = 0; i < allFlashcards.size(); i++) {
                if (allFlashcards.get(i).getQuestion().equals(flashcardQuestion.getText().toString())) {
                    cardToEdit = allFlashcards.get(i);
                }
            }

            Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
            if (intent != null) {
                String currQuestion = flashcardQuestion.getText().toString(); // this gets the current question text from the editText view
                String currAnswer = flashcardAnswer.getText().toString(); // this gets the current answer text from the editText view

                // multiple choice answer texts
                String wrongChoice1 = wrongAnswer1.getText().toString();
                String wrongChoice2 = wrongAnswer2.getText().toString();

                intent.putExtra(AddCardActivity.QUESTION_KEY, currQuestion); // puts a string into Intent, with the key "QUESTION_KEY"
                intent.putExtra(AddCardActivity.ANSWER_KEY, currAnswer);
                intent.putExtra(AddCardActivity.WRONG_ANSWER1_KEY, wrongChoice1);
                intent.putExtra(AddCardActivity.WRONG_ANSWER2_KEY, wrongChoice2);

                startActivityForResult(intent, EDIT_CARD_REQUEST_CODE); // create an activity with with the "Intent" of expecting data in return from AddCardActivity
            }

        });

        nextButton.setOnClickListener(view -> {
            int randomIndex = getRandomNumber(0, allFlashcards.size() - 1);

            while (allFlashcards.size() != 1 && cardIndex == randomIndex) {
                randomIndex = getRandomNumber(0, allFlashcards.size() - 1);
            }

            cardIndex = randomIndex;

            Log.i("yengin", "cardIndex: ");
            System.out.println("cardIndex: " + cardIndex);

            if (cardIndex >= allFlashcards.size()) {
                Snackbar.make(view, "This was the last card! Going back to the start", Snackbar.LENGTH_SHORT).show();
                cardIndex = 0; // reset card index
            }

            try {
                updateCurrCardDisplay(cardIndex);

                // if answer side was shown, make invisible and display question
                showQuestion();
                resetMultipleChoice();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Accessed an index out of bounds of allFlashcards");
            }

        });

        deleteButton.setOnClickListener(view -> {
            flashcardDatabase.deleteCard(flashcardQuestion.getText().toString());
            allFlashcards = flashcardDatabase.getAllCards(); // update list after deleting a card
            cardIndex--;

            if (allFlashcards.size() > 0) {
                // update card being displayed to
                try {
//                    Flashcard currCard = allFlashcards.get(cardIndex);
//                    flashcardQuestion.setText(currCard.getQuestion());
//                    flashcardAnswer.setText(currCard.getAnswer());
//                    wrongAnswer1.setText(currCard.getWrongAnswer1());
//                    wrongAnswer2.setText(currCard.getWrongAnswer2());
//                    correctAnswer.setText(currCard.getAnswer());
                    updateCurrCardDisplay(cardIndex);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Accessed an index out of bounds of allFlashcards");
                }
            } else {
                // display empty state
                cardIndex = 0;
                String emptyQuestion = "Add a new flashcard!";
                String emptyAnswer = "Press the '+' button";

                flashcardQuestion.setText(emptyQuestion);
                flashcardAnswer.setText(emptyAnswer);
                wrongAnswer1.setText("");
                wrongAnswer2.setText("");
                correctAnswer.setText("");
            }

            // if answer side was shown, make invisible and display question
            showQuestion();

            System.out.println("cardIndex: " + cardIndex);
        });
    }

    /*
    startActivityForResult() and onActivityResult() allows us to start another activity and receive a result back
     If the created activity passes data back with an Intent onActivityResult() handles this data.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) { // check if there's an Intent
            // get data
            String questionString = data.getExtras().getString(AddCardActivity.QUESTION_KEY); // key String needs to match key used in the intent from AddCard
            String answerString = data.getExtras().getString(AddCardActivity.ANSWER_KEY);
            String wrongAnswer1String = data.getExtras().getString(AddCardActivity.WRONG_ANSWER1_KEY);
            String wrongAnswer2String = data.getExtras().getString(AddCardActivity.WRONG_ANSWER2_KEY);

            // need to set as global textview var
            flashcardQuestion.setText(questionString);
            flashcardAnswer.setText(answerString);
            wrongAnswer1.setText(wrongAnswer1String);
            wrongAnswer2.setText(wrongAnswer2String);
            correctAnswer.setText(answerString);

            if (requestCode == ADD_CARD_REQUEST_CODE) { // requestCode needs to match the 100 used in startActivityForResult
                // save new card to db after its been added
                flashcardDatabase.insertCard(new Flashcard(questionString, answerString, wrongAnswer1String, wrongAnswer2String));
                allFlashcards = flashcardDatabase.getAllCards();

                Snackbar.make(findViewById(R.id.flashcard_question_textview),
                        "Card successfully created",
                        Snackbar.LENGTH_SHORT)
                        .show();
            } else if (requestCode == EDIT_CARD_REQUEST_CODE) {
                // set TextViews to show EDITED question, correct answer, and wrong answers
                cardToEdit.setQuestion(questionString);
                cardToEdit.setAnswer(answerString);
                cardToEdit.setWrongAnswer1(wrongAnswer1String);
                cardToEdit.setWrongAnswer2(wrongAnswer2String);
                // set TextViews to wrong answers


                flashcardDatabase.updateCard(cardToEdit);

                Snackbar.make(findViewById(R.id.flashcard_question_textview),
                        "Card successfully edited",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void showQuestion() {
        flashcardQuestion.setVisibility(View.VISIBLE);
        flashcardAnswer.setVisibility(View.INVISIBLE);
    }

    private void resetMultipleChoice() {
        wrongAnswer1.setBackgroundColor(getResources().getColor(R.color.gray_800, null));
        wrongAnswer1.setTextColor(getResources().getColor(R.color.gray_200));

        wrongAnswer2.setBackgroundColor(getResources().getColor(R.color.gray_800, null));
        wrongAnswer2.setTextColor(getResources().getColor(R.color.gray_200));

        correctAnswer.setBackgroundColor(getResources().getColor(R.color.gray_800, null));
        correctAnswer.setTextColor(getResources().getColor(R.color.gray_200));
    }

    private void updateCurrCardDisplay(int currCardIndex) {
        Flashcard currCard = allFlashcards.get(currCardIndex);
        flashcardQuestion.setText(currCard.getQuestion());
        flashcardAnswer.setText(currCard.getAnswer());
        wrongAnswer1.setText(currCard.getWrongAnswer1());
        wrongAnswer2.setText(currCard.getWrongAnswer2());
        correctAnswer.setText(currCard.getAnswer());
    }

    private int getRandomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}