package com.idorosh.i_dorosh_connectedapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;


//Class to get image from url
public class FlickrImageView extends AppCompatActivity {

    //Variable for current image url from main activity
    String currentImage = "";

    //Variable that sets image to the Image view in the detail view
    ImageView img;

    //Bitmap for the Image that is retrieved from url.
    Bitmap bitmap;

    //A dialog that appears when the image is loading
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        //Gets photo url from main activity
        currentImage = (getIntent().getExtras().getString("selectedPhoto"));

        //Calls the load image method and passes through the image url
        new LoadImage().execute(currentImage);

        //Setting the title for the image
        TextView imageTitle = (TextView) findViewById(R.id.selectedTitle);
        imageTitle.setText(getIntent().getExtras().getString("selectedTitle"));

        //Setting img to the proper Image view
        img = (ImageView) findViewById(R.id.flickrImage);

    }

    //Gets Image from url and creates a bitmap. Also sets the bitmap to the image view in the detail screen
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Showing the dialog on start of loading
            pDialog = new ProgressDialog(FlickrImageView.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }

        protected Bitmap doInBackground(String... args) {
            //Retrieves image and creates bitmap
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            //Checks if image is null if not it will set it to the image view and dismisses the dialog
            if (image != null) {
                img.setImageBitmap(image);
                pDialog.dismiss();

            } else {

                pDialog.dismiss();
                Toast.makeText(FlickrImageView.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }


}
