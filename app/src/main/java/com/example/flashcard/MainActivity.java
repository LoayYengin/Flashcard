package com.example.flashcard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean isShowingAnswers = false;

    TextView flashcardQuestion;
    TextView flashcardAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get question and answer views from xml
        flashcardQuestion = findViewById(R.id.flashcard_question_textview);
        flashcardAnswer = findViewById(R.id.flashcard_answer_textview);
        TextView wrongAnswer1 = findViewById(R.id.wrong_answer1_textview);
        TextView wrongAnswer2 = findViewById(R.id.wrong_answer2_textview);
        TextView correctAnswer = findViewById(R.id.correct_answer_textview);
        ImageView toggleChoices = findViewById(R.id.toggle_choices_visibility_imageView);

        ImageView addButton = findViewById(R.id.add_card_imageView);



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
            flashcardQuestion.setVisibility(View.VISIBLE);
            flashcardAnswer.setVisibility(View.INVISIBLE);
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

        // navigate to AddCardActivity
        addButton.setOnClickListener(view -> {
            Intent toAddCard = new Intent(MainActivity.this, AddCardActivity.class);
            if (toAddCard != null) {
//                MainActivity.this.startActivity(toAddCard);
                startActivityForResult(toAddCard, 100); // create an activity with with the "Intent" of expecting data in return from AddCardActivity
            }
        });
    }

    /*
    startActivityForResult() and onActivityResult() allows us to start another activity and receive a result back
     If the created activity passes data back with an Intent onActivityResult() handles this data.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) { // requestCode needs to match the 100 used in startActivityForRestult
            // get data
            if (data != null) { // check if there's an Intent
                String questionString = data.getExtras().getString(AddCardActivity.QUESTION_KEY); // key String needs to match key used in the intent from AddCard
                String answerString = data.getExtras().getString(AddCardActivity.ANSWER_KEY);

                // need to set as global textview var
                flashcardQuestion.setText(questionString);
                flashcardAnswer.setText(answerString);
            }
        }
    }
}