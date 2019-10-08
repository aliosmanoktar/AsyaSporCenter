package com.aliosman.asyasporcenter.Adapter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.Kasa;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class adapter_kasa extends ArrayAdapter<Kasa> {

    String TAG = getClass().getName();
    public adapter_kasa(@NonNull Context context, int resource, @NonNull List<Kasa> kasas) {
        super(context, resource, kasas);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_kasa,parent,false);
        TextView aciklama_text = convertView.findViewById(R.id.kasa_list_AciklamaText);
        TextView fiyat_text = convertView.findViewById(R.id.kasa_list_FiyatText);
        Kasa item = getItem(position);
        aciklama_text.setText(item.getAdi());
        fiyat_text.setText(item.getFiyat()+" ₺");
        Log.e(TAG, "getView: Eklendi"+item.getAdi() );
        convertView.setTag(item);
        return convertView;
    }
}
