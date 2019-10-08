package com.aliosman.asyasporcenter.Fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.aliosman.asyasporcenter.Adapter.CustomDialog;
import com.aliosman.asyasporcenter.Values.IStokButtonClick;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.aliosman.asyasporcenter.Connection.IUploadFinish;
import com.aliosman.asyasporcenter.Connection.UrunConnection;
import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.IAddBottomClick;
import com.aliosman.asyasporcenter.Values.Urun;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class stok_add_bottom extends BottomSheetDialogFragment {
    private boolean updateImage=false;
    private int count40=0,count41=0,count42=0,count43=0,count44=0,count45=0;
    private TextView txt40,txt41,txt42,txt43,txt44,txt45;
    private ImageView add40,add41,add42,add43,add44,add45;
    private ImageView remove40,remove41,remove42,remove43,remove44,remove45;
    private EditText Edit_Fiyat;
    private EditText Edit_UrunAdi;
    private Button btn_save;
    private Urun item;
    private IAddBottomClick click;
    private ImageView img_urun;
    private Bitmap image;
    private String TAG=getClass().getName();
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_stok_ekleme_dialog, container, false);
        Bundle args = getArguments();

        img_urun=view.findViewById(R.id.bottom_stok_add_UrunImage);

        txt40=view.findViewById(R.id.bottom_stok_add_40count);
        txt41=view.findViewById(R.id.bottom_stok_add_41count);
        txt42=view.findViewById(R.id.bottom_stok_add_42count);
        txt43=view.findViewById(R.id.bottom_stok_add_43count);
        txt44=view.findViewById(R.id.bottom_stok_add_44count);
        txt45=view.findViewById(R.id.bottom_stok_add_45count);

        add40=view.findViewById(R.id.bottom_stok_add_40add);
        add41=view.findViewById(R.id.bottom_stok_add_41add);
        add42=view.findViewById(R.id.bottom_stok_add_42add);
        add43=view.findViewById(R.id.bottom_stok_add_43add);
        add44=view.findViewById(R.id.bottom_stok_add_44add);
        add45=view.findViewById(R.id.bottom_stok_add_45add);

        remove40=view.findViewById(R.id.bottom_stok_add_40remove);
        remove41=view.findViewById(R.id.bottom_stok_add_41remove);
        remove42=view.findViewById(R.id.bottom_stok_add_42remove);
        remove43=view.findViewById(R.id.bottom_stok_add_43remove);
        remove44=view.findViewById(R.id.bottom_stok_add_44remove);
        remove45=view.findViewById(R.id.bottom_stok_add_45remove);

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


        txt40.setOnClickListener(count40Click);
        txt41.setOnClickListener(count41Click);
        txt42.setOnClickListener(count42Click);
        txt43.setOnClickListener(count43Click);
        txt44.setOnClickListener(count44Click);
        txt45.setOnClickListener(count45Click);

        Edit_Fiyat = view.findViewById(R.id.bottom_stok_add_UrunFiyat);
        Edit_UrunAdi = view.findViewById(R.id.bottom_stok_add_UrunAdi);

        btn_save = view.findViewById(R.id.bottom_ekle_btn_save);
        btn_save.setOnClickListener(addUrun);
        click=(IAddBottomClick)args.getSerializable("click");
        img_urun.setOnClickListener(urunImageClick);
        Log.e(TAG, "onCreateView: " );
        item = (Urun) args.getSerializable("Urun");
        if (item!=null) {
            txt40.setText(item.getCount40()+"");
            txt41.setText(item.getCount41()+"");
            txt42.setText(item.getCount42()+"");
            txt43.setText(item.getCount43()+"");
            txt44.setText(item.getCount44()+"");
            txt45.setText(item.getCount45()+"");
            count40=item.getCount40();
            count41=item.getCount41();
            count42=item.getCount42();
            count43=item.getCount43();
            count44=item.getCount44();
            count45=item.getCount45();
            Picasso.get().load(Uri.parse(item.getImagePath()))
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            img_urun.setImageBitmap(bitmap);
                            image=bitmap;
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
            Edit_Fiyat.setText(item.getFiyat()+"");
            Edit_UrunAdi.setText(item.getUrunAdi());
            btn_save.setText("Ürün Güncelle");
        }

        return view;
    }

    View.OnClickListener count40Click = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            new CustomDialog().Input(getActivity(), count40,new IStokButtonClick() {
                @Override
                public void Click(int count) {
                    count40=count;
                    txt40.setText(""+count40);
                }
            }).show();
        }
    };
    View.OnClickListener count41Click = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            new CustomDialog().Input(getActivity(),count41, new IStokButtonClick() {
                @Override
                public void Click(int count) {
                    count41=count;
                    txt41.setText(""+count41);
                }
            }).show();
        }
    };
    View.OnClickListener count42Click = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            new CustomDialog().Input(getActivity(), count42,new IStokButtonClick() {
                @Override
                public void Click(int count) {
                    count42=count;
                    txt42.setText(""+count42);
                }
            }).show();
        }
    };
    View.OnClickListener count43Click = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            new CustomDialog().Input(getActivity(),count43, new IStokButtonClick() {
                @Override
                public void Click(int count) {
                    count43=count;
                    txt43.setText(""+count43);
                }
            }).show();
        }
    };
    View.OnClickListener count44Click = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            new CustomDialog().Input(getActivity(), count44,new IStokButtonClick() {
                @Override
                public void Click(int count) {
                    count44=count;
                    txt44.setText(""+count44);
                }
            }).show();
        }
    };

    View.OnClickListener count45Click = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            new CustomDialog().Input(getActivity(), count45,new IStokButtonClick() {
                @Override
                public void Click(int count) {
                    count45=count;
                    txt45.setText(""+count45);
                }
            }).show();
        }
    };
    View.OnClickListener add40Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            count40++;
            txt40.setText(""+count40);
        }
    };
    View.OnClickListener add41Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            count41++;
            txt41.setText(""+count41);
        }
    };

    View.OnClickListener add42Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            count42++;
            txt42.setText(""+count42);
        }
    };
    View.OnClickListener add43Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            count43++;
            txt43.setText(""+count43);
        }
    };
    View.OnClickListener add44Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            count44++;
            txt44.setText(""+count44);
        }
    };
    View.OnClickListener add45Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            count45++;
            txt45.setText(""+count45);
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

    View.OnClickListener addUrun = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (item==null && image==null) {
                Toast.makeText(getContext(), "Resim Seçilmelidir", Toast.LENGTH_LONG).show();
                return;
            }
            if (item.getId()==null || item.getId().length() == 0){
               new UrunConnection().UploadUrun(getUrun(), image, finish);
            }else{
                if (!updateImage)
                    new UrunConnection().UpdataUrun(getUrun().setId(item.getId()).setImagePath(item.getImagePath()),finish);
                else {
                    UrunConnection connection= new UrunConnection();
                    connection.DeleteUrun(getUrun().setId(item.getId()));
                    connection.UploadUrun(getUrun(),image,finish);
                }
            }
        }
    };
    View.OnClickListener urunImageClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            click.Click();
        }
    };

    private IUploadFinish finish=new IUploadFinish() {
        Dialog dialog=null;
        Handler dinleme;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                InfoDialog();
            }
        };
        @Override
        public void Start() {
            try {
                dialog = new AwesomeProgressDialog(getContext())
                        .setMessage("Değişiklikler Sunucu Üzerine Kaydediliyor")
                        .setTitle("Lütfen Bekleyiniz")
                        .setCancelable(false)
                        .show();
                HandlerDinle();
            }catch (Exception ex){

            }
        }
        private void HandlerDinle(){
            dinleme=new Handler();
            dinleme.postDelayed(runnable,10000);
        }
        private void InfoDialog(){
            if (dialog!=null)
                dialog.dismiss();
            dialog=new AwesomeWarningDialog(getContext())
                    .setMessage("Değişikliklerin Sunucuya İletimi Sırasında Zaman Aşımı Gerçekleşti. İnternet Erişimi Sağlandığı Anda Tekrar Denenecektir")
                    .setTitle("Hata")
                    .show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (dialog!=null)
                        dialog.dismiss();
                }
            },1000);
        }
        @Override
        public void Upload(boolean sonuc) {
            try {
                if (dialog!=null){
                    dinleme.removeCallbacks(runnable);
                    dialog.dismiss();
                }
                if (sonuc){

                    dialog = new AwesomeSuccessDialog(getContext())
                                .setMessage("Değişikliler Başarılı Bir Şekilde Kaydedildi")
                                .setTitle("Başarılı")
                                .setDoneButtonClick(new Closure() {
                                    @Override
                                    public void exec() {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    dismiss();

                }else {
                    dialog = new AwesomeErrorDialog(getContext())
                            .setMessage("Değişikliler Sırasında Hata Oluştu")
                            .setTitle("Başarısız")
                            .setErrorButtonClick(new Closure() {
                                @Override
                                public void exec() {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (dialog!=null)
                            dialog.dismiss();
                    }
                },1000);
            }catch (Exception ex){

            }
        }
    };

    public Bundle onSaveInstanceState() {
        Bundle bundle=new Bundle();
        bundle.putSerializable("newUrUn",getUrun());
        bundle.putSerializable("urun",this.item);
        return bundle;
    }

    private Urun getUrun(){
       return new Urun()
                .setCount40(count40)
                .setCount41(count41)
                .setCount42(count42)
                .setCount43(count43)
                .setCount44(count44)
                .setCount45(count45)
                .setFiyat(Float.parseFloat(Edit_Fiyat.getText().toString().length()==0?"0":Edit_Fiyat.getText().toString()))
                .setUrunAdi(Edit_UrunAdi.getText().toString());

    }

    public void onViewStateRestored(Bundle savedInstanceState,boolean insertimage) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState==null)
            return;
        Log.e(TAG, "onViewStateRestored: " );
        Urun item = (Urun) savedInstanceState.getSerializable("urun");
        this.item = item != null ? item : new Urun();
        Urun urun = (Urun) savedInstanceState.getSerializable("newUrUn");
        count40=urun.getCount40();
        count41=urun.getCount41();
        count42=urun.getCount42();
        count43=urun.getCount43();
        count44=urun.getCount44();
        count45=urun.getCount45();
        txt40.setText(""+count40);
        txt41.setText(""+count41);
        txt42.setText(""+count42);
        txt43.setText(""+count43);
        txt44.setText(""+count44);
        txt45.setText(""+count45);
        if (item!=null && item.getId()!=null)
            btn_save.setText("Ürün Güncelle");
        Edit_UrunAdi.setText(urun.getUrunAdi());
        Edit_Fiyat.setText(urun.getFiyat()==0 ? "":urun.getFiyat()+"");
    }
    public void SendImage(Bitmap bitmap){
        Log.e(TAG, "SendImage: "+bitmap.getHeight()+" "+bitmap.getWidth() );
        bitmap=Bitmap.createScaledBitmap(bitmap,300,300,true);
        img_urun.setImageBitmap(bitmap);
        Log.e(TAG, "SendImage: "+bitmap.getHeight()+" "+bitmap.getWidth() );
        updateImage=true;
        image=bitmap;
    }
}