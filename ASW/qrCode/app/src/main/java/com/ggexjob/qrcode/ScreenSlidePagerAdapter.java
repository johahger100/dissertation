package com.ggexjob.qrcode;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by jhager on 2015-03-11.
 */
/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private Products product = null;
    private String randText = "";

    public ScreenSlidePagerAdapter(FragmentManager fm, Products _product) {
        super(fm);

        product = _product;

        super.saveState();
    }

    public ScreenSlidePagerAdapter(FragmentManager fm, String randomizeString) {
        super(fm);

        RandomizeEnglishText ret = new RandomizeEnglishText();

        for (int i=0; i<750; i++)
            randText = randText + ret.randchar();

        Log.d("RANDTEXT", randText);

        super.saveState();
    }

    @Override
    public Fragment getItem(int position)
    {
        if(product == null)
            return createColumnSlide(null, randText, "RandomizeText", "1/1");

        if(position == 0)
            return createStartSlide(product.getName(), product.getImage(), 1, product.getComponents().size()+1);
        else if(position > 0 && position < 1+product.getComponents().size())
            return createComponentSlide(product.getComponents().get(position - 1));
        else if(position >= 1+product.getComponents().size() && position < 1+product.getComponents().size()+product.getInstructions().size())
            return createInstructionSlide(product.getInstructions().get(position - 1 - product.getComponents().size()));
        else
            return null; //TODO error slide
    }

    private Fragment createStartSlide(String productName, byte[] productImage, int componentStart, int instructionsStart)
    {
        return ScreenSlideStartPageFragment.newInstance(productName, productImage, componentStart, instructionsStart);
    }

    private Fragment createComponentSlide(Components component)
    {
        String timestamp = String.valueOf(product.getComponents().indexOf(component) + 1) + "/" + product.getComponents().size();
        String footer = "Components";

        if(component.getImage() != null)
            return createColumnSlide(component.getImage(), component.getName(), footer, timestamp);
        else
            return createTextSlide(component.getName(), footer, timestamp);
    }

    private Fragment createInstructionSlide(Instructions instruction)
    {
        String timestamp = String.valueOf(product.getInstructions().indexOf(instruction)+1) + "/" + product.getInstructions().size();
        String footer = "Instructions";

        if(instruction.getImage() != null && instruction.getInstruction() != null)
            return createColumnSlide(instruction.getImage(), instruction.getInstruction(), footer, timestamp);
        else if(instruction.getImage() != null && instruction.getInstruction() == null)
            return createFullImageSlide(instruction.getImage(), footer, timestamp);
        else
           return createTextSlide(instruction.getInstruction(), footer, timestamp);
    }

    private Fragment createColumnSlide(byte[] image, String text, String footer, String timestamp)
    {
        return ScreenSlideColumnPageFragment.newInstance(image, text, footer, timestamp);
    }

    private Fragment createFullImageSlide(byte[] image, String footer, String timestamp)
    {
        return ScreenSlideImagePageFragment.newInstance(image, footer, timestamp);
    }

    private Fragment createTextSlide(String text, String footer, String timestamp)
    {
        return ScreenSlidePageFragment.newInstance(text, footer, timestamp);
    }

    @Override
    public int getCount() {
        if(product == null)
            return 1;
        return 1 + product.getComponents().size() + product.getInstructions().size();
    }
}