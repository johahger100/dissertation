package com.ggexjob.qrcode;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jhager on 2015-03-06.
 */
public class ScreenSlideStartPageFragment extends Fragment implements LoadImage.LoadImageListener
{

    public int componentStart = 0;
    public int instructionStart = 0;
    public String productName;
    private Boolean FULL_SIZE_IMAGE = false;
    public byte[] image;
    private ImageView imageView;
    private View view;

    private final String TAG_PRODUCTNAME = "PRODUCTNAME";
    private final String TAG_COMPONENTSTART = "COMPONENTSTART";
    private final String TAG_INSTRUCTIONSTART = "INSTRUCTIONSTART";
    private final String TAG_INSTRUCTIONIMAGE = "INSTRUCTION_IMAGE";

    public ScreenSlideStartPageFragment() {  }

    public static ScreenSlideStartPageFragment newInstance(String _productName, byte[] byteImage, int _componentStart, int _instructionStart)
    {
        ScreenSlideStartPageFragment ssspf = new ScreenSlideStartPageFragment();

        ssspf.image = byteImage;
        ssspf.productName = _productName;
        ssspf.instructionStart = _instructionStart;
        ssspf.componentStart = _componentStart;

        return ssspf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_screen_slide_start_page, container, false);
        imageView = (ImageView) view.findViewById(R.id.productImage);

        if(savedInstanceState != null)
        {
            productName = savedInstanceState.getString(TAG_PRODUCTNAME);
            componentStart = savedInstanceState.getInt(TAG_COMPONENTSTART);
            instructionStart = savedInstanceState.getInt(TAG_INSTRUCTIONSTART);
            image = savedInstanceState.getByteArray(TAG_INSTRUCTIONIMAGE);
        }

        ((TextView) view.findViewById(R.id.productName)).setText(productName);
        prepareForClicks(view);

        if(image != null)
            new LoadImage(this.getActivity(), FULL_SIZE_IMAGE, this).execute(image);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(TAG_PRODUCTNAME, productName);
        outState.putInt(TAG_COMPONENTSTART, componentStart);
        outState.putInt(TAG_INSTRUCTIONSTART, instructionStart);
        outState.putByteArray(TAG_INSTRUCTIONIMAGE, image);
    }

    private void prepareForClicks(View view)
    {
        (view.findViewById(R.id.componentClickable)).setClickable(true);
        (view.findViewById(R.id.componentClickable)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ScreenSlidePagerActivity.menuComponentClicked(view, componentStart);
            }
        });

        (view.findViewById(R.id.instructionClickable)).setClickable(true);
        (view.findViewById(R.id.instructionClickable)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ScreenSlidePagerActivity.menuInstructionClicked(view, instructionStart);
            }
        });
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}