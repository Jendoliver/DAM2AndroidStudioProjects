package com.apporelbotna.listsandadapterstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity
{
    public static final String[] listItems = {
        "Porrule", "Camorrano", "Spetek", "Okulart", "Phat", "Es el", "Conde", "Phat", "No se esconde", "Comiendo", "Jamon", "jam0n", "jAmon", "Con bertin osborne"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);// PILLA ESTRUCTURA LIST Y LA DESPLEGA SOBRE UN UNICO ITEM EN EL LAYOUT
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { // i = position of the list
                Toast.makeText(ListActivity.this, "POSITION: " + i, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
