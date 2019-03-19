package com.example.hani.mylistview;

import android.graphics.Bitmap;

/**
 * Created by Hani on 05-Feb-19.
 */

public class Films {
    private String name;
    private String gender;
    private int year;
    private Bitmap image;
    private int id;
    boolean Selected = false;

    //constracter

    public Films(String name, int year, String gender, Bitmap image, int id, boolean selected) {
        this.name = name;
        this.gender = gender;
        this.year = year;
        this.image = image;
        this.id = id;
        this.Selected=selected;
    }
    // lourem pexil pour limages


    //Getter & Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        year = year;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        this.Selected = selected;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        gender = gender;
    }
}