package com.example.stephenfrench.cameraatom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
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
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
        Log.d("alex", "dispatchTakePictureIntent(): end");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("alex", "onActivityResult(): start");

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d("alex", "onActivityResult(): result confirmed");






            Log.d("alex", "onActivityResult(): got imageview");
        }
        Log.d("alex", "onActivityResult(): end");
    }

    public void displayPic(View v) {
        Bitmap imageBitmap = (Bitmap) BitmapFactory.decodeFile(mCurrentPhotoPath);
        mImageView.setImageBitmap(imageBitmap);
    }

    private File createImageFile() throws IOException {
        Log.d("alex", "createImageFile(): start");
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("alex", "createImageFile(): end");
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public void empty(View view) {
        mImageView.setImageBitmap(null);

    }
}
