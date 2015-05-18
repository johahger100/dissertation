package com.github.barcodeeye;

/**
 * Created by jhager on 2015-03-10.
 */
public class ProductURL {
    private static ProductURL ourInstance = new ProductURL();
    private String url;

    public static ProductURL getInstance() {
        return ourInstance;
    }

    private ProductURL() {
    }

    public void setProductURL(String _url)
    {
        url = _url;
    }

    public String getProductURL()
    {
        return url;
    }
}
