package com.github.barcodeeye;

import java.util.List;

/**
 * Created by rhoorn on 2015-03-10.
 */
public class Products {
    private int Id;
    private String Name;
    private String Description;
    private List<Components> Components;
    private List<Instructions> Instructions;
    private byte[] Image;

    public int getId() { return Id; }
    public String getName() { return Name; }
    public String getDescription() { return Description; }
    public List<Components> getComponents() { return Components; }
    public List<Instructions> getInstructions() { return Instructions; }
    public byte[] getImage() { return Image; }
}
