package com.ggexjob.qrcode;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class ScreenSlidePagerActivity extends FragmentActivity {

    private String start;
    private ArrayList<String> components = new ArrayList<>();
	private ArrayList<String> instructions = new ArrayList<String>();
    private ArrayList<String> all = new ArrayList<>();

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static int NUM_PAGES = 1;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private static ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        String qrCode = getIntent().getExtras().getString("QR_CODE");

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        new DownloadProductTask(this, mPager, qrCode).execute();
    }

    public static void menuComponentClicked(View view, int componentStart)
    {
        mPager.setCurrentItem(componentStart);
    }

    public static void menuInstructionClicked(View view, int instructionStart)
    {
        mPager.setCurrentItem(instructionStart);
    }

    @Override
    public void onBackPressed()
    {
        if (mPager.getCurrentItem() == 0)
        {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        }
        else
        {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }       
    }
}