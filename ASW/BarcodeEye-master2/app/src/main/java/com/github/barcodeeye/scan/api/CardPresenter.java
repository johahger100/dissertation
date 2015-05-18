package com.github.barcodeeye.scan.api;

import java.util.ArrayList;
import java.util.List;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import com.github.barcodeeye.scan.CardScrollViewAdapter;
import com.google.android.glass.widget.CardBuilder;

public class CardPresenter implements Parcelable {

    private static final String TAG = CardPresenter.class.getSimpleName();
    private final List<Uri> mImages = new ArrayList<Uri>();
    private String mText = "";
    private String mFooter = "";
    private String mTimeStamp = "";
    private String mUrl = "";
    private PendingIntent mPendingIntent;
    private Bitmap mImage = null;
    private Boolean titleCard = false;
    private int mByteArrayLength = 0;
    private byte[] mByteArray = null;
    private boolean hasByteArray = false;

    public CardPresenter() {
    }

    public CardPresenter(String text, String footer, String timeStamp, String url, PendingIntent intent,
            List<Uri> images) {
        mText = text;
        mFooter = footer;
        mTimeStamp = timeStamp;
        mUrl = url;
        mPendingIntent = intent;
        if (images != null) {
            mImages.addAll(images);
        }
    }

    public String getText() {
        return mText;
    }

    public CardPresenter setText(String text) {
        mText = text;
        return this;
    }

    public String getFooter() {
        return mFooter;
    }

    public CardPresenter setFooter(String footer) {
        mFooter = footer;
        return this;
    }
    
    public String getTimeStamp() {
    	return mTimeStamp;
    }
    
    public CardPresenter setTimeStamp(String timeStamp) {
    	mTimeStamp = timeStamp;
    	return this;
    }
    
    public String getImageUrl() {
    	return mUrl;
    }
    
    public CardPresenter setImageUrl(String url) {
    	mUrl = url;
    	return this;
    }

    public PendingIntent getPendingIntent() {
        return mPendingIntent;
    }

    public CardPresenter setPendingIntent(PendingIntent pendingIntent) {
        mPendingIntent = pendingIntent;
        return this;
    }

    public List<Uri> getImages() {
        return mImages;
    }

    public CardPresenter addImage(Context context, int resourceId) {
        String packageName = context.getPackageName();
        return addImage(Uri.parse(
                "android.resource://" + packageName + "/" + resourceId));
    }

    public CardPresenter addImage(Uri uri) {
        if (uri != null) {
            mImages.add(uri);
        } else {
            Log.w(TAG, "PhotoUri was null!");
        }
        return this;
    }
    
    public void setBitmapImage(Bitmap image)
    {
    	mImage = image;
    }
    
    public Bitmap getBitmapImage()
    {
    	return mImage;
    }

    public void setByteArray(byte[] byteArray) { mByteArray = byteArray; setByteArrayLength(byteArray.length); hasByteArray = true; }

    public byte[] getByteArray() { return mByteArray; }

    public void setByteArrayLength(int length) { mByteArrayLength = length; }

    public int getByteArrayLength() { return mByteArrayLength; }

    public void setTitleCard()
   {
       titleCard = true;
   }

    private Boolean isTitleCard()
    {
        return titleCard;
    }

    public View getCardView(Context context, CardScrollViewAdapter csa)
    {
        if(isTitleCard())
        {
            CardBuilder cardBuilder = new CardBuilder(context, CardBuilder.Layout.TITLE)
                    .setText(getText());

            if(getByteArray() != null) cardBuilder = (new LoadImage(isTitleCard(), getByteArray()).doInBackground(cardBuilder));

            return cardBuilder.getView();
        }
        else if(hasByteArray && mText.isEmpty())
        {
            CardBuilder cardBuilder = new CardBuilder(context, CardBuilder.Layout.CAPTION)
                    .setFootnote(mFooter)
                    .setTimestamp(mTimeStamp);
            cardBuilder = (new LoadImage(true, getByteArray()).doInBackground(cardBuilder));

            return cardBuilder.getView();
        }
        else if(hasByteArray)
        {
            CardBuilder cardBuilder = new CardBuilder(context, CardBuilder.Layout.COLUMNS)
                    .setText(getText())
                    .setFootnote(mFooter)
                    .setTimestamp(mTimeStamp);

            cardBuilder = (new LoadImage(isTitleCard(), getByteArray()).doInBackground(cardBuilder));

            return cardBuilder.getView();
        }
        else
        {
        	CardBuilder cardBuilder = new CardBuilder(context, CardBuilder.Layout.TEXT);
            cardBuilder.setText(getText());
            cardBuilder.setFootnote(getFooter());

	        cardBuilder.setTimestamp(mTimeStamp);
	        return cardBuilder.getView();
        }
    }

    /* *********************************************************************
     * Parcelable interface related methods
     */
    protected CardPresenter(Parcel in) {
        in.readList(mImages, Uri.class.getClassLoader());
        mText = in.readString();
        mFooter = in.readString();
        mTimeStamp = in.readString();
        mUrl = in.readString();
        titleCard = (in.readInt() == 1);
        mPendingIntent = in.readParcelable(PendingIntent.class.getClassLoader());
        hasByteArray = (in.readInt() == 1);
        if(hasByteArray) {
            setByteArray(new byte[in.readInt()]);
            in.readByteArray(mByteArray);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mImages);
        dest.writeString(mText);
        dest.writeString(mFooter);
        dest.writeString(mTimeStamp);
        dest.writeString(mUrl);
        dest.writeInt(isTitleCard() ? 1 : 0);
        dest.writeParcelable(mPendingIntent, 0);
        dest.writeInt(hasByteArray ? 1 : 0);
        if(hasByteArray) {
            dest.writeInt(mByteArrayLength);
            dest.writeByteArray(mByteArray);
        }
    }

    public static final Parcelable.Creator<CardPresenter> CREATOR = new Parcelable.Creator<CardPresenter>() {
        @Override
        public CardPresenter createFromParcel(Parcel in) {
            return new CardPresenter(in);
        }

        @Override
        public CardPresenter[] newArray(int size) {
            return new CardPresenter[size];
        }
    };
}