package com.aliosman.asyasporcenter.Values;

import android.content.Context;
import android.content.SharedPreferences;

public class preferences {
    private String ayar="ayar";
    private String Dukkan="dukkan";
    private Context c;
    public preferences(Context context){
        c=context;
    }

    public void setDukkan(String dukkan){
        SharedPreferences.Editor editor=c.getSharedPreferences(ayar,Context.MODE_PRIVATE).edit();
        editor.putString(Dukkan,dukkan);
        editor.commit();
    }
    public String getDukkan(){
        return c.getSharedPreferences(ayar,Context.MODE_PRIVATE).getString(Dukkan,"");
    }
}
