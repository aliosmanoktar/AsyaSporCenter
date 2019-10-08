package com.aliosman.asyasporcenter.Adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.IRecylerItemClick;
import com.aliosman.asyasporcenter.Values.Urun;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class adapter_urun extends RecyclerView.Adapter<adapter_urun.ViewHolder>{
    private List<Urun> uruns;
    private IRecylerItemClick click;

    public adapter_urun(List<Urun> uruns, IRecylerItemClick click) {
        this.uruns = uruns;
        this.click=click;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_urun,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Urun item = uruns.get(i);
        viewHolder.txt_urun.setText(item.getUrunAdi());
        viewHolder.txt_40.setText(item.getCount40()+"");
        viewHolder.txt_41.setText(item.getCount41()+"");
        viewHolder.txt_42.setText(item.getCount42()+"");
        viewHolder.txt_43.setText(item.getCount43()+"");
        viewHolder.txt_44.setText(item.getCount44()+"");
        viewHolder.txt_45.setText(item.getCount45()+"");
        viewHolder.txt_fiyat.setText(item.getFiyat()+" ₺");
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
        viewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.Click(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return uruns.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView txt_urun,txt_fiyat,txt_40,txt_41,txt_42,txt_43,txt_44,txt_45;
        private RelativeLayout root;
        private ShimmerLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.list_urun_shimmer);
            root=itemView.findViewById(R.id.list_urun_rootview);
            img=itemView.findViewById(R.id.list_urun_img);
            txt_urun=itemView.findViewById(R.id.list_urun_adi);
            txt_fiyat=itemView.findViewById(R.id.list_urun_fiyat);
            txt_40=itemView.findViewById(R.id.list_urun_40);
            txt_41=itemView.findViewById(R.id.list_urun_41);
            txt_42=itemView.findViewById(R.id.list_urun_42);
            txt_43=itemView.findViewById(R.id.list_urun_43);
            txt_44=itemView.findViewById(R.id.list_urun_44);
            txt_45=itemView.findViewById(R.id.list_urun_45);
        }

    }
}
