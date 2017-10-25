package com.apporelbotna.asgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
Realitzar una aplicació Android que inclogui els següents elements:

1) Una pantalla de benvinguda amb un menú d'opcions que contindrà les següents opcions: "Jugar", "Rànquing", "Ajustaments", "Quant a".

2) Les dues primeres accions ens portaran a pàgines buides, que omplirem més endavant.

3) La acció "Ajustaments" ens ha de permetre fixar el nom del jugador i assignar-li una foto.

4) La pantalla "Quant a" serà de construcció i disseny lliure.
 */

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
        btnPlay = (Button) findViewById(R.id.btnPlayMain);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        // Open RankingActivity
        btnRanking = (Button) findViewById(R.id.btnRankingMain);
        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RankingActivity.class);
                startActivity(intent);
            }
        });

        // Open SettingsActivity
        btnSettings = (Button) findViewById(R.id.btnSettingsMain);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Open AboutUsActivity
        btnAboutUs = (Button) findViewById(R.id.btnAboutUsMain);
        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });
    }
}
