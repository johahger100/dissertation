package com.github.barcodeeye;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by jhager on 2015-03-24.
 */

public class RandomizeEnglishText {

    private ArrayList<Double> doubleList;
    private List<String> alph;

    public RandomizeEnglishText(){

        // http://www.data-compression.com/english.html

        doubleList = new ArrayList<Double>();
        doubleList.add(0.0651738);                          // A
        doubleList.add(doubleList.get(0) + 0.0124248);      // B
        doubleList.add(doubleList.get(1) + 0.0217339);      // C
        doubleList.add(doubleList.get(2) + 0.0349835);      // D
        doubleList.add(doubleList.get(3) + 0.1041442);      // E
        doubleList.add(doubleList.get(4) + 0.0197881);      // F
        doubleList.add(doubleList.get(5) + 0.0158610);      // G
        doubleList.add(doubleList.get(6) + 0.0492888);      // H
        doubleList.add(doubleList.get(7) + 0.0558094);      // I
        doubleList.add(doubleList.get(8) + 0.0009033);      // J
        doubleList.add(doubleList.get(9) + 0.0050529);      // K
        doubleList.add(doubleList.get(10) + 0.0331490);     // L
        doubleList.add(doubleList.get(11) + 0.0202124);     // M
        doubleList.add(doubleList.get(12) + 0.0564513);     // N
        doubleList.add(doubleList.get(13) + 0.0596302);     // O
        doubleList.add(doubleList.get(14) + 0.0137645);     // P
        doubleList.add(doubleList.get(15) + 0.0008606);     // Q
        doubleList.add(doubleList.get(16) + 0.0497563);     // R
        doubleList.add(doubleList.get(17) + 0.0515760);     // S
        doubleList.add(doubleList.get(18) + 0.0729357);     // T
        doubleList.add(doubleList.get(19) + 0.0225134);     // U
        doubleList.add(doubleList.get(20) + 0.0082903);     // V
        doubleList.add(doubleList.get(21) + 0.0171272);     // W
        doubleList.add(doubleList.get(22) + 0.0013692);     // X
        doubleList.add(doubleList.get(23) + 0.0145984);     // Y
        doubleList.add(doubleList.get(24) + 0.0007836);     // Z
        doubleList.add(doubleList.get(25) + 0.1918182);     // _

        alph = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", " ");
    }

    private double randfrom(double min, double max)
    {
        Random rand = new Random();
        double range = (max - min);
        return min + range * rand.nextDouble();
    }


    private String getChar(int pos, double rand)
    {
        if(rand <= doubleList.get(pos) || pos+1 == alph.size())
            return alph.get(pos);

        return getChar(pos+1, rand);
    }

    public String randchar() {
        double rand = randfrom(0, 1);
        return getChar(0, rand);
    }
}