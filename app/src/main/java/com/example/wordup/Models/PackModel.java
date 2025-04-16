package com.example.wordup.Models;

public class PackModel {
    String packName;
    int image;

    public PackModel(String packName, int image) {
        this.packName = packName;
        this.image = image;
    }

    public String getPackName() {
        return packName;
    }

    public int getImage() {
        return image;
    }
}
