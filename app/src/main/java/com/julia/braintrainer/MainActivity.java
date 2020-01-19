package com.julia.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timer, score, exercise, feedback;
    Button startButton;
    Button option0, option1, option2, option3;
    ConstraintLayout gameLayout;
    GridLayout optionsLayout;
    ArrayList<Integer> options;
    int locationOfCorrectOption;
    int usersScore, numberOfQuestions;

    public void start(View view){
        usersScore = 0;
        numberOfQuestions = 0;
        generateNewExercise();
        startButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        optionsLayout.setVisibility(View.VISIBLE);
        feedback.setText("");
        startTimer();
    }

    public void generateNewExercise(){
        Random random = new Random();
        int a = random.nextInt(30)+5;
        int b = random.nextInt(30)+5;
        locationOfCorrectOption = random.nextInt(4);
        options = new ArrayList<>();
        for(int i = 0; i<4; i++){
            if (i == locationOfCorrectOption) options.add(a + b);
            else {
                int incorrectOption = random.nextInt(100)+5;
                while (incorrectOption == a + b){
                    incorrectOption = random.nextInt(100)+5;
                }
                options.add(incorrectOption);
            }
        }
        exercise.setText(a + " + " + b);
        option0.setText(Integer.toString(options.get(0)));
        option1.setText(Integer.toString(options.get(1)));
        option2.setText(Integer.toString(options.get(2)));
        option3.setText(Integer.toString(options.get(3)));
    }

    public void checkAnswer(View view){
        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectOption))){
            usersScore++;
            feedback.setText("CORRECT!");
            MediaPlayer beep = MediaPlayer.create(this, R.raw.correctbeep);
            beep.start();
        } else {
            feedback.setText("WRONG!");
            MediaPlayer beep = MediaPlayer.create(this, R.raw.wrongbeep);
            beep.start();
        }
        numberOfQuestions++;
        score.setText(usersScore+"/"+numberOfQuestions);
        generateNewExercise();
    }

    public void startTimer(){
        new CountDownTimer(30100, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText((millisUntilFinished/1000)+" sec");
            }

            @Override
            public void onFinish() {
                MediaPlayer beep = MediaPlayer.create(getApplicationContext(), R.raw.finishsound);
                beep.start();
                feedback.setText("Time is over!\nScore "+usersScore+"/"+numberOfQuestions);
                optionsLayout.setVisibility(View.INVISIBLE);
                startButton.setText("TRY AGAIN!");
                startButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameLayout = findViewById(R.id.gameLayout);
        timer = findViewById(R.id.timer);
        score = findViewById(R.id.score);
        exercise = findViewById(R.id.exercise);
        feedback = findViewById(R.id.feedback);
        startButton = findViewById(R.id.startButton);
        optionsLayout = findViewById(R.id.optionsGrid);
        option0 = findViewById(R.id.option0);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
    }
}
