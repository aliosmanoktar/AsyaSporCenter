package com.aliosman.asyasporcenter.Values;

import java.io.Serializable;

public class Kasa implements Serializable {
    private String ID;
    private String Adi;
    private float Fiyat;

    public Kasa(){

    }

    public Kasa(String Adi, float Fiyat){
        this.Adi = Adi;
        this.Fiyat = Fiyat;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAdi() {
        return Adi;
    }

    public void setAdi(String adi) {
        Adi = adi;
    }

    public float getFiyat() {
        return Fiyat;
    }

    public void setFiyat(float fiyat) {
        Fiyat = fiyat;
    }

}
