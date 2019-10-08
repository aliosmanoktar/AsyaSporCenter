package com.aliosman.asyasporcenter.Connection;

import android.support.annotation.NonNull;
import android.util.Log;

import com.aliosman.asyasporcenter.Values.Satis;
import com.aliosman.asyasporcenter.Values.Sube;
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

public class SatisConnection {
    private DatabaseReference SatisdatabaseReference;
    private final String AyFormat="MM\\yyyy";
    private final String GunFormat ="dd\\MM\\yyyy";
    private final String SaatFormat="HH:mm";
    private final String TAG = getClass().getName();
    public SatisConnection(){
        SatisdatabaseReference = FirebaseDatabase.getInstance().getReference("Satislar");

    }

    public void InsertSatis(Satis satis,final IUploadFinish finish){
        finish.Start();
        final  DatabaseReference temp = SatisdatabaseReference.child(getZaman(AyFormat)).child(getZaman(GunFormat));
        String UploadID = temp.push().getKey();
        satis.setSaat(getZaman(SaatFormat));
        temp.child(UploadID).setValue(satis);
        UpdateCount(satis.getAdet(),finish);
    }

    private void UpdateCount(final int adet,final IUploadFinish finish){
        final DatabaseReference temp =SatisdatabaseReference.child(getZaman(AyFormat)).child(getZaman(GunFormat));
        temp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("count").exists()){

                    Object obj = dataSnapshot.child("count").getValue();
                    int count=Integer.parseInt(obj.toString());
                    temp.child("count").setValue(count+adet);
                }else{

                    temp.child("count").setValue(adet);

                }
                if (finish!=null)
                    finish.Upload(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (finish!=null)
                    finish.Upload(false);
            }
        });
    }

    private String getZaman(String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public void getSatislar(String tarih, String gun, final ISatisList list){
        final List<Satis> satis = new ArrayList<>();
        final DatabaseReference temp =SatisdatabaseReference.child(tarih).child(gun);
        temp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot!=null){
                    for(DataSnapshot item:dataSnapshot.getChildren()){
                        if (!item.getKey().equals("count")) {
                            satis.add(item.getValue(Satis.class));
                        }
                    }
                    Log.e(TAG, "onDataChange: "+satis.size() );
                    list.List(satis);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void getSatislar(String tarih, final ISatisList lists){
        Log.e(TAG, "getSatisCount: Tarih == "+tarih );
        final List<Satis> satis = new ArrayList<>();
        final DatabaseReference temp =SatisdatabaseReference.child(tarih);
        temp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot!=null){
                    for (DataSnapshot item:dataSnapshot.getChildren()) {
                        if (item!=null){
                            for ( DataSnapshot child: item.getChildren()) {
                                if (!child.getKey().equals("count")){
                                    Satis sa= child.getValue(Satis.class);
                                    sa.setTarih(item.getKey());
                                    satis.add(sa);
                                }
                            }
                        }

                    }
                }
                lists.List(satis);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}