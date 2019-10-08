package com.aliosman.asyasporcenter.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.aliosman.asyasporcenter.Connection.ILogin;
import com.aliosman.asyasporcenter.Connection.LoginConnection;
import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.Sube;
import com.aliosman.asyasporcenter.Values.preferences;

public class Login_ekrani extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_giris = findViewById(R.id.btn_giris);
        final EditText text_password = findViewById(R.id.txt_password);
        btn_giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new AwesomeProgressDialog(Login_ekrani.this)
                        .setTitle("Bekleyin")
                        .setMessage("Kullanıcı Girişi Yapılıyor")
                        .setCancelable(false)
                        .show();
                new LoginConnection().kontrol(text_password.getText().toString(), new ILogin() {
                    @Override
                    public void Login(Sube item) {
                        if (dialog!=null)
                            dialog.dismiss();
                        if (item!=null){
                            new preferences(getBaseContext()).setDukkan(item.getAdi());
                            Intent i = new Intent(getBaseContext(),AnaEkran.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }else{
                            final AwesomeErrorDialog errorDialog = new AwesomeErrorDialog(Login_ekrani.this)
                                    .setButtonText("Tamam")
                                    .setTitle("Hata")
                                    .setMessage("Şifre Hatalı");
                            errorDialog.setErrorButtonClick(new Closure() {
                                @Override
                                public void exec() {
                                    errorDialog.hide();
                                }
                            });
                            errorDialog.show();

                        }
                    }
                });
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
