package com.example.googleplayssdkprj.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class KTStore {
    private KTLocation location;
    private String name;
    private String iconUrl;

    private Double rating;
    private boolean isOpened;


    private Double photoHeight;
    private Double photoWidth;
    private String photoUrl;

    public KTStore(KTLocation location, String name, String iconUrl, Double rating, boolean isOpened, Double photoHeight, Double photoWidth, String photoUrl) {
        this.location = location;
        this.name = name;
        this.iconUrl = iconUrl;
        this.rating = rating;
        this.isOpened = isOpened;
        this.photoHeight = photoHeight;
        this.photoWidth = photoWidth;
        this.photoUrl = photoUrl;
    }

    public KTStore() {
    }


    public Double getPhotoWidth() {
        return photoWidth;
    }

    public void setPhotoWidth(Double photoWidth) {
        this.photoWidth = photoWidth;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Double getPhotoHeight() {
        return photoHeight;
    }

    public void setPhotoHeight(Double photoHeight) {
        this.photoHeight = photoHeight;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
    //<!--가게 이름, 가게 사진, 가게 레이팅, 열려있는지, 가게 좌표 -->

    public KTLocation getLocation() {
        return location;
    }

    public void setLocation(KTLocation location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

}
