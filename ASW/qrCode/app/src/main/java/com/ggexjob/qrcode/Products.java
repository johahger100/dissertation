package com.ggexjob.qrcode;

import java.util.List;

/**
 * Created by rhoorn on 2015-03-11.
 */
public class Products {
    private int Id;
    private String Name;
    private String Description;
    private byte[] Image;
    private List<Components> Components;
    private List<Instructions> Instructions;

    public int getId() { return Id; }
    public String getName() { return Name; }
    public String getDescription() { return Description; }
    public byte[] getImage() { return Image; }
    public List<Components> getComponents() { return Components; }
    public List<Instructions> getInstructions() { return Instructions; }
}
