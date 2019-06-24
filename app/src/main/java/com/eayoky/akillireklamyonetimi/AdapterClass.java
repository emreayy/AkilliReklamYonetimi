package com.eayoky.akillireklamyonetimi;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder>{

    ArrayList<Firma> list;
    public AdapterClass(ArrayList<Firma> list){
        this.list=list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from (viewGroup.getContext ()).inflate (R.layout.card_holder,viewGroup,false);
        return new MyViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText (list.get (i).getFirmaAdi ());
        myViewHolder.kategori.setText (list.get (i).getKategori ());
        myViewHolder.sure.setText (list.get (i).getKampanyaSuresi ());
        myViewHolder.icerik.setText (list.get (i).getKampanyaIcerik ());
        myViewHolder.lat.setText (list.get (i).getFirmaLat ());
        myViewHolder.lon.setText (list.get (i).getFirmaLon ());
    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,kategori,sure,icerik,lat,lon;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            name=itemView.findViewById (R.id.firmaName);
            kategori=itemView.findViewById (R.id.firmaKategori);
            sure=itemView.findViewById (R.id.firmaSure);
            icerik=itemView.findViewById (R.id.firmaIcerik);
            lat=itemView.findViewById (R.id.firmaLat);
            lon=itemView.findViewById (R.id.firmaLon);
        }
    }
}