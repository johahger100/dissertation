package com.ggexjob.qrcode;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment {

	private String instruction;
    private String footer;
    private String timeStamp;

	public ScreenSlidePageFragment() {  }

    public static ScreenSlidePageFragment newInstance(String _instruction, String _footer, String _timeStamp)
    {
        ScreenSlidePageFragment sspf = new ScreenSlidePageFragment();

        sspf.instruction = _instruction;
        sspf.footer = _footer;
        sspf.timeStamp = _timeStamp;

        return sspf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	super.onCreateView(inflater, container, savedInstanceState);
    	
        View view = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        
        if(savedInstanceState != null) {
            instruction = savedInstanceState.getString("INSTRUCTION");
            footer = savedInstanceState.getString("FOOTER");
            timeStamp = savedInstanceState.getString("TIMESTAMP");
        }

        ((TextView) view.findViewById(R.id.fragmentText)).setText(instruction);
        ((TextView) view.findViewById(R.id.fragmentFooterText)).setText(footer);
        ((TextView) view.findViewById(R.id.fragmentTimestampText)).setText(timeStamp);

        return (ViewGroup) view;
    }

    @Override
	public void onSaveInstanceState(Bundle outState)
    {
    	super.onSaveInstanceState(outState);
    	outState.putString("INSTRUCTION", instruction);
        outState.putString("FOOTER", footer);
        outState.putString("TIMESTAMP", timeStamp);
    }
}