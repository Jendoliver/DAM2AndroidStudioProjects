package com.apporelbotna.listsandadapterstest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public static int[] buttons = { R.id.btn1, R.id.btn2 };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int id : buttons) {
            Button btn = (Button) findViewById(id);
            btn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(this, ListActivity.class));
                break;
            case R.id.btn2:
        }
    }
}
