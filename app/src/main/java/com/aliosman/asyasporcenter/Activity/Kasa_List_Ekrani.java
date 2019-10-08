package com.aliosman.asyasporcenter.Activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.aliosman.asyasporcenter.Adapter.adapter_kasa;
import com.aliosman.asyasporcenter.Connection.IListKasa;
import com.aliosman.asyasporcenter.Connection.KasaConnection;
import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.Kasa;

import java.util.List;

public class Kasa_List_Ekrani extends AppCompatActivity implements IListKasa {
    private TextView txt_toplam;
    private ListView list;
    Dialog dialog;
    String TAG = getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasa__list__ekrani);
        String tarih = getIntent().getExtras().getString("tarih");
        Log.e(TAG, "onCreate: "+tarih);
        list=findViewById(R.id.kasa_ekran_listesi);
        txt_toplam=findViewById(R.id.txt_toplam);
        dialog=new AwesomeProgressDialog(Kasa_List_Ekrani.this)
                .setTitle("Lütfen Bekleyiniz")
                .setMessage("Veriler Alınıyor")
                .setCancelable(false)
                .show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new KasaConnection().getKasa(tarih,this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return true;
    }

    @Override
    public void KasaList(List<Kasa> kasaList) {
        if (dialog!=null)
            dialog.dismiss();
        txt_toplam.setText(getToplam(kasaList)+" ₺");
        list.setAdapter(new adapter_kasa(getApplicationContext(),R.layout.layout_kasa,kasaList));
    }
    private float getToplam(List<Kasa> kasaList){
        float toplam=0;
        for (Kasa item :kasaList){
            toplam+=item.getFiyat();
        }
        return toplam;
    }
}
