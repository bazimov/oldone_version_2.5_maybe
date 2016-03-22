package com.ilmnuri.com.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 3/5/2016.
 */
public class AlbumModel implements Serializable{
    String album, category;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getArrTrack() {
        return arrTrack;
    }

    public void setArrTrack(ArrayList<String> arrTrack) {
        this.arrTrack = arrTrack;
    }

    ArrayList<String> arrTrack;

}
