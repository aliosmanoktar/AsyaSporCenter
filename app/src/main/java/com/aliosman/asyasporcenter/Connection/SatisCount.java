package com.aliosman.asyasporcenter.Connection;

public class SatisCount {
    private String ID;
    private int count;

    public SatisCount(String ID, int count) {
        this.ID = ID;
        this.count = count;
    }

    public SatisCount setID(String ID) {
        this.ID = ID;
        return this;
    }


    public SatisCount setCount(int count) {
        this.count = count;
        return this;
    }

    public String getID() {
        return ID;
    }


    public int getCount() {
        return count;
    }
}
