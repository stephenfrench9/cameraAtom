package com.example.stephenfrench.cameraatom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("alex", "onCreate(): start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.prestige);
        Log.d("alex", "onCreate(): end");

    }


    public void buttonClicked(View v) {
        Log.d("alex", "buttonClicked(): start");
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Log.d("alex", "dispatchTakePictureIntent(): start");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.d("alex", "dispatchTakePictureIntent(): intent created");
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Log.d("alex", "dispatchTakePictureIntent(): package manager checked");
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            Log.d("alex", "dispatchTakePictureIntent(): Activity launched");
        }
        Log.d("alex", "dispatchTakePictureIntent(): end");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("alex", "onActivityResult(): start");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d("alex", "onActivityResult(): result confirmed");
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
        Log.d("alex", "onActivityResult(): end");
    }
}
