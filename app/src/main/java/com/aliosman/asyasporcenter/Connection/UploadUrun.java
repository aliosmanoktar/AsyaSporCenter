package com.aliosman.asyasporcenter.Connection;

import com.aliosman.asyasporcenter.Values.Urun;

public class UploadUrun{
    private String UrunAdi,ImagePath;
    private int count40=0,count41=0,count42=0,count43=0,count44=0,count45=0;
    private float fiyat;
    public UploadUrun(){

    }

    public UploadUrun(String urunAdi, String imagePath, int count40, int count41, int count42, int count43, int count44, int count45, float fiyat) {
        UrunAdi = urunAdi;
        ImagePath = imagePath;
        this.count40 = count40;
        this.count41 = count41;
        this.count42 = count42;
        this.count43 = count43;
        this.count44 = count44;
        this.count45 = count45;
        this.fiyat = fiyat;
    }

    public String getUrunAdi() {
        return UrunAdi;
    }

    public int getCount40() {
        return count40;
    }

    public int getCount41() {
        return count41;
    }

    public int getCount42() {
        return count42;
    }

    public int getCount43() {
        return count43;
    }

    public int getCount44() {
        return count44;
    }

    public int getCount45() {
        return count45;
    }

    public float getFiyat() {
        return fiyat;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public void setUrunAdi(String urunAdi) {
        UrunAdi = urunAdi;
    }

    public void setCount40(int count40) {
        this.count40 = count40;
    }

    public void setCount41(int count41) {
        this.count41 = count41;
    }

    public void setCount42(int count42) {
        this.count42 = count42;
    }

    public void setCount43(int count43) {
        this.count43 = count43;
    }

    public void setCount44(int count44) {
        this.count44 = count44;
    }

    public void setCount45(int count45) {
        this.count45 = count45;
    }

    public void setFiyat(float fiyat) {
        this.fiyat = fiyat;
    }

    public static UploadUrun parse(Urun urun, String ImagePath){
        return new UploadUrun(urun.getUrunAdi(),ImagePath,urun.getCount40(),urun.getCount41(),urun.getCount42(),urun.getCount43(),urun.getCount44(),urun.getCount45(),urun.getFiyat());
    }

    public Urun parse(){
        return new Urun()
                .setUrunAdi(getUrunAdi())
                .setFiyat(getFiyat())
                .setCount40(getCount40())
                .setCount41(getCount41())
                .setCount42(getCount42())
                .setCount43(getCount43())
                .setCount44(getCount44())
                .setCount45(getCount45())
                .setImagePath(getImagePath());
    }
}