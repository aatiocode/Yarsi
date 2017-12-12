package com.yarsi.yarsi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yarsi.yarsi.model.Dokter;

import java.util.List;

public class DokterListAdapter extends RecyclerView.Adapter<DokterListAdapter.DokterViewHolder> {

    private List<Dokter> dokterList;
    private Context context;

    private static int currentPosition = 0;

    public DokterListAdapter(List<Dokter> dokterList, Context context) {
        this.dokterList = dokterList;
        this.context = context;
    }

    @Override
    public DokterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dokter_item, parent, false);
        return new DokterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final DokterViewHolder holder, final int position) {
        Dokter dokter = dokterList.get(position);
        holder.txtViewName.setText(dokter.getNamaDepan() +" "+ dokter.getNamaBelakang() +" "+ dokter.getSpesialis());
        holder.txtNama.setText(dokter.getNamaDepan() +" "+ dokter.getNamaBelakang() +" "+ dokter.getSpesialis());
        holder.txtUnitPerawatan.setText(dokter.getUnitPerawatan());


        Glide.with(context).load(dokter.getFoto()).into(holder.imgDokter);
        holder.linearLayoutListDokter.setVisibility(View.GONE);

        //if the position is equals to the item position which is to be expanded
        if (currentPosition == position) {
            //creating an animation
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

            //toggling visibility
            holder.linearLayoutListDokter.setVisibility(View.VISIBLE);

            //adding sliding effect
            holder.linearLayoutListDokter.startAnimation(slideDown);
        }

        holder.txtViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the position of the item to expand it
                currentPosition = position;

                //reloding the list
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dokterList.size();
    }

    class DokterViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDokter;
        TextView txtNama, txtUnitPerawatan, txtJadwal, txtViewName;

        LinearLayout linearLayoutListDokter;

        DokterViewHolder(View itemView) {
            super(itemView);
            txtViewName = (TextView) itemView.findViewById(R.id.txtViewName);
            imgDokter = (ImageView) itemView.findViewById(R.id.imgDokter);
            txtNama = (TextView) itemView.findViewById(R.id.txtNama);
            txtUnitPerawatan = (TextView) itemView.findViewById(R.id.txtUnitPerawatan);
            txtJadwal = (TextView) itemView.findViewById(R.id.txtJadwal);

            linearLayoutListDokter = (LinearLayout) itemView.findViewById(R.id.linearLayoutListDokter);
        }
    }
}
