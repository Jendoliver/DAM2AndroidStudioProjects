package com.apporelbotna.firstasapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

        /* Events for btnDefault */
        Button btnDefault = (Button) findViewById(R.id.btn1);
        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(getResources().getString(R.string.btnDefaultClick));
            }
        });
        btnDefault.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                textView.setText(getResources().getString(R.string.btnDefaultLongClick));
                // MainActivity.this = context, SecondActivity.class = the new class to be "generated"
                // We can't put simply this on the first parameter because we're actually on the View.OnLongClickListener class
                // ALL INTENTS ARE HOLDED BY THE ANDROID OPERATIVE SYSTEM
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);    /** These two lines are used **/
                startActivity(intent);                                                  /** to change the current activity **/
                return false;
            }
        });

        /* Events for btnText */
        Button btnText = (Button) findViewById(R.id.btn2);
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(textInput.getText());
            }
        });
        btnText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Uri uri = Uri.parse("http://www.google.es");      /** These lines **/
                Intent intent = new Intent(Intent.ACTION_VIEW);   /** are used **/
                intent.setData(uri);                              /** to open **/
                startActivity(intent);                            /** an external uri **/
                return false;
            }
        });

        /* Events for btnTelephone */
        Button btnTelephone = (Button) findViewById(R.id.btnTelephone);
        btnTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:628530069");               /** An uri is like a URL but more general: **/
                Intent intent = new Intent(Intent.ACTION_VIEW);     /** it can hold directions to a telephone (tel:), to a mail (mailto:), etc **/
                intent.setData(uri);                                /** https://es.wikipedia.org/wiki/URI_scheme **/
                startActivity(intent);
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
