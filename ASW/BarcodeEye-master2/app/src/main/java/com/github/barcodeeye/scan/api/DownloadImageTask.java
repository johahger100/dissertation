package com.github.barcodeeye.scan.api;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.github.barcodeeye.scan.CardScrollViewAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

// http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
// http://stackoverflow.com/questions/24832799/google-glass-have-card-display-image-from-web

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private CardScrollViewAdapter cardScrollViewAdapter;
    private Bitmap instructionImage;
    private boolean updated;
    private CardPresenter cardPresenter;

    public DownloadImageTask(CardScrollViewAdapter csa, CardPresenter cd) {
        this.cardScrollViewAdapter = csa;
        this.cardPresenter = cd;
    }

    protected Bitmap doInBackground(String... urls) {

    	if(this.cardPresenter.getBitmapImage() != null)
    	{
    		updated = false;
    		return this.cardPresenter.getBitmapImage();
    	}

        try
        {
            InputStream in = new BufferedInputStream(new URL(urls[0]).openStream());
            instructionImage = BitmapFactory.decodeStream(in);
        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        updated = true;
        return instructionImage;
    }

    protected void onPostExecute(Bitmap result) {
        if(updated && result != null)
        {
                CardPresenter oldCardPresenter = this.cardPresenter;
                CardPresenter newCardPresenter = new CardPresenter();

                result = resizeBitmap(result);

                newCardPresenter.setBitmapImage(result);
                newCardPresenter.setTimeStamp(oldCardPresenter.getTimeStamp());
    		newCardPresenter.setImageUrl(oldCardPresenter.getImageUrl());
    		newCardPresenter.setPendingIntent(oldCardPresenter.getPendingIntent());

	        this.cardScrollViewAdapter.replaceItem(oldCardPresenter, newCardPresenter);

	        this.cardScrollViewAdapter.notifyDataSetChanged();
    	}
    }

    private Bitmap resizeBitmap(Bitmap bitmap)
    {
        int width = bitmap.getWidth(), height = bitmap.getHeight();
        int newWidth, newHeight;

        if(width > 640 && (width-640) >= (height-360))
        {
            newWidth = 640;
            newHeight = ((newWidth*height) / width < 1) ?   1   :   ((newWidth*height) / width);
        }
        else if(height > 360 && (width-640) < (height-360))
        {
            newHeight = 360;
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