package com.aliosman.asyasporcenter.Connection;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.aliosman.asyasporcenter.Values.Satis;
import com.aliosman.asyasporcenter.Values.Urun;
import com.aliosman.asyasporcenter.Values.UrunConverator;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UrunConnection {

    private DatabaseReference UrunlerdatabaseReference;
    private StorageReference storageReference;
    private String TAG = getClass().getName();

    public UrunConnection(){
        UrunlerdatabaseReference = FirebaseDatabase.getInstance().getReference("urunler");
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void UploadUrun(final Urun urun, Bitmap image, final IUploadFinish finish){
        if (finish!=null)
            finish.Start();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        final StorageReference reference = storageReference.child("urunler/"+System.currentTimeMillis()+".jpg");
        reference.putBytes(data)
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            try {
                                Crashlytics.logException(task.getException());
                            }catch (Exception ex){

                            }
                            throw task.getException();
                        }
                        return reference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    UploadUrun upload = UploadUrun.parse(urun,downloadUri.toString());
                    String UploadID= UrunlerdatabaseReference.push().getKey();
                    UrunlerdatabaseReference.child(UploadID).setValue(upload);
                    Log.e(TAG, "onComplete: "+UploadID);
                } else {

                }
                if (finish!=null)
                    finish.Upload(task.isSuccessful());
            }
        });
    }
    public void UploadUrun(final Urun urun, File image, final IUploadFinish finish){
        if (finish!=null)
            finish.Start();
        Log.e(TAG, "UploadUrun: File" );
        final StorageReference reference = storageReference.child("urunler/"+System.currentTimeMillis()+".jpg");
        reference.putFile(Uri.parse(image.toURI().toString()))
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            try {
                                Crashlytics.logException(task.getException());
                            }catch (Exception ex){

                            }
                            throw task.getException();
                        }
                        return reference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    UploadUrun upload = UploadUrun.parse(urun,downloadUri.toString());
                    String UploadID= UrunlerdatabaseReference.push().getKey();
                    UrunlerdatabaseReference.child(UploadID).setValue(upload);
                    Log.e(TAG, "onComplete: "+UploadID);
                } else {

                }
                if (finish!=null)
                    finish.Upload(task.isSuccessful());
            }
        });
    }
    public void UpdataUrun(Urun urun, final IUploadFinish finish){
        UploadUrun upload = UploadUrun.parse(urun,urun.getImagePath());
        Log.e(TAG, "UpdataUrun: "+urun.getId() );
        if (finish!=null)
            finish.Start();
        UrunlerdatabaseReference
                .child(urun.getId()).setValue(upload).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (finish!=null)
                    finish.Upload(true);
                Log.e(TAG, "onSuccess: " );
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (finish!=null)
                    finish.Upload(false);
                Log.e(TAG, "onFailure: " );
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Log.e(TAG, "onCanceled: " );
            }
        });
    }

    public void DeleteUrun(Urun urun){
        UrunlerdatabaseReference.child(urun.getId()).removeValue();
    }

    public void ListUrun(final IListUrun list){
        UrunlerdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Urun> uruns = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    UploadUrun urun =item.getValue(UploadUrun.class);
                    uruns.add(urun.parse().setId(item.getKey()));
                }
                Collections.sort(uruns,new UrunConverator());
                list.ListUrun(uruns);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}