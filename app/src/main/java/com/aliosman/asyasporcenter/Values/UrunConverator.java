package com.aliosman.asyasporcenter.Values;

import java.util.Comparator;

public class UrunConverator implements Comparator<Urun> {
    @Override
    public int compare(Urun o1, Urun o2) {
        return (o1.getUrunAdi().compareTo(o2.getUrunAdi()));
    }
}
