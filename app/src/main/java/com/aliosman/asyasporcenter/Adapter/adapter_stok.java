package com.aliosman.asyasporcenter.Adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.IRecylerItemClick;
import com.aliosman.asyasporcenter.Values.Urun;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class adapter_stok  extends RecyclerView.Adapter<adapter_stok.ViewHolder> {
    List<Urun> uruns;
    IRecylerItemClick click;
    String TAG = getClass().getName();
    public adapter_stok(List<Urun> uruns, IRecylerItemClick click){
        this.uruns=uruns;
        this.click=click;
    }
    @Override
    public adapter_stok.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sepet,viewGroup,false);
        return new adapter_stok.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final adapter_stok.ViewHolder viewHolder, int i) {
        final Urun item = uruns.get(i);
        viewHolder.txt_urun.setText(item.getUrunAdi());
        viewHolder.txt_40.setText(item.getCount40()+"");
        viewHolder.txt_41.setText(item.getCount41()+"");
        viewHolder.txt_42.setText(item.getCount42()+"");
        viewHolder.txt_43.setText(item.getCount43()+"");
        viewHolder.txt_44.setText(item.getCount44()+"");
        viewHolder.txt_45.setText(item.getCount45()+"");
        viewHolder.txt_fiyat.setText(item.getFiyat()+"₺");
        viewHolder.layout.startShimmerAnimation();
        Picasso.get()
                .load(Uri.parse(item.getImagePath()))
                .centerCrop()
                .fit()
                .into(viewHolder.img, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.layout.stopShimmerAnimation();
                    }

                    @Override
                    public void onError(Exception e) {
                        viewHolder.layout.stopShimmerAnimation();
                    }
                });
        if (click!=null)
            viewHolder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.Click(item);
                }
            });
    }

    public Urun getItem(int position){
        return uruns.get(position);
    }

    @Override
    public int getItemCount() {
        return uruns.size();
    }

    public void removeItem(int index){
        uruns.remove(index);
        notifyItemRemoved(index);
    }

    public void restoreItem(Urun data, int index){
        uruns.add(index,data);
        notifyItemInserted(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_urun,txt_fiyat,txt_40,txt_41,txt_42,txt_43,txt_44,txt_45;
        private RelativeLayout root,frg_back;
        public LinearLayout frg_view;
        private ImageView img;
        private ShimmerLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.list_urun_shimmer);
            frg_view=itemView.findViewById(R.id.list_sepet_frg_view);
            frg_back=itemView.findViewById(R.id.list_sepet_frg_delete);
            root=itemView.findViewById(R.id.list_sepet_rootview);
            txt_urun=itemView.findViewById(R.id.list_sepet_adi);
            txt_fiyat=itemView.findViewById(R.id.list_sepet_fiyat);
            txt_40=itemView.findViewById(R.id.list_sepet_40);
            txt_41=itemView.findViewById(R.id.list_sepet_41);
            txt_42=itemView.findViewById(R.id.list_sepet_42);
            txt_43=itemView.findViewById(R.id.list_sepet_43);
            txt_44=itemView.findViewById(R.id.list_sepet_44);
            txt_45=itemView.findViewById(R.id.list_sepet_45);
            img=itemView.findViewById(R.id.list_sepet_img);
        }

    }
}
