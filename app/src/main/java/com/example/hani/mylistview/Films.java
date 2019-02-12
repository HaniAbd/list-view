package com.example.hani.mylistview;

/**
 * Created by Hani on 05-Feb-19.
 */

public class Films {
    private String Name;
    private String Gender;
    private int Date;
    private int Img;
    private int Id;
    boolean Selected = false;

    //constracter

    public Films(String name, int date, String gender, int img, int id, boolean selected) {
        Name = name;
        Gender = gender;
        Date = date;
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

    public int getDate() {
        return Date;
    }

    public void setDate(int Date) {
        Date = Date;
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

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}