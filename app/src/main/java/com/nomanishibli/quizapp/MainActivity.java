package com.nomanishibli.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText edit;
    TextView clickCounter, clock;
    Button tryToPressMe, starter;
    Context context;
    Toast toast;
    int xPos, yPos;
    int clicks = 0;
    final Handler t = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString("name", "Guest");
        editor.apply();
    }

    public void goToOne(View view) {
        setContentView(R.layout.activity_main);
        edit = findViewById(R.id.editText);

        edit.setText(sharedPreferences.getString("name", "What's your name?"));
    }

    public void goToTwo(View view) {
        setContentView(R.layout.activity_main2);
        tryToPressMe = findViewById(R.id.tryToPressMe);
        started = false;
        clicks = 0;
        tryToPressMe.setText("Try\nto Press Me\n" + sharedPreferences.getString("name", "Guest") + "!");
    }

    public void goToThree(View view) {
        setContentView(R.layout.activity_main3);
    }

    public void goToFour(View view) {
        setContentView(R.layout.activity_main4);
    }

    public void enterName(View view) {
        edit = findViewById(R.id.editText);
        editor = sharedPreferences.edit();
        editor.putString("name", "" + edit.getText());
        editor.apply();
        Log.d("update", sharedPreferences.getString("name", ""));
    }

    public void move(View view){
        if(started) {
            tryToPressMe = findViewById(R.id.tryToPressMe);
            xPos = (int) (Math.random() * (Resources.getSystem().getDisplayMetrics().widthPixels - tryToPressMe.getWidth()));
            yPos = (int) (Math.random() * (Resources.getSystem().getDisplayMetrics().heightPixels - tryToPressMe.getHeight() - 250));
            clicks++;
            clickCounter = findViewById(R.id.clickCounter);
            clickCounter.setText("Clicks: " + clicks);

            Log.d("move", "xPos: " + xPos + ", yPos: " + yPos);
            t.postDelayed(new Runnable() {
                public void run() {
                    if (xPos != tryToPressMe.getX() && yPos != tryToPressMe.getY()) {
                        if (xPos > tryToPressMe.getX())
                            tryToPressMe.setX(tryToPressMe.getX() + 1);
                        else if (xPos < tryToPressMe.getX())
                            tryToPressMe.setX(tryToPressMe.getX() - 1);
                        if (yPos > tryToPressMe.getY())
                            tryToPressMe.setY(tryToPressMe.getY() + 1);
                        else if (yPos < tryToPressMe.getY())
                            tryToPressMe.setY(tryToPressMe.getY() - 1);

                        //t.postDelayed(this, 100);
                        t.postDelayed(this, 1);
                        t.postDelayed(this, 1);
                    }

                }
            }, 100);
        }
    }

    int time = 15;
    boolean started = false;

    public void startStop(View view) {
        starter = findViewById(R.id.starter);
        clock = findViewById((R.id.clock));
        if (started) {
            context = getApplicationContext();
            toast = Toast.makeText(context, "Score of " + clicks + " submitted!", Toast.LENGTH_SHORT);
            toast.show();
            clicks = 0;
            clickCounter = findViewById(R.id.clickCounter);
            clickCounter.setText("Clicks: " + clicks);
            starter.setText("START");
            time = 15;
        }
        else{
            starter.setText("STOP");
            t.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(time != 0 && started) {
                        time = time - 1;
                        clock.setText("TIME: " + time);
                        t.postDelayed(this, 1000);
                    }
                    else{
                        context = getApplicationContext();
                        toast = Toast.makeText(context, "Score of " + clicks + " submitted!", Toast.LENGTH_SHORT);
                        toast.show();
                        clicks = 0;
                        time = 15;
                        clickCounter = findViewById(R.id.clickCounter);
                        clickCounter.setText("Clicks: " + clicks);
                        starter.setText("START");
                    }

                }
            }, 1000);
        }


        started = !started;

    }
}