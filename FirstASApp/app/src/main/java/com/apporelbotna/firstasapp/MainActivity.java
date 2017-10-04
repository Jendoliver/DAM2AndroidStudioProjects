package com.apporelbotna.firstasapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView textView;
    RelativeLayout mainBackground;
    EditText textInput;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Sets the activity_main as first window
        textView = (TextView) findViewById(R.id.tv_olga); // Gets tv_olga from layout
        textInput = (EditText) findViewById(R.id.input1); // Gets input1 from layout
        mainBackground = (RelativeLayout) findViewById(R.id.mainBackground); // Gets background (relativeLayout) from layout

        /* Events for btnDefault button */
        Button btnDefault = (Button) findViewById(R.id.btn1);
        // Syy normal click
        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(getResources().getString(R.string.btnDefaultClick));
            }
        });
        // Syy long click
        btnDefault.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                textView.setText(getResources().getString(R.string.btnDefaultLongClick));
                return false;
            }
        });

        /* Event for btnText button */
        Button btnText = (Button) findViewById(R.id.btn2);
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("Warning","OSTIEEEE");
                textView.setText(textInput.getText());
            }
        });

        /* Events for colors button */
        Button btnRed = (Button) findViewById(R.id.btnRed);
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainBackground.setBackgroundColor(Color.RED);
            }
        });
        Button btnGreen = (Button) findViewById(R.id.btnGreen);
        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainBackground.setBackgroundColor(Color.GREEN);
            }
        });
        Button btnBlue = (Button) findViewById(R.id.btnBlue);
        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainBackground.setBackgroundColor(Color.BLUE);
            }
        });
    }
}
