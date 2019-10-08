package com.aliosman.asyasporcenter.Connection;

import android.support.annotation.NonNull;
import android.util.Log;

import com.aliosman.asyasporcenter.Values.Kasa;
import com.aliosman.asyasporcenter.Values.Satis;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KasaConnection {
    private DatabaseReference KasadatabaseReference;
    private final String GunFormat ="dd\\MM\\yyyy";
    private final String TAG = getClass().getName();

    public KasaConnection(){
        KasadatabaseReference = FirebaseDatabase.getInstance().getReference("Kasa");
    }
    public void getKasa(IListKasa list){
        getKasa(getZaman(GunFormat),list);
    }
    public void getKasa(String tarih, final IListKasa list){
        final List<Kasa> kasaListe = new ArrayList<>();
        final DatabaseReference temp =KasadatabaseReference.child(tarih);
        temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kasaListe.clear();
                for(DataSnapshot item:dataSnapshot.getChildren()){
                    Log.e(TAG, "onDataChange: "+item.getKey() );
                    kasaListe.add(item.getValue(Kasa.class));
                }
                list.KasaList(kasaListe);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getKasaOnly(String tarih, final IListKasa list){
        final List<Kasa> kasaListe = new ArrayList<>();
        final DatabaseReference temp =KasadatabaseReference.child(tarih);
        temp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kasaListe.clear();
                for(DataSnapshot item:dataSnapshot.getChildren()){
                    Log.e(TAG, "onDataChange: "+item.getKey() );
                    kasaListe.add(item.getValue(Kasa.class));
                }
                list.KasaList(kasaListe);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void InsertKasa(Kasa kasa, final IUploadFinish finish){
        if (finish!=null)
            finish.Start();
        final  DatabaseReference temp = KasadatabaseReference.child(getZaman(GunFormat));
        String UploadID = temp.push().getKey();
        kasa.setID(UploadID);
        temp.child(UploadID).setValue(kasa).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (finish!=null)
                    finish.Upload(task.isSuccessful());
            }
        });
    }
    public void InsertKasa(String Tarih,Kasa kasa, final IUploadFinish finish){
        if (finish!=null)
            finish.Start();
        final  DatabaseReference temp = KasadatabaseReference.child(Tarih);
        String UploadID = temp.push().getKey();
        kasa.setID(UploadID);
        temp.child(UploadID).setValue(kasa).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (finish!=null)
                    finish.Upload(task.isSuccessful());
            }
        });
    }

    public void UpdateKasa(Kasa kasa, final IUploadFinish finish){
        if (finish!=null)
            finish.Start();
        final  DatabaseReference temp = KasadatabaseReference.child(getZaman(GunFormat));
        String UploadID = kasa.getID();
        temp.child(UploadID).setValue(kasa).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (finish!=null)
                    finish.Upload(task.isSuccessful());
            }
        });
    }


    private String getZaman(String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(Calendar.getInstance().getTime());
    }
}
