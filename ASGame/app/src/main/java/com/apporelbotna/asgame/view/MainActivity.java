package com.apporelbotna.asgame.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.apporelbotna.asgame.R;

public class MainActivity extends AppCompatActivity
{
    Button btnPlay;
    Button btnRanking;
    Button btnSettings;
    Button btnAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open GameActivity
        btnPlay = findViewById(R.id.btnPlayMain);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        // Open RankingActivity
        btnRanking = findViewById(R.id.btnRankingMain);
        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RankingActivity.class);
                startActivity(intent);
            }
        });

        // Open SettingsActivity
        btnSettings = findViewById(R.id.btnSettingsMain);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Open AboutUsActivity
        btnAboutUs = findViewById(R.id.btnAboutUsMain);
        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });
    }
}
