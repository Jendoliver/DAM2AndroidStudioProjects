package com.apporelbotna.firstasapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    RelativeLayout mainBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv_olga);
        mainBackground = (RelativeLayout) findViewById(R.id.mainBackground);

        // Events
        Button syy = (Button) findViewById(R.id.btn1);
        syy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Ese syy ke patsdonidas");
            }
        });

        Button noo = (Button) findViewById(R.id.btn2);
        final EditText textInput = (EditText) findViewById(R.id.input1);
        noo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(textInput.getText());
            }
        });

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
