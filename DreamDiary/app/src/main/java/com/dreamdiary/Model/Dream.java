package com.dreamdiary.Model;

public class Dream {

    private int id;
    private String title;
    private String date;
    private String text;

    public Dream(int id, String tittle, String date, String text) {
        this.id = id;
        this.title = tittle;
        this.date = date;
        this.text = text;
    }

    public Dream(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String tittle) {
        this.title = tittle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
