package com.aliosman.asyasporcenter.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.aliosman.asyasporcenter.R;


public class Hakkinda_Ekrani extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hakkinda__ekrani);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayout mail_ziya = findViewById(R.id.iletisim_ziya);
        mail_ziya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] recipients = {"ziyagurel55@hotmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.setType("text/html");
                startActivity(Intent.createChooser(intent, "Send mail"));
            }
        });
        LinearLayout mail_ali = findViewById(R.id.iletisim_ali);
        mail_ali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] recipients = {"aliosmanoktar@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.setType("text/html");
                startActivity(Intent.createChooser(intent, "Send mail"));
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return false;
    }
}
