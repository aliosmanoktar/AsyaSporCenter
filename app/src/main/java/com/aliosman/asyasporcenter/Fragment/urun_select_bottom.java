package com.aliosman.asyasporcenter.Fragment;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.GlobalBus;
import com.aliosman.asyasporcenter.Values.Urun;
import com.squareup.picasso.Picasso;

public class urun_select_bottom extends BottomSheetDialogFragment {

    private Urun urun;
    private int count40=0,count41=0,count42=0,count43=0,count44=0,count45=0;
    private TextView txtUrunAdi;
    private TextView txt40,txt41,txt42,txt43,txt44,txt45;
    private ImageView add40,add41,add42,add43,add44,add45;
    private ImageView remove40,remove41,remove42,remove43,remove44,remove45;
    private Button btn_add;
    private ImageView img;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_urun_select_dialog,container,false);

        txtUrunAdi=view.findViewById(R.id.bottom_select_UrunAdi);

        txt40=view.findViewById(R.id.bottom_select_40count);
        txt41=view.findViewById(R.id.bottom_select_41count);
        txt42=view.findViewById(R.id.bottom_select_42count);
        txt43=view.findViewById(R.id.bottom_select_43count);
        txt44=view.findViewById(R.id.bottom_select_44count);
        txt45=view.findViewById(R.id.bottom_select_45count);

        add40=view.findViewById(R.id.bottom_select_40add);
        add41=view.findViewById(R.id.bottom_select_41add);
        add42=view.findViewById(R.id.bottom_select_42add);
        add43=view.findViewById(R.id.bottom_select_43add);
        add44=view.findViewById(R.id.bottom_select_44add);
        add45=view.findViewById(R.id.bottom_select_45add);

        remove40=view.findViewById(R.id.bottom_select_40remove);
        remove41=view.findViewById(R.id.bottom_select_41remove);
        remove42=view.findViewById(R.id.bottom_select_42remove);
        remove43=view.findViewById(R.id.bottom_select_43remove);
        remove44=view.findViewById(R.id.bottom_select_44remove);
        remove45=view.findViewById(R.id.bottom_select_45remove);

        btn_add=view.findViewById(R.id.bottom_select_btn_add);

        add40.setOnClickListener(add40Click);
        add41.setOnClickListener(add41Click);
        add42.setOnClickListener(add42Click);
        add43.setOnClickListener(add43Click);
        add44.setOnClickListener(add44Click);
        add45.setOnClickListener(add45Click);

        remove40.setOnClickListener(remove40Click);
        remove41.setOnClickListener(remove41Click);
        remove42.setOnClickListener(remove42Click);
        remove43.setOnClickListener(remove43Click);
        remove44.setOnClickListener(remove44Click);
        remove45.setOnClickListener(remove45Click);

        btn_add.setOnClickListener(btn_click);

        Bundle args = getArguments();
        if (args!=null){
            urun=(Urun)args.getSerializable("Urun");
        }
        img=view.findViewById(R.id.bottom_select_UrunImage);
        Picasso.get()
                .load(Uri.parse(urun.getImagePath()))
                .centerCrop()
                .fit()
                .into(img);
        if(urun.getCount40() == 0 ){
            ImageDisabled(add40);
            ImageDisabled(remove40);
        }
        if(urun.getCount41() == 0){
            ImageDisabled(add41);
            ImageDisabled(remove41);
        }
        if(urun.getCount42() == 0){
            ImageDisabled(add42);
            ImageDisabled(remove42);
        }
        if(urun.getCount43() == 0){
            ImageDisabled(add43);
            ImageDisabled(remove43);
        }
        if(urun.getCount44() == 0){
            ImageDisabled(add44);
            ImageDisabled(remove44);
        }
        if(urun.getCount45() == 0){
            ImageDisabled(add45);
            ImageDisabled(remove45);
        }
        txtUrunAdi.setText(urun.getUrunAdi());
        return view;
    }

    View.OnClickListener add40Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (urun.getCount40()!=count40){
                count40++;
                txt40.setText(""+count40);
            }
        }
    };
    View.OnClickListener add41Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (urun.getCount41()!=count41){
                count41++;
                txt41.setText(""+count41);
            }
        }
    };

    View.OnClickListener add42Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (urun.getCount42()!=count42){
                count42++;
                txt42.setText(""+count42);
            }
        }
    };
    View.OnClickListener add43Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (urun.getCount43()!=count43){
                count43++;
                txt43.setText(""+count43);
            }
        }
    };
    View.OnClickListener add44Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (urun.getCount44()!=count44){
                count44++;
                txt44.setText(""+count44);
            }
        }
    };
    View.OnClickListener add45Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (urun.getCount45()!=count45){
                count45++;
                txt45.setText(""+count45);
            }
        }
    };
    View.OnClickListener remove40Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (count40>0){
                count40--;
                txt40.setText(""+count40);
            }
        }
    };
    View.OnClickListener remove41Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (count41>0){
                count41--;
                txt41.setText(""+count41);
            }
        }
    };

    View.OnClickListener remove42Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (count42>0){
                count42--;
                txt42.setText(""+count42);
            }
        }
    };
    View.OnClickListener remove43Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (count43>0){
                count43--;
                txt43.setText(""+count43);
            }
        }
    };
    View.OnClickListener remove44Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (count44>0){
                count44--;
                txt44.setText(""+count44);
            }
        }
    };
    View.OnClickListener remove45Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (count45>0){
                count45--;
                txt45.setText(""+count45);
            }
        }
    };
    Dialog dialog;
    View.OnClickListener btn_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if ( urun!=null){
                if (count40==count41 && count41==count42 && count42==count43 && count43==count44 &&count44==count45 && count45==0){
                    dialog= new AwesomeErrorDialog(getContext())
                            .setMessage("Adet 0 Olamaz")
                            .setTitle("Hata")
                            .setCancelable(true)
                            .setButtonText("Tamam")
                            .setErrorButtonClick(new Closure() {
                                @Override
                                public void exec() {
                                    dialog.hide();
                                }
                            })
                            .show();
                    return;
                }
                urun.setCount40(count40);
                urun.setCount41(count41);
                urun.setCount42(count42);
                urun.setCount43(count43);
                urun.setCount44(count44);
                urun.setCount45(count45);
                GlobalBus.getBus().post(urun);
                dialog= new AwesomeSuccessDialog(getContext())
                        .setMessage("Sepete Eklendi")
                        .setTitle("Başarılı")
                        .setCancelable(true)
                        .setDoneButtonClick(new Closure() {
                            @Override
                            public void exec() {
                                dialog.dismiss();
                            }
                        }).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                },1000);
                dismiss();
            }
        }
    };
    public void ImageDisabled(ImageView image){
        image.setEnabled(false);
        image.setColorFilter(getResources().getColor(R.color.disabled_button));
    }
}