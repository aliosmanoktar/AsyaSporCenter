package com.aliosman.asyasporcenter.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.aliosman.asyasporcenter.Adapter.adapter_kasa;
import com.aliosman.asyasporcenter.Connection.IListKasa;
import com.aliosman.asyasporcenter.Connection.IUploadFinish;
import com.aliosman.asyasporcenter.Connection.KasaConnection;
import com.aliosman.asyasporcenter.Fragment.kasa_add_bottom;
import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.Kasa;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class Kasa_Ekrani extends AppCompatActivity implements IListKasa,SlyCalendarDialog.Callback {
    private ListView list;
    private TextView txt_toplam;
    private final String GunFormat ="dd\\MM\\yyyy";
    Dialog dialog=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasa);
        Button ekle_btn = findViewById(R.id.kasa_ekran_btn_ekle);
        txt_toplam=findViewById(R.id.txt_toplam);
        ekle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new kasa_add_bottom().show(getSupportFragmentManager(),"bottom_kasa");
            }
        });
        list=findViewById(R.id.kasa_ekran_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Kasa kasa= (Kasa)view.getTag();
                if(kasa.getAdi().equals("Devir Kasa"))
                    return;
                kasa_add_bottom kasa_bf=new kasa_add_bottom();
                Bundle b = new Bundle();
                b.putSerializable("kasa_item",kasa);
                kasa_bf.setArguments(b);
                kasa_bf.show(getSupportFragmentManager(),"bottom_kasa");
            }
        });
        dialog=new AwesomeProgressDialog(Kasa_Ekrani.this)
                .setTitle("Lütfen Bekleyiniz")
                .setMessage("Veriler Alınıyor")
                .setCancelable(false)
                .show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new KasaConnection().getKasa(this);
    }
    private String TAG =getClass().getName();
    @Override
    public void KasaList(List<Kasa> kasaList) {
        if (dialog!=null)
            dialog.dismiss();
        txt_toplam.setText(getToplam(kasaList)+" ₺");
        list.setAdapter(new adapter_kasa(getApplicationContext(),R.layout.layout_kasa,kasaList));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kasa,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        else if (item.getItemId()==R.id.menu_kasa){
            new KasaConnection().getKasaOnly(getZaman(Calendar.getInstance().getTime()), new IListKasa() {
                @Override
                public void KasaList(List<Kasa> kasaList) {
                    Calendar c= Calendar.getInstance();
                    c.add(Calendar.DATE,1);
                    new KasaConnection().InsertKasa(getZaman(c.getTime()), new Kasa("Devir Kasa", getToplam(kasaList)), new IUploadFinish() {
                        @Override
                        public void Start() {
                            dialog=new AwesomeProgressDialog(Kasa_Ekrani.this)
                                    .setTitle("Lütfen Bekleyiniz")
                                    .setCancelable(false)
                                    .setMessage("Devir Kasa Alınıyor")
                                    .show();
                        }

                        @Override
                        public void Upload(boolean sonuc) {
                            if (dialog!=null)
                                dialog.dismiss();
                            dialog=new AwesomeSuccessDialog(Kasa_Ekrani.this)
                                    .setTitle("Başarılı")
                                    .setMessage("Devir Kasa işlemi başarılı")
                                    .setDoneButtonClick(new Closure() {
                                        @Override
                                        public void exec() {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                    });
                }
            });
        }else if (item.getItemId()==R.id.menu_kasa_list){
            new SlyCalendarDialog()
                    .setSingle(true)
                    .setCallback(Kasa_Ekrani.this)
                    .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");
        }
        return true;
    }
    private String getZaman(Date date){
        DateFormat dateFormat = new SimpleDateFormat(GunFormat);
        return dateFormat.format(date);
    }
    private float getToplam(List<Kasa> kasaList){
        float toplam=0;
        for (Kasa item :kasaList){
            toplam+=item.getFiyat();
        }
        return toplam;
    }
    @Override
    public void onCancelled() {

    }
    @Override
    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
        Intent i = new Intent(getApplicationContext(),Kasa_List_Ekrani.class);
        i.putExtra("tarih",getZaman(firstDate.getTime()));
        startActivity(i);
    }
}