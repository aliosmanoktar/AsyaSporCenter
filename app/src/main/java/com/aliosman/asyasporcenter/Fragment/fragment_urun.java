package com.aliosman.asyasporcenter.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliosman.asyasporcenter.Adapter.CustomDialog;
import com.aliosman.asyasporcenter.Adapter.adapter_urun;
import com.aliosman.asyasporcenter.Connection.IListUrun;
import com.aliosman.asyasporcenter.Connection.UrunConnection;
import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.GlobalBus;
import com.aliosman.asyasporcenter.Values.IRecylerItemClick;
import com.aliosman.asyasporcenter.Values.IUrunSearch;
import com.aliosman.asyasporcenter.Values.Urun;

import java.util.ArrayList;
import java.util.List;

public class fragment_urun extends Fragment implements IUrunSearch, IRecylerItemClick {
    private Dialog loading;
    private RecyclerView recyclerView;
    private List<Urun> uruns;
    private String TAG = getClass().getName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_urunler,container,false);
        uruns = new ArrayList<>();
        recyclerView=view.findViewById(R.id.recyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 0));
        RefreshList();
        loading=new CustomDialog().Loading(getContext());
        try {
            loading.show();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return view;
    }

    @Override
    public void Search(String value) {
        if (value.length()!=0)
            SetAdapter(SearchList(value.toLowerCase()));
        else SetAdapter(uruns);
    }

    private List<Urun> SearchList(String s){
        List<Urun> temp = new ArrayList<>();
        for (Urun item : uruns)
            if (item.getUrunAdi().toLowerCase().contains(s))
                temp.add(item);
        return temp;
    }

    private void SetAdapter(List<Urun> uruns){
        if (loading!=null && loading.isShowing())
            loading.dismiss();
        recyclerView.setAdapter(new adapter_urun(uruns,this));
    }

    @Override
    public void Click(Urun urun) {
        BottomSheetDialogFragment bf = new urun_select_bottom();
        Bundle args = new Bundle();
        args.putSerializable("Urun",new Urun()
                .setCount40(urun.getCount40())
                .setCount41(urun.getCount41())
                .setCount42(urun.getCount42())
                .setCount43(urun.getCount43())
                .setCount44(urun.getCount44())
                .setCount45(urun.getCount45())
                .setId(urun.getId())
                .setFiyat(urun.getFiyat())
                .setUrunAdi(urun.getUrunAdi())
                .setImagePath(urun.getImagePath()));
        bf.setArguments(args);
        bf.show(getFragmentManager(),"urun_select");
    }
    private void RefreshList(){
        new UrunConnection().ListUrun(new IListUrun() {
            @Override
            public void ListUrun(List<Urun> urunler) {
                uruns=urunler;
                GlobalBus.getBus().post(uruns);
                SetAdapter(uruns);
            }
        });
    }
}