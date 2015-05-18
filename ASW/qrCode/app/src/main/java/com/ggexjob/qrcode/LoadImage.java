package com.ggexjob.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

/**
 * Created by jhager on 2015-04-02.
 */
public class LoadImage extends AsyncTask<byte[], Void, Bitmap> {

    private final LoadImageListener _listener;
    private boolean fullImage;
    private Context context;

    public interface LoadImageListener { public void onImageLoaded(Bitmap bitmap); }

    public LoadImage(Context _context, Boolean _fullImage, LoadImageListener listener)
    {
        _listener = listener;
        fullImage = _fullImage;
        context = _context;
    }

    protected Bitmap doInBackground(byte[]... _byteImages) {
        byte[] byteImage = _byteImages[0];

        return resizeBitmap(BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length));
    }

    protected void onPostExecute(Bitmap result)
    {
        _listener.onImageLoaded(result);
    }

    private Bitmap resizeBitmap(Bitmap bitmap)
    {
        int width = bitmap.getWidth(), height = bitmap.getHeight();
        int newWidth, newHeight;
        int maxWidth, maxHeight;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        if(fullImage) {
            maxWidth = metrics.widthPixels;
            maxHeight = metrics.heightPixels;
        }
        else {
            maxWidth = 3*metrics.widthPixels/8;
            maxHeight = metrics.heightPixels;
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