package com.apporelbotna.asgame.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.apporelbotna.asgame.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity
{
    EditText editTextName;
    ImageView imgUser;
    Button btnTakePhoto;
    Button btnGallery;
    Uri imgUserURI;

    private static final int CAPTURE_IMAGE_REQUEST_CODE = 1;
    private static final int SELECT_IMAGE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        editTextName = findViewById(R.id.etNameSettings);
        imgUser = findViewById(R.id.imgUser);

        // Take photo to get imgUser
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null) { // To ensure that the mobile has a camera activity and the intent can be completed
                    startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
                }
            }
        });

        // Open gallery to select imgUser
        btnGallery = findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.putExtra("return-data", true);
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT); // Use ACTION_OPEN_DOCUMENT to ensure that the app remembers its permissions to get the URI every time
                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE);
                }
            }
        });
    }

    @Override
    protected void onPause() { // Save existing data
        saveSettings();
        super.onPause();
    }

    public void saveSettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", editTextName.getText().toString());
        if(imgUserURI != null)  {
            editor.putString("imgUserURI", imgUserURI.toString());
        }
        editor.apply();
    }

    @Override
    protected void onResume() { // Restore existing data
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String name = prefs.getString("name", ""); // 2nd argument = default value if can't get the value
        editTextName.setText(name);
        String imgUserURIString = prefs.getString("imgUserURI", null);
        if(imgUserURIString != null) {
            imgUser.setImageURI(Uri.parse(imgUserURIString));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // THIS METHOD IS ALWAYS CALLED WHEN WE OPEN AN EXTERNAL ACTIVITY AND GO BACK
        if(resultCode != RESULT_OK) return;
        switch(requestCode) {
            case CAPTURE_IMAGE_REQUEST_CODE:
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                imgUser.setImageBitmap(imageBitmap);
                imgUserURI = saveToInternalStorage(imageBitmap);;
                saveSettings();
                break;
            case SELECT_IMAGE_REQUEST_CODE:
                imgUserURI = data.getData();
                imgUser.setImageURI(imgUserURI);
                saveSettings();
                break;
        }
    }

    public Uri saveToInternalStorage(Bitmap bitmap) {
        File dir = this.getFilesDir();
        File file = new File(dir, "imgUser.jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            return Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
