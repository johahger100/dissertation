package com.github.barcodeeye;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import com.google.android.glass.widget.Slider;

/**
 * Created by jhager on 2015-03-10.
 */
public class DownloadProductTask extends AsyncTask<Products, Products, Products> {

    private static Products product;
    private String qrCode;
    private View view;
    private Slider progressSlider;
    private Slider.Indeterminate progressIndeterminate;

    public DownloadProductTask(View _view, String _qrCode)
    {
        view = _view;
        qrCode = _qrCode;
    }

    @Override
    protected void onPreExecute()
    {
        progressSlider = Slider.from(view);
        progressIndeterminate = progressSlider.startIndeterminate();
        super.onPreExecute();
    }

    protected Products doInBackground(Products... params) {
        try {
            GetService client = ServiceGenerator.createService(GetService.class, "http://googleglassexjobb.azurewebsites.net");

            product = client.GetProduct(qrCode);

            return product;

        }catch(Exception ex){
            Log.d("App", ex.getMessage());
            return new Products();
        }

    }

    @Override
    protected void onPostExecute(Products product) {
        progressIndeterminate.hide();

        super.onPostExecute(product);
    }
}