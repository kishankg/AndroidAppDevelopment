package com.example.voldemort.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView resultTextView;
    TextView pointsTextView;
    TextView sumTextView;
    TextView timerTextView;
    Button button0,button1,button2,button3;
    Button playAgainButton;
    RelativeLayout relativeLayout;

    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCurrentAnswer;
    int score=0;
    int numberOfQuestions = 0;

    public void playAgain(final View view)
    {
        score = 0;
        numberOfQuestions=0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(view.INVISIBLE);

        generateQuestion();

        new CountDownTimer(30100,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(Long.toString(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0s");
                resultTextView.setText("Your score is " + pointsTextView.getText().toString());
                playAgainButton.setVisibility(view.VISIBLE);
            }
        }.start();
        generateQuestion();
    }
    public void generateQuestion()
    {
        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a)+" + "+Integer.toString(b));

        locationOfCurrentAnswer = rand.nextInt(4);

        answers.clear();

        int incorrectAnswer;

        for(int i=0;i<4;i++)
        {
            if(i==locationOfCurrentAnswer)
            {
                answers.add(a+b);
            }
            else
            {
                incorrectAnswer = rand.nextInt(41);
                while(incorrectAnswer == a+b)
                {
                    incorrectAnswer = rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void chooseAnswer(View view)
    {
        if(view.getTag().toString().equals(Integer.toString(locationOfCurrentAnswer)))
        {
            score++;
            resultTextView.setText("Correct");

        }
        else
        {
            resultTextView.setText("Incorrect");
        }
        numberOfQuestions++;
        pointsTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        generateQuestion();

    }

    public void start(View view)
    {
        startButton.setVisibility(view.INVISIBLE);
        playAgain((TextView)findViewById(R.id.timerTextView));
        relativeLayout.setVisibility(view.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);

        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);

        playAgainButton = (Button)findViewById(R.id.playAgainButton);

        sumTextView = (TextView)findViewById(R.id.sumTextView);
        resultTextView = (TextView)findViewById(R.id.resultTextView);
        pointsTextView = (TextView)findViewById(R.id.pointsTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);


    }
}
