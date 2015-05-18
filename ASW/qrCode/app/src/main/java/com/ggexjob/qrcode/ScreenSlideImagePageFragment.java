package com.ggexjob.qrcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenSlideImagePageFragment extends Fragment implements LoadImage.LoadImageListener {

	private String instruction = null;
    public String footer;
    public String timeStamp;
    public byte[] image;
    private Boolean FULL_SIZE_IMAGE = true;
    private ImageView imageView;

	public ScreenSlideImagePageFragment() {  }

    public static ScreenSlideImagePageFragment newInstance(byte[] byteImage, String _footer, String _timeStamp)
    {
        ScreenSlideImagePageFragment ssipf = new ScreenSlideImagePageFragment();

        ssipf.image = byteImage;
        ssipf.footer = _footer;
        ssipf.timeStamp = _timeStamp;

        return ssipf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_screen_slide_image_page, container, false);
        imageView = (ImageView) view.findViewById(R.id.instructionImageView);

        if(savedInstanceState != null)
        {
        	instruction = savedInstanceState.getString("INSTRUCTION");
            image = savedInstanceState.getByteArray("INSTRUCTION_IMAGE");
            footer = savedInstanceState.getString("FOOTER");
            timeStamp = savedInstanceState.getString("TIMESTAMP");

            ((TextView) view.findViewById(R.id.fragmentFooterText)).setText(footer);
            ((TextView) view.findViewById(R.id.fragmentTimestampText)).setText(timeStamp);
        }

        ((TextView) view.findViewById(R.id.fragmentFooterText)).setText(footer);
        ((TextView) view.findViewById(R.id.fragmentTimestampText)).setText(timeStamp);

        if(image != null)
            new LoadImage(this.getActivity(), FULL_SIZE_IMAGE, this).execute(image);

        return (ViewGroup) view;
    }

    @Override
	public void onSaveInstanceState(Bundle outState)
    {
    	super.onSaveInstanceState(outState);
    	outState.putString("INSTRUCTION", instruction);
        outState.putByteArray("INSTRUCTION_IMAGE", image);
        outState.putString("FOOTER", footer);
        outState.putString("TIMESTAMP", timeStamp);
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}