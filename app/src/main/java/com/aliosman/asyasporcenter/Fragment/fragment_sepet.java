package com.aliosman.asyasporcenter.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.crashlytics.android.Crashlytics;
import com.aliosman.asyasporcenter.Adapter.Helper.ReyclerItemTouchHelper;
import com.aliosman.asyasporcenter.Adapter.Helper.ReyclerItemTouchHelperListener;
import com.aliosman.asyasporcenter.Adapter.adapter_sepet;
import com.aliosman.asyasporcenter.Connection.IUploadFinish;
import com.aliosman.asyasporcenter.Connection.SatisConnection;
import com.aliosman.asyasporcenter.Connection.UrunConnection;
import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.GlobalBus;
import com.aliosman.asyasporcenter.Values.Satis;
import com.aliosman.asyasporcenter.Values.SatisType;
import com.aliosman.asyasporcenter.Values.Urun;
import com.aliosman.asyasporcenter.Values.preferences;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class fragment_sepet extends Fragment implements ReyclerItemTouchHelperListener{
    private RecyclerView recyclerView;
    private List<Urun> urunler;
    private List<Urun> orj_urun;
    private adapter_sepet adapter;
    private RelativeLayout root_view;
    private String TAG =getClass().getName();
    private EditText ucret;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_sepet,container,false);
        GlobalBus.getBus().register(this);
        recyclerView=view.findViewById(R.id.sepet_recyler);
        urunler = new ArrayList<>();
        adapter=new adapter_sepet(urunler);
        root_view=view.findViewById(R.id.sepet_root_view);
        ItemTouchHelper.SimpleCallback item = new ReyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(item).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 0));
        Button satis_kart = view.findViewById(R.id.btn_satis_kart);
        satis_kart.setOnClickListener(btn_satis_kart);
        Button satis_nakit = view.findViewById(R.id.btn_satis_nakit);
        satis_nakit.setOnClickListener(btn_satis_nakit);
        ucret=view.findViewById(R.id.edit_ucret);
        return view;
    }

    View.OnClickListener btn_satis_nakit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Satis(SatisType.Nakit);
        }
    };
    View.OnClickListener btn_satis_kart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Satis(SatisType.Kart);
        }
    };
    private void Satis(SatisType type){
        final Dialog progress = new AwesomeProgressDialog(getContext())
                .setTitle("Bekleyiniz")
                .setMessage("Veriler Sunucu Üzerine Kaydediliyor")
                .show();
        UrunConnection connection = new UrunConnection();
        int toplam=0;
        for (Urun item : orj_urun){
            int index = getUrunID(item);
            if (index!=-1){
                Gson g = new Gson();
                Urun value = urunler.get(index);
                Log.e(TAG, "onClick: Value = "+new Gson().toJson(value));
                Urun item2= g.fromJson(g.toJson(item),Urun.class);
                item.setCount40(item.getCount40()-value.getCount40());
                item.setCount41(item.getCount41()-value.getCount41());
                item.setCount42(item.getCount42()-value.getCount42());
                item.setCount43(item.getCount43()-value.getCount43());
                item.setCount44(item.getCount44()-value.getCount44());
                item.setCount45(item.getCount45()-value.getCount45());
                if (item2.getCount40()<item.getCount40() || item2.getCount41()<item.getCount41() || item2.getCount42()<item.getCount42() || item2.getCount43()<item.getCount43() || item2.getCount44()<item.getCount44() || item2.getCount45()<item.getCount45()){
                    try {
                        Crashlytics.log("Upload Urun : "+new Gson().toJson(item));
                        Crashlytics.log("Orj Urun : "+new Gson().toJson(item2));
                        Crashlytics.logException(new Exception("Satış Hatası Oldu"));
                    }catch (Exception ex){

                    }
                }
                toplam+=value.getCount40()+value.getCount41()+value.getCount42()+value.getCount43()+value.getCount44()+value.getCount45();
                connection.UpdataUrun(item,null);
            }
        }
        SatisConnection satisConnection=new SatisConnection();
        Log.e(TAG, "Satis: Eklenen Satis"+toplam+" "+getUcret()+" ₺");
        satisConnection.InsertSatis(new Satis(getUcret(),toplam,type,new preferences(getContext()).getDukkan()),new  IUploadFinish(){
            Dialog dialog;
            @Override
            public void Start() {

            }

            @Override
            public void Upload(boolean sonuc) {
                if (progress!=null)
                    progress.dismiss();
                if (sonuc)
                {
                    dialog=new AwesomeSuccessDialog(getContext())
                            .setTitle("Başarılı")
                            .setMessage("Ayarlar Başarılı Bir Şekilde Kaydedildi")
                            .setDoneButtonClick(new Closure() {
                                @Override
                                public void exec() {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    },1000);
                }else {
                    dialog=new AwesomeErrorDialog(getContext())
                            .setTitle("Hata")
                            .setMessage("İşlem Sırasında Hata Gerçekleşti")
                            .setErrorButtonClick(new Closure() {
                                @Override
                                public void exec() {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
                urunler.clear();
                SetAdapter(urunler);
                ucret.setText("");
            }
        });

    }
    private float getUcret(){
        float tutar=0;
        try{
            tutar=Float.parseFloat(ucret.getText().toString());
        }catch (Exception ex){
            tutar=0;
        }
        return tutar;
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, final int position) {
        final Urun item = adapter.getItem(position);
        adapter.removeItem(position);
        Snackbar snackbar= Snackbar.make(root_view,"Ürün Silindi",Snackbar.LENGTH_LONG);
        snackbar.setAction("Geri Al", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.restoreItem(item,position);
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorHeader));
        snackbar.show();
    }
    @Subscribe
    public void AddSepet(Urun value){
        Log.e(TAG, "AddSeper: " );
        int index = getUrunID(value);
        if (index!=-1){
            urunler.get(index).setCount40(urunler.get(index).getCount40()+value.getCount40());
            urunler.get(index).setCount41(urunler.get(index).getCount41()+value.getCount41());
            urunler.get(index).setCount42(urunler.get(index).getCount42()+value.getCount42());
            urunler.get(index).setCount43(urunler.get(index).getCount43()+value.getCount43());
            urunler.get(index).setCount44(urunler.get(index).getCount44()+value.getCount44());
            urunler.get(index).setCount45(urunler.get(index).getCount45()+value.getCount45());
        }else
            urunler.add(value);
        SetAdapter(urunler);
    }

    private int getUrunID(Urun value){
        for (int i = 0;i<urunler.size();i++)
            if (urunler.get(i).getId().equals(value.getId()))
                return i;
            return -1;
    }

    private void SetAdapter(List<Urun> urunler){
        adapter=new adapter_sepet(urunler);
        recyclerView.setAdapter(adapter);
    }
    @Subscribe
    public void SetUrun(List<Urun> uruns){
        Log.e(TAG, "SetUrun: " );
        this.orj_urun=uruns;
    }
    /*@Override
    public void setOrgUrun(List<Urun> uruns) {
        Log.e(TAG, "setOrgUrun: ");
        this.orj_urun = uruns;
    }*/
}
