package com.apporelbotna.asgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity
{
    EditText editTextName;
    ImageView imgUser;
    Button btnTakePhoto;
    Button btnGallery;

    private static final int CAPTURE_IMAGE_REQUEST_CODE = 1;
    private static final int SELECT_IMAGE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        editTextName = (EditText) findViewById(R.id.editTextName);
        imgUser = (ImageView) findViewById(R.id.imgUser);

        btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null) { // To ensure that the mobile has a camera activity and the intent can be completed
                    startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
                }
            }
        });

        btnGallery = (Button) findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE);
                }
            }
        });
    }

    @Override
    protected void onPause() { // Save existing data
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", editTextName.getText().toString());
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onResume() { // Restore existing data
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String name = prefs.getString("name", ""); // 2nd argument = default value if can't get the value
        editTextName.setText(name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // THIS METHOD IS ALWAYS CALLED WHEN WE OPEN AN EXTERNAL ACTIVITY AND GO BACK
        if(resultCode != RESULT_OK) return;
        switch(requestCode) {
            case CAPTURE_IMAGE_REQUEST_CODE:
                imgUser.setImageBitmap((Bitmap) data.getExtras().get("data"));
                break;
            case SELECT_IMAGE_REQUEST_CODE:
                imgUser.setImageURI(data.getData());
                break;
        }
    }
}
