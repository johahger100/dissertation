package com.github.barcodeeye.scan.result.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.github.barcodeeye.Components;
import com.github.barcodeeye.DownloadProductTask;
import com.github.barcodeeye.Instructions;
import com.github.barcodeeye.ProductURL;
import com.github.barcodeeye.Products;
import com.github.barcodeeye.Timer;
import com.github.barcodeeye.scan.api.CardPresenter;
import com.github.barcodeeye.scan.result.ResultProcessor;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;

public class TextResultProcessor extends ResultProcessor<ParsedResult> {

    private int componentPosition;
    private int instructionPosition;
    private List<CardPresenter> cardPresenters;
    private boolean isComponent = false;
    DownloadProductTask downloadProductTask;// = new DownloadProductTask();

    public TextResultProcessor(Context context, ParsedResult parsedResult,
            Result result, Uri photoUri) {
        super(context, parsedResult, result, photoUri);
    }

    @Override
    public List<CardPresenter> getCardResults() {
        return null;
    }

    @Override
    public List<CardPresenter> getCardResults(View view) {

            Products product;
            List<Components> componentList;
            List<Instructions> instructionList;
            CardPresenter startCard;
            List<CardPresenter> componentCards;
            List<CardPresenter> instructionCards;

            // Gets product ID and sets the value to the product URL
            getSetProductURL();

            // Start download of prouct information
            try {
                downloadProductTask = new DownloadProductTask(view, ProductURL.getInstance().getProductURL());

                Timer.getInstance().startTimer();
                product = downloadProductTask.execute().get();
                Timer.getInstance().stopTimer();
                Timer.getInstance().logElapsedTime("downloadProductTask");

                Timer.getInstance().startTimer();

                if (product == null) {
                    startCard = createStarterCard("Product Not Found", null);
                    cardPresenters = new ArrayList<CardPresenter>();
                    cardPresenters.add(startCard);
                }
                else {

                    componentList = product.getComponents();
                    instructionList = product.getInstructions();

                    startCard = createStarterCard(product.getName(), product.getImage());
                    componentCards = setComponentList(componentList);
                    instructionCards = setInstructionList(instructionList);

                    setCardPositions(1, 1 + componentCards.size());

                    cardPresenters = componentCards;
                    cardPresenters.addAll(instructionCards);
                    cardPresenters.add(0, startCard);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            // return card list
            return cardPresenters;
    }

    private void getSetProductURL() {
        ParsedResult parsedResult = getParsedResult();
        String productID = parsedResult.getDisplayResult();
        ProductURL.getInstance().setProductURL(productID);
    }

    private List<CardPresenter> setComponentList(List<Components> componentList)
    {
        List<CardPresenter> cards1 = new ArrayList<CardPresenter>();
        int progress=0, total;

        total = componentList.size();

        for(Components component : componentList) {
            CardPresenter cardPresenter1 = new CardPresenter();

            progress++;

            byte[] image = component.getImage();
            if(image != null)
                cardPresenter1.setByteArray(image);

            cardPresenter1.setText(component.getName());

            cardPresenter1.setFooter("Components");
            cardPresenter1.setTimeStamp(String.valueOf(progress) + "/" + String.valueOf(total));

            cards1.add(cardPresenter1);
        }

        return cards1;
    }

    private List<CardPresenter> setInstructionList(List<Instructions> instructionList)
    {
        List<CardPresenter> cards1 = new ArrayList<CardPresenter>();
        int progress=0, total;

        total = instructionList.size();

        for(Instructions instruction : instructionList) {
            CardPresenter cardPresenter1 = new CardPresenter();

            progress++;

            byte[] image = instruction.getImage();
            if(image != null)
                cardPresenter1.setByteArray(image);

            if(instruction.getInstruction() != null)
                cardPresenter1.setText(instruction.getInstruction());

            cardPresenter1.setFooter("Instructions");
            cardPresenter1.setTimeStamp(String.valueOf(progress) + "/" + String.valueOf(total));

            cards1.add(cardPresenter1);
        }

        return cards1;
    }

    private CardPresenter createStarterCard(String name, byte[] image)
    {
        CardPresenter cd = new CardPresenter();
        cd.setTitleCard();
        cd.setText(name.toUpperCase());
        if(image != null)
        {
            cd.setByteArray(image);
        }

        return cd;
    }

    private void splitTextCard(List<CardPresenter> cds, String instruction, int progress, int partProgress, String timeStamp)
    {
        int pos = cds.size();
        CardPresenter cd = new CardPresenter();

        cd.setTimeStamp(
                (!isComponent)   ?
                String.valueOf(progress) + "(" + (char) partProgress++ + ")" + timeStamp   :
                "(" + (char) partProgress++ + ")");

        cd.setFooter(
                (!isComponent)   ?
                "Instructions"   :
                "Components");

        if(instruction.length() > 170)
        {
            String instr = instruction.substring(0, 170);
            splitTextCard(cds, instruction.substring(170), progress, partProgress, timeStamp);
            cds.add(pos, cd.setText(instr));
        }
        else if(countComponents(instruction) > 6)
        {
            int lineBreakIndex = findNthOccurence(instruction, "\n", 5);
            String instr = instruction.substring(0, lineBreakIndex);
            splitTextCard(cds, instruction.substring(lineBreakIndex+1), progress, partProgress, timeStamp);
            cds.add(pos, cd.setText(instr));
        }
        else
        {
            cds.add(pos, cd.setText(instruction));
        }
    }

    private int findNthOccurence(String string, String substring, int n)
    {
        int index = string.indexOf(substring, 0);
        while (n-- > 0 && index != -1)
            index = string.indexOf(substring, index+1);
        return index;
    }

    private int countComponents(String components)
    {
        return components.length() - components.replace("\n", "").length();
    }

    private void setCardPositions(int comp, int instr)
    {
        componentPosition = comp;
        instructionPosition = instr;
    }

    @Override
    public int getComponentPosition()
    {
        return componentPosition;
    }

    @Override
    public int getInstructionPosition()
    {
        return instructionPosition;
    }
}
