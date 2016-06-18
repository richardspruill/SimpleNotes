package com.simple.apps.simplenotes.Models;

import java.util.Date;

/**
 * Created by anthony on 15.06.16.
 */
public class Note {
    public static final String ID_KEY = "id_key";

    private String text;
    private String title;
    private Date dateCreated;
    private int id;

    public Note(String text, String title, Date dateCreated){
        this.text = text;
        this.title = title;
        this.dateCreated = dateCreated;
    }

    public Note(int id, String text, String title, Date dateCreated){
        this.id = id;
        this.text = text;
        this.title = title;
        this.dateCreated = dateCreated;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
