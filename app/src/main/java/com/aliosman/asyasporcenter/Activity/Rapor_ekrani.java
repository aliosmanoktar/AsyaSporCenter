package com.aliosman.asyasporcenter.Activity;


import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.evrencoskun.tableview.TableView;
import com.aliosman.asyasporcenter.Adapter.TableAdapter;
import com.aliosman.asyasporcenter.Connection.IListSube;
import com.aliosman.asyasporcenter.Connection.LoginConnection;
import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.Satis;
import com.aliosman.asyasporcenter.Values.TableCell;
import com.jaredrummler.materialspinner.MaterialSpinner;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;
import com.aliosman.asyasporcenter.Connection.ISatisList;
import com.aliosman.asyasporcenter.Connection.SatisConnection;
//asya1
public class Rapor_ekrani extends AppCompatActivity  implements SlyCalendarDialog.Callback,ISatisList{

    private String TAG = getClass().getName();
    private TableView tableView;
    private TextView txt_gecerli,txt_toplam_satis,txt_toplam_ayakkabi;
    private Dialog dialog;
    private final String TekGunFormat="EE, dd MMM";
    private final String CokGunFormat="EE, dd";
    private final String AyFormat="MM\\yyyy";
    private final String GunFormat ="dd\\MM\\yyyy";
    private Calendar firsDate;
    private Calendar seconDate;
    private String dukkan="Hepsi";
    private String ucret_type="Hepsi";
    private TableAdapter adapter;
    private List<Satis> satis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapor_ekrani);
        Button btn_tarih = findViewById(R.id.layout_rapor_btn_basla_tarih);
        final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        new LoginConnection().GetSubes(new IListSube() {
            @Override
            public void List(List<String> subes) {
                spinner.setItems(subes);
            }
        });
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                dukkan=item;
                Listele(Search(
                        (firsDate==seconDate && seconDate==null) ?
                                satis :
                                Search(satis,firsDate,seconDate)
                        ,item,ucret_type));
            }
        });
        MaterialSpinner spinner_ucret=findViewById(R.id.spinner_ucret);
        spinner_ucret.setItems("Hepsi","Nakit","Kart");
        spinner_ucret.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                ucret_type=item;
                    Listele(Search(
                            (firsDate==seconDate && seconDate==null) ?
                                    satis :
                                    Search(satis,firsDate,seconDate)
                            ,dukkan,item));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_gecerli=findViewById(R.id.txt_baslangic);
        txt_toplam_ayakkabi=findViewById(R.id.txt_satis_ayakkabi);
        txt_toplam_satis=findViewById(R.id.satis_toplam);
        tableView=findViewById(R.id.my_TableView);
        btn_tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SlyCalendarDialog()
                        .setSingle(false)
                        .setCallback(Rapor_ekrani.this)
                        .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");
            }
        });

        adapter =new TableAdapter(getApplicationContext());
        tableView.setAdapter(adapter);
        tableView.setSelected(false);
        ShowDialog();
        Date d= Calendar.getInstance().getTime();
        new SatisConnection().getSatislar(getZaman(AyFormat,d),getZaman(GunFormat,d),this);
        txt_gecerli.setText(getZaman(TekGunFormat,d));
     }

    private void ShowDialog(){
        if (dialog==null || !dialog.isShowing())
            dialog=new AwesomeProgressDialog(this)
                    .setMessage("Veriler Yükleniyor")
                    .setTitle("Lütfen Bekleyiniz")
                    .show();
    }

    private List<TableCell> getHeaders(){
        List<TableCell> list=new ArrayList<>();
        list.add(new TableCell("Adet"));
        list.add(new TableCell("Tutar"));
        list.add(new TableCell("Şube"));
        list.add(new TableCell("Saat"));
        list.add(new TableCell("Tip"));
        return list;
    }

    private List<TableCell> getRowHeaders(int size){
        List<TableCell> cels=new ArrayList<>();
        for (int i=1;i<size+1;i++)
            cels.add(new TableCell(""+i));
        return cels;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return false;
    }

    private List<List<TableCell>> getData(List<Satis> satis){
        List<List<TableCell>> items = new ArrayList<>();
        for (Satis item : satis)
        {
            List<TableCell> cells = new ArrayList<>();
            cells.add(new TableCell(item.getAdet()+""));
            cells.add(new TableCell(item.getTutar()+""));
            cells.add(new TableCell(item.getDukkan()+""));
            cells.add(new TableCell(item.getSaat()+""));
            cells.add(new TableCell(item.getSatisType()+""));
            items.add(cells);
        }
        return items;
    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
        if (firstDate!=null && secondDate!=null && firstDate.get(Calendar.MONTH)!=secondDate.get(Calendar.MONTH)){
            dialog=new AwesomeErrorDialog(this)
                    .setErrorButtonClick(new Closure() {
                        @Override
                        public void exec() {
                            dialog.dismiss();
                        }
                    })
                    .setButtonText("Tamam")
                    .setCancelable(true)
                    .setTitle("Hata")
                    .setMessage("Farklı aylar arasında seçim yapılamaz")
                    .show();
            return;
        }
        ShowDialog();
        if (secondDate==null){
            txt_gecerli.setText(getZaman(TekGunFormat,firstDate.getTime()));
            Log.e(TAG, "onDataSelected: "+"Tek Tarih" );
            this.firsDate=this.seconDate=null;
            new SatisConnection().getSatislar(getZaman(AyFormat,firstDate.getTime()),getZaman(GunFormat,firstDate.getTime()),this);
        }
        else{
            this.firsDate=firstDate;
            this.firsDate.set(Calendar.HOUR_OF_DAY, 0);
            this.seconDate=secondDate;
            Log.e(TAG, "onDataSelected: "+(firstDate==null)+" "+(secondDate==null) );
            txt_gecerli.setText(getZaman(CokGunFormat,firstDate.getTime())+" - "+getZaman(TekGunFormat,seconDate.getTime()));
            new SatisConnection().getSatislar(getZaman(AyFormat,firstDate.getTime()),this);
            Log.e(TAG, "onDataSelected: "+"Cift Tarih" );
        }
    }

    private String getZaman(String format, Date date){
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    @Override
    public void List(List<Satis> satislar) {
        this.satis=satislar;
        if (dialog!=null)
            dialog.dismiss();
        if (firsDate==seconDate && seconDate==null ){
                Listele(Search(satislar,dukkan,ucret_type));
        }else {
            Listele(Search(satislar,firsDate,seconDate));
        }
    }

    private List<Satis> Search(List<Satis> items,Calendar firsDate,Calendar seconDate){
        List<Satis> temp = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat(GunFormat);
        for (Satis item :items){
            try {
                Date date=format.parse(item.getTarih());
                int first = firsDate.getTime().compareTo(date);
                int second = seconDate.getTime().compareTo(date);
                if ((first<0 || first==0) && (second>0 || second==0))
                    temp.add(item);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Log.e(TAG, "Search: Tarih:"+temp.size() );
        return temp;
    }

    private List<Satis> SearchUcret(List<Satis> items,String Ucret){
        Log.e(TAG,Ucret.equals("Hepsi")+"");
        if (Ucret.equals("Hepsi"))
            return items;
        List<Satis> temp = new ArrayList<>();
        for(Satis item: items){
            if (item.getSatisType().toString()==Ucret)
                temp.add(item);
        }
        return temp;
    }

    private List<Satis> Search(List<Satis> items, String dukkan,String ucret){
        if (dukkan.equals("Hepsi"))
            return SearchUcret(items,ucret);
        List<Satis> temp = new ArrayList<>();
        for(Satis item: items)
            if (item.getDukkan().equals(dukkan))
                temp.add(item);
        Log.e(TAG, "Search: "+temp.size());
        return SearchUcret(temp,ucret);
    }

    private void Listele(List<Satis> satislar){
        int width=tableView.getWidth()-tableView.getRowHeaderWidth();
        width/=5;
        for (int i = 0;i<5;i++)
            tableView.setColumnWidth(i,width);
        adapter.setAllItems(getHeaders(),getRowHeaders(satislar.size()),getData(satislar));
        int ayakkabi=0;
        float toplam=0;
        for (Satis item:satislar)
        {
            ayakkabi+=item.getAdet();
            toplam+=item.getTutar();
        }
        txt_toplam_satis.setText(toplam+" ₺");
        txt_toplam_ayakkabi.setText(ayakkabi+ " adet");
        tableView.setAdapter(adapter);
    }

}