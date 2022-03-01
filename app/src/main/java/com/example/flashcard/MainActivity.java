package com.example.flashcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean isShowingAnswers = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get question and answer views from xml
        TextView flashcardQuestion = findViewById(R.id.flashcard_question_textview);
        TextView flashcardAnswer = findViewById(R.id.flashcard_answer_textview);
        TextView wrongAnswer1 = findViewById(R.id.wrong_answer1_textview);
        TextView wrongAnswer2 = findViewById(R.id.wrong_answer2_textview);
        TextView correctAnswer = findViewById(R.id.correct_answer_textview);
        ImageView toggleChoices = findViewById(R.id.toggle_choices_visibility_imageView);


        // hide Question and display Answer on click
        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("yengin", "onCLick method was clicked");
                Toast.makeText(MainActivity.this, "Display answer!", Toast.LENGTH_SHORT).show();
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);
            }
        });

        // hide answer and display question on click
        flashcardAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("yengin", "onCLick method was clicked");
                Toast.makeText(MainActivity.this, "Display question!", Toast.LENGTH_SHORT).show();
                flashcardQuestion.setVisibility(View.VISIBLE);
                flashcardAnswer.setVisibility(View.INVISIBLE);
            }
        });

        wrongAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongAnswer1.setBackgroundColor(getResources().getColor(R.color.red_200, null));
                wrongAnswer1.setTextColor(getResources().getColor(R.color.black));

                correctAnswer.setBackgroundColor(getResources().getColor(R.color.green_200, null));
                correctAnswer.setTextColor(getResources().getColor(R.color.black));
            }
        });

        wrongAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongAnswer2.setBackgroundColor(getResources().getColor(R.color.red_200, null));
                wrongAnswer2.setTextColor(getResources().getColor(R.color.black));

                correctAnswer.setBackgroundColor(getResources().getColor(R.color.green_200, null));
                correctAnswer.setTextColor(getResources().getColor(R.color.black));
            }
        });

        correctAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctAnswer.setBackgroundColor(getResources().getColor(R.color.green_200, null));
                correctAnswer.setTextColor(getResources().getColor(R.color.black));

            }
        });

        toggleChoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

    }
}