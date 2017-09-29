package com.apporelbotna.firstasapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv_olga);

        // Events
        Button syy = (Button) findViewById(R.id.btn1);
        syy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Ese syy ke syyyyy");
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
    }
}
