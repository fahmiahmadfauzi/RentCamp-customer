package com.if9.fahmi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.if9.fahmi.Model.ModelData;
import com.if9.fahmi.ProfileTokoActivity;
import com.if9.fahmi.R;
import com.if9.fahmi.Session.SessionManager;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    List<ModelData> mListItems;
    Bitmap bitmap;
    Context context;

    SessionManager sessionManager;

    public AdapterData(Context context, List<ModelData> items) {
        this.mListItems = items;
        this.context = context;
    }


    @Override
    public AdapterData.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_toko, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(AdapterData.HolderData holder, int position) {
        ModelData mlist = mListItems.get(position);

        holder.tv_title.setText(mlist.getNama());
        holder.tv_keterangan.setText(mlist.getAlamat());
        //loading image
        Glide.with(context).load(mlist.getFoto()).thumbnail(0.5f).transition(new DrawableTransitionOptions().crossFade()).into(holder.thubnail);
        // new GetImageFromURL(holder.thubnail).execute(mlist.getFoto());
        holder.md = mlist;
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder {


        ImageView thubnail;
        TextView tv_title, tv_keterangan;
        ModelData md;

        public HolderData(View v) {
            super(v);
            thubnail = v.findViewById(R.id.img_cover);
            tv_title = v.findViewById(R.id.tv_title);
            tv_keterangan = v.findViewById(R.id.tv_description);
            sessionManager = new SessionManager(context);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent detail = new Intent(context, ProfileTokoActivity.class);

                    // detail.putExtra("klik",1);
                    detail.putExtra("id_toko", md.getId_toko());
                    detail.putExtra("nama_toko", md.getNama());
                    detail.putExtra("alamat_toko", md.getAlamat());
                    detail.putExtra("foto", md.getFoto());
                    sessionManager.createSessionIdToko(md.getId_toko());
                    context.startActivity(detail);


                }
            });


        }
    }


}
