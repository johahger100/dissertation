package com.ggexjob.qrcode;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

// http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private Bitmap instructionImage = null;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {

        String urldisplay = urls[0];
        
        try
        {
            InputStream in = new java.net.URL(urldisplay).openStream();
            instructionImage = BitmapFactory.decodeStream(in);
        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            
        }
        
        return instructionImage;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}