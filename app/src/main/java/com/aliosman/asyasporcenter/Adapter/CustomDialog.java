package com.aliosman.asyasporcenter.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.IStokButtonClick;
import com.victor.loading.newton.NewtonCradleLoading;

public class CustomDialog {

    public Dialog Loading(Context context){
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        NewtonCradleLoading loading =dialog.findViewById(R.id.dialog_loading_loaging_icon);
        loading.setLoadingColor(Color.parseColor("#FFB300"));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loading.start();
        return dialog;
    }

    public Dialog Input(Context c, int count,final IStokButtonClick click){
        final Dialog dialog=new Dialog(c);
        dialog.setContentView(R.layout.layout_input_dialog);
        final EditText input_text=dialog.findViewById(R.id.layout_input_dialog_Adet);
        Button Kaydet=dialog.findViewById(R.id.layout_input_dialog_Kaydet);
        input_text.setText((count==0? "" : count+""));
        dialog.setCanceledOnTouchOutside(true);
        Kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adet=0;
                try{
                  adet=Integer.parseInt(input_text.getText().toString());
                }catch (NumberFormatException ex){
                    adet=0;
                }
                click.Click(adet);
                dialog.dismiss();

            }
        });
        return dialog;
    }

}