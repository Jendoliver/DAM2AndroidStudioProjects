package com.apporelbotna.asgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
Realitzar una aplicació Android que inclogui els següents elements:

1) Una pantalla de benvinguda amb un menú d'opcions que contindrà les següents opcions: "Jugar", "Rànquing", "Ajustaments", "Quant a".

2) Les dues primeres accions ens portaran a pàgines buides, que omplirem més endavant.

3) La acció "Ajustaments" ens ha de permetre fixar el nom del jugador i assignar-li una foto.

4) La pantalla "Quant a" serà de construcció i disseny lliure.
 */

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
