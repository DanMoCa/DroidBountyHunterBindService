package com.example.dan14z.droidbountyhunterbindservice.model;

/**
 * Created by Dan14z on 11/09/2017.
 */

public class Fugitivo {
    private String mNombre;
    private String mStatus;
    private String mPhoto;

    public Fugitivo(String nombre, String status, String photo) {
        mNombre = nombre;
        mStatus = status;
        mPhoto = photo;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getNombre() {
        return mNombre;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getPhoto() {
        return mPhoto;
    }
}
