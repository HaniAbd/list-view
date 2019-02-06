package com.example.hani.mylistview;

/**
 * Created by Hani on 13-Oct-17.
 */

public class Produits {
    private String Name;
    private int Amount;
    private int Img;
    private int Id;
    boolean Selected = false;

    //constracter

    public Produits(String name, int amount, int img, int id, boolean selected) {
        Name = name;
        Amount = amount;
        Img = img;
        Id = id;
        Selected=selected;
    }


    //Getter & Setter

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getImg() {
        return Img;
    }

    public void setImg(int img) {
        Img = img;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        this.Selected = selected;
    }
}