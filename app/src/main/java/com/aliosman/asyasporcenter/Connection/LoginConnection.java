package com.aliosman.asyasporcenter.Connection;

import android.support.annotation.NonNull;
import com.aliosman.asyasporcenter.Values.Sube;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginConnection {

    private DatabaseReference LogindatabaseReference;
    public LoginConnection(){
        LogindatabaseReference = FirebaseDatabase.getInstance().getReference("Subeler");
    }
    public void kontrol(final String sifre, final ILogin login) {
        LogindatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Sube kullanici=null;
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Sube sube = item.getValue(Sube.class);
                    if (sifre.equals(sube.getSifre()))

                        kullanici=sube;
                }
                login.Login(kullanici);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void GetSubes(final IListSube listSube){
        final List<String> items=new ArrayList<>();
        items.add("Hepsi");
        LogindatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    items.add(item.getValue(Sube.class).getAdi());
                }
                listSube.List(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}