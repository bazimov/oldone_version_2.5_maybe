package com.ilmnuri.com.model;

import java.util.ArrayList;


public class Global {

    private static Global instance;
    ArrayList<AlbumModel> arrayList;

    public void setArrayList(ArrayList<AlbumModel> arrayList) {
        this.arrayList = arrayList;
    }

    public Global () {
        instance = this;
        arrayList = new ArrayList<>();
    }
    public static  Global getInstance() {
        if (instance == null) {
            return new Global();
        }
        return instance;
    }

    public ArrayList<AlbumModel> getAlbums(String categoryName) {
        ArrayList<AlbumModel> arrAlbums = new ArrayList<>();
        for (AlbumModel albumModel : arrayList) {
            if (albumModel.getCategory().equals(categoryName)) {
                arrAlbums.add(albumModel);
            }
        }
        return arrAlbums;
    }
}
