package com.if9.fahmi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.if9.fahmi.Model.ModelDataAlat;
import com.if9.fahmi.PesanActivity;
import com.if9.fahmi.R;
import com.if9.fahmi.Session.SessionManager;

import java.util.HashMap;
import java.util.List;

public class AdapterDataAlat extends RecyclerView.Adapter<AdapterDataAlat.HolderData> {

    List<ModelDataAlat> mListItems;
    Bitmap bitmap;
    Context context;
    SessionManager sessionManager;

    public AdapterDataAlat(Context context, List<ModelDataAlat> items) {
        this.mListItems = items;
        this.context = context;
    }


    @Override
    public AdapterDataAlat.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_alat, parent, false);
        AdapterDataAlat.HolderData holderData = new AdapterDataAlat.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(AdapterDataAlat.HolderData holder, int position) {
        ModelDataAlat mlist = mListItems.get(position);

        holder.tv_title.setText(mlist.getNama());
        holder.tv_keterangan.setText(mlist.getHarga());
        holder.tv_stok.setText(mlist.getStok());
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
        TextView tv_title, tv_keterangan, tv_stok;
        Button btnPesan;

        ModelDataAlat md;

        public HolderData(View v) {
            super(v);
            thubnail = v.findViewById(R.id.img_cover);
            tv_title = v.findViewById(R.id.tv_title);
            tv_keterangan = v.findViewById(R.id.tv_description);
            tv_stok = v.findViewById(R.id.tv_stok);
            btnPesan = v.findViewById(R.id.btnpesan);

            sessionManager = new SessionManager(context);
            HashMap<String, String> userr = sessionManager.getUserDetail();
            final String Id_user = userr.get(SessionManager.ID);
            HashMap<String, String> user = sessionManager.getIdTokoo();
            final String Id_toko = user.get(SessionManager.ID_TOKO);


            btnPesan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pesan = new Intent(context, PesanActivity.class);
                    pesan.putExtra("id_alat", md.getId_alat());
                    pesan.putExtra("id_user", Id_user);
                    pesan.putExtra("id_toko", Id_toko);
                    pesan.putExtra("nama_alat", md.getNama());
                    pesan.putExtra("harga_alat", md.getHarga());
                    context.startActivity(pesan);
                }
            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                       /* Intent detail=new Intent(context, AlatTokoDetailActivity.class);
                        // detail.putExtra("klik",1);
                        detail.putExtra("id_alat", md.getId_alat());
                        detail.putExtra("nama_alat", md.getNama());
                        detail.putExtra("harga_alat", md.getHarga());
                        detail.putExtra("foto", md.getFoto());


                        context.startActivity(detail);
*/


                }
            });


        }
    }


}
