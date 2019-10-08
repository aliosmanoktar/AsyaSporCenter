package com.aliosman.asyasporcenter.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import com.aliosman.asyasporcenter.R;
import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import io.fabric.sdk.android.Fabric;

public class AnaEkran extends AppCompatActivity {
    String TAG = getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(new Fabric.Builder(this)
                    .kits(new Crashlytics())
                    .debuggable(true)
                    .build());
        setContentView(R.layout.activity_ana_ekran);
        try {
            Picasso picasso = new Picasso.Builder(this).downloader(new OkHttp3Downloader(getCacheDir(), 250000000)).build();
            Picasso.setSingletonInstance(picasso);
        }catch (Exception ex){
            Log.e(TAG, "onCreate: "+ex.toString() );
        }
        Button btn_satis = findViewById(R.id.btn_satis);
        Button btn_stok = findViewById(R.id.btn_stok);
        Button btn_rapor = findViewById(R.id.btn_rapor);
        Button btn_kasa = findViewById(R.id.btn_kasa);

        btn_kasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AnaEkran.this);
                builder.setTitle("Kasa Şifresi");
                final EditText input = new EditText(AnaEkran.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);
                builder.setPositiveButton("Giriş", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        if (m_Text.equals("12012011")){
                            startActivity(new Intent(getApplicationContext(),Kasa_Ekrani.class));
                        }
                        else {
                            dialog.cancel();
                        }
                    }
                });
                builder.setNegativeButton("Çıkış", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        btn_rapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Rapor_ekrani.class));
            }
        });
        btn_satis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),satis_ekran.class));
            }
        });
        btn_stok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AnaEkran.this);
                builder.setTitle("Stok Şifresi");
                final EditText input = new EditText(AnaEkran.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);
                builder.setPositiveButton("Giriş", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        if (m_Text.equals("1234")){
                            startActivity(new Intent(getApplicationContext(),stok_Ekrani.class));
                        }
                        else {
                            dialog.cancel();
                        }
                    }
                });
                builder.setNegativeButton("Çıkış", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hakkinda,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu_hakkinda){
            startActivity(new Intent(getApplicationContext(),Hakkinda_Ekrani.class));
        }
        return true;
    }
}
