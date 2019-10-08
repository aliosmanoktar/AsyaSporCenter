package com.aliosman.asyasporcenter.Values;

public class Satis {
    private float Tutar;
    private int Adet;
    private SatisType SatisType;
    private String Dukkan;
    private String Saat="saat";
    private String Tarih;
    public Satis(){

    }

    public Satis(float tutar, int adet, SatisType satisType, String dukkan) {
        Tutar = tutar;
        Adet = adet;
        SatisType = satisType;
        Dukkan = dukkan;
    }

    public String getTarih() {
        return Tarih;
    }

    public void setTarih(String tarih) {
        Tarih = tarih;
    }

    public float getTutar() {
        return Tutar;
    }

    public void setTutar(float tutar) {
        Tutar = tutar;
    }

    public int getAdet() {
        return Adet;
    }

    public void setAdet(int adet) {
        Adet = adet;
    }

    public SatisType getSatisType() {
        return SatisType;
    }

    public void setSatisType(SatisType satisType) {
        SatisType = satisType;
    }

    public String getDukkan() {
        return Dukkan;
    }

    public void setDukkan(String dukkan) {
        Dukkan = dukkan;
    }

    public String getSaat() {
        return Saat;
    }

    public void setSaat(String saat) {
        Saat = saat;
    }
}
