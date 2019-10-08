package com.aliosman.asyasporcenter.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aliosman.asyasporcenter.Fragment.fragment_urun;
import com.aliosman.asyasporcenter.Fragment.fragment_sepet;
import com.aliosman.asyasporcenter.Values.IUrunSearch;

public class satis_tabbed_adapter extends FragmentStatePagerAdapter {

    private fragment_urun fr_urun=null;
    private fragment_sepet fr_sepet=null;
    public IUrunSearch search;
    public satis_tabbed_adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i==0){
            if (fr_urun==null){
                if (fr_sepet==null)
                    fr_sepet=new fragment_sepet();
                fr_urun=new fragment_urun();
                search=fr_urun;
            }
            return fr_urun;
        }
        else if (i==1){
            if (fr_sepet==null)
                fr_sepet= new fragment_sepet();
            return fr_sepet;
        }
        else return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
