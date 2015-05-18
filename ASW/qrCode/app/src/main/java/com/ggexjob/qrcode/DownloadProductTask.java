package com.ggexjob.qrcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

/**
 * Created by jhager on 2015-03-11.
 */
public class DownloadProductTask  extends AsyncTask<Products, Void, Products> {

    private static Products product;
    private String qrCode;
    private Context context;
    private ProgressDialog dialog;
    private ViewPager pager;

    public DownloadProductTask(Context _context, ViewPager _pager, String _qrCode)
    {
        context = _context;
        pager = _pager;
        qrCode = _qrCode;
    }

    @Override
    protected void onPreExecute()
    {
        new ProgressDialog(context);
        dialog = ProgressDialog.show(context, "", "Loading...");
        super.onPreExecute();
    }

    protected Products doInBackground(Products... params) {
        if(qrCode.compareTo("rand") != 0) {
            try {
                ProductService client = ServiceGenerator.createService(ProductService.class, "http://googleglassexjobb.azurewebsites.net");

                product = client.getproduct(qrCode);

                return product;

            } catch (Exception ex) {
                Log.d("App", ex.getMessage());
                return null;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Products product)
    {
        Timer.getInstance().startTimer();

        if(dialog != null && dialog.isShowing())
            dialog.dismiss();

        PagerAdapter mPagerAdapter;

        if(qrCode.compareTo("rand") != 0) {
            mPagerAdapter = new ScreenSlidePagerAdapter(((FragmentActivity) context).getSupportFragmentManager(), product);
        }
        else {
            mPagerAdapter = new ScreenSlidePagerAdapter(((FragmentActivity) context).getSupportFragmentManager(), qrCode);
        }

        pager.setAdapter(mPagerAdapter);
        mPagerAdapter.notifyDataSetChanged();

        Timer.getInstance().stopTimer();
        Timer.getInstance().logElapsedTime("Display");

        super.onPostExecute(product);
    }
}