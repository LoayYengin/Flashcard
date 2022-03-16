package com.example.flashcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {
    public static final String QUESTION_KEY = "question_key"; // since both MainActivity and AddCardActivity need to access the key, can declare as static variables
    public static String ANSWER_KEY = "answer_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        ImageView cancelButton = findViewById(R.id.cancel_imageView);
        ImageView saveButton = findViewById(R.id.save_button_imageView);

        EditText questionField = findViewById(R.id.enter_question_editText);
        EditText answerField = findViewById(R.id.enter_answer_editText);

        String questionString = getIntent().getStringExtra(QUESTION_KEY); // if activity launched with Intent access the passed data
        String answerString = getIntent().getStringExtra(ANSWER_KEY);

        cancelButton.setOnClickListener(view -> {
            finish();
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(); // this is where we will put our data
                String inputQuestion = ((EditText) findViewById(R.id.enter_question_editText)).getText().toString(); // this gets the question text input from the editText view
                String inputAnswer = ((EditText) findViewById(R.id.enter_answer_editText)).getText().toString(); // this gets the answer text input from the editText view

                data.putExtra(QUESTION_KEY, inputQuestion); // puts a string into Intent, with the key "QUESTION_KEY"
                data.putExtra(ANSWER_KEY, inputAnswer);
                setResult(RESULT_OK, data); // set result code and bundle data for response

                if (inputQuestion.equals("") || inputAnswer.equals("")) {
                    Toast.makeText(AddCardActivity.this, "Must enter both Question and Answer", Toast.LENGTH_SHORT).show();
                } else {
                    finish(); // closes this activity and pass data to the original activity that launched this activity
                }

            }

        });


        if (questionString != null && answerString != null) {
            questionField.setText(questionString);
            answerField.setText(answerString);
        }

    }

}