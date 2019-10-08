package com.aliosman.asyasporcenter.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.aliosman.asyasporcenter.Connection.IUploadFinish;
import com.aliosman.asyasporcenter.Connection.KasaConnection;
import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.Kasa;

public class kasa_add_bottom extends BottomSheetDialogFragment implements IUploadFinish {
    private EditText aciklama_EditText;
    private EditText fiyat_EditText;
    private Button kaydet_btn;
    private Kasa kasa;
    private String TAG=getClass().getName();
    private Dialog dialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_kasa_ekleme_dialog,container,false);
        Bundle args = getArguments();
        aciklama_EditText = view.findViewById(R.id.bottom_kasa_ekran_diolog_aciklamaText);
        fiyat_EditText = view.findViewById(R.id.bottom_kasa_ekran_diolog_fiyatText);
        kaydet_btn = view.findViewById(R.id.bottom_kasa_ekran_kaydet_btn);
        if (args!=null){
            kasa=(Kasa)args.getSerializable("kasa_item");
            aciklama_EditText.setText(kasa.getAdi());
            fiyat_EditText.setText(kasa.getFiyat()+"");
        }
        kaydet_btn.setOnClickListener(addKasa);
        return view;
    }

    View.OnClickListener addKasa = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (aciklama_EditText.getText().toString().length()==0 || fiyat_EditText.getText().toString().length()==0){
                final AwesomeErrorDialog errorDialog = new AwesomeErrorDialog(getActivity());
                errorDialog.setTitle("Hata")
                        .setMessage("Boş değer olamaz")
                        .setButtonText("Tamam")
                        .setErrorButtonClick(new Closure() {
                            @Override
                            public void exec() {
                                errorDialog.hide();
                            }
                        }).show();
                return;
            }
            if (kasa==null)
                new KasaConnection().InsertKasa(new Kasa(aciklama_EditText.getText().toString(),Float.parseFloat(fiyat_EditText.getText().toString())),kasa_add_bottom.this);
            else {
                kasa.setAdi(aciklama_EditText.getText().toString());
                kasa.setFiyat(Float.parseFloat(fiyat_EditText.getText().toString()));
                new KasaConnection().UpdateKasa(kasa,kasa_add_bottom.this);
            }
        }
    };

    @Override
    public void Start() {
        dialog=new AwesomeProgressDialog(getActivity())
                .setMessage("İşlem Yapılıyor")
                .setTitle("Lütfen Bekleyiniz")
                .setCancelable(false)
                .show();
    }

    @Override
    public void Upload(boolean sonuc) {
        if (dialog!=null)
            dialog.dismiss();
        if (sonuc){
            dialog=new AwesomeSuccessDialog(getActivity())
                    .setMessage("İşlem Başarılı")
                    .setTitle("Başarılı")
                    .setDoneButtonClick(new Closure() {
                        @Override
                        public void exec() {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }else{
            dialog = new AwesomeErrorDialog(getActivity())
                    .setTitle("Hata")
                    .setMessage("Boş değer olamaz")
                    .setButtonText("Tamam")
                    .setErrorButtonClick(new Closure() {
                        @Override
                        public void exec() {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }
}
