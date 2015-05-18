package com.ggexjob.qrcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jhager on 2015-04-07.
 */
public class ScreenSlideColumnPageFragment extends Fragment implements LoadImage.LoadImageListener {

    public String instruction;
    public String footer;
    public String timeStamp;
    public byte[] image;
    private ImageView imageView;
    private Boolean FULL_SIZE_IMAGE = false;

    public ScreenSlideColumnPageFragment() {  }

    public static ScreenSlideColumnPageFragment newInstance(byte[] byteImage, String _instruction, String _footer, String _timeStamp)
    {
        ScreenSlideColumnPageFragment sscpf = new ScreenSlideColumnPageFragment();

        sscpf.image = byteImage;
        sscpf.instruction = _instruction;
        sscpf.footer = _footer;
        sscpf.timeStamp = _timeStamp;

        return sscpf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_screen_slide_column_page, container, false);
        imageView = (ImageView) view.findViewById(R.id.columnImage);


        if(savedInstanceState != null) {
            instruction = savedInstanceState.getString("INSTRUCTION");
            footer = savedInstanceState.getString("FOOTER");
            timeStamp = savedInstanceState.getString("TIMESTAMP");
            image = savedInstanceState.getByteArray("INSTRUCTION_IMAGE");

            ((TextView) view.findViewById(R.id.fragmentText)).setText(instruction);
            ((TextView) view.findViewById(R.id.fragmentFooterText)).setText(footer);
            ((TextView) view.findViewById(R.id.fragmentTimestampText)).setText(timeStamp);
        }

        ((TextView) view.findViewById(R.id.fragmentText)).setText(instruction);
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
        outState.putString("FOOTER", footer);
        outState.putString("TIMESTAMP", timeStamp);
        outState.putByteArray("INSTRUCTION_IMAGE", image);
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}