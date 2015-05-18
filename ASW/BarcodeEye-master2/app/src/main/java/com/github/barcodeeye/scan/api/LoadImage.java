package com.github.barcodeeye.scan.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.android.glass.widget.CardBuilder;

/**
 * Created by jhager on 2015-04-02.
 */
public class LoadImage extends AsyncTask<CardBuilder, Void, CardBuilder> {

    private boolean fullImage;
    private byte[] byteImage;

    public LoadImage(Boolean _fullImage, byte[] _byteImage)
    {
        fullImage = _fullImage;
        byteImage = _byteImage;
    }

    protected CardBuilder doInBackground(CardBuilder... params) {

        CardBuilder cardBuilder = params[0];

        try {
            cardBuilder.addImage(resizeBitmap(BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length)));
        } catch (Exception e) {
            Log.e("LOAD IMAGE ASYNC TASK", "Failed to load: " + e);
        }

        return cardBuilder;
    }

    private Bitmap resizeBitmap(Bitmap bitmap)
    {
        int width = bitmap.getWidth(), height = bitmap.getHeight();
        int newWidth, newHeight;
        int maxWidth, maxHeight;

        if(fullImage) {
            maxWidth = 640;
            maxHeight = 360;
        }
        else {
            maxWidth = 240;
            maxHeight = 360;
        }

        if(width > maxWidth && (width-maxWidth) >= (height-maxHeight))
        {
            newWidth = maxWidth;
            newHeight = ((newWidth*height) / width < 1) ?   1   :   ((newWidth*height) / width);
        }
        else if(height > maxHeight && (width-maxWidth) < (height-maxHeight))
        {
            newHeight = maxHeight;
            newWidth = ((newHeight*width) / height < 1) ?   1   :   ((newHeight*width) / height);
        }
        else
        {
            newWidth = width;
            newHeight = height;
        }

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
    }
}