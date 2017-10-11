package com.example.jandol.asp1camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
{
    ImageView imageView;
    private static final int CAPTURE_IMAGE_REQUEST_CODE = 1;
    private static final int SELECT_IMAGE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);

        Button btnCamera = (Button) findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null) { // To ensure that the mobile has a camera activity and the intent can be completed
                    startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
                }
            }
        });

        Button btnGal = (Button) findViewById(R.id.btnGal);
        btnGal.setOnClickListener(new View.OnClickListener() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // THIS METHOD IS ALWAYS CALLED WHEN WE OPEN AN EXTERNAL ACTIVITY AND GO BACK
        if(resultCode != RESULT_OK) return;
        switch(requestCode) {
            case CAPTURE_IMAGE_REQUEST_CODE:
                imageView.setImageBitmap((Bitmap) data.getExtras().get("data"));
                break;
            case SELECT_IMAGE_REQUEST_CODE:
                imageView.setImageURI(data.getData());
                break;
        }
    }
}
