package com.yarsi.yarsi;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.yarsi.yarsi.model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class DokterListAdapter extends RecyclerView.Adapter<DokterListAdapter.DokterViewHolder> {

    private List<Dokter> dokterList, filterList;
    private Context context;

    private static int currentPosition = 0;

    public DokterListAdapter(Context mContext, List<Dokter> dokterList) {
        this.dokterList = dokterList;
        this.context = mContext;
        this.filterList = new ArrayList<Dokter>();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.dokterList);
    }


    @Override
    public DokterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dokter_item, parent, false);
        return new DokterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final DokterViewHolder holder, final int position) {
        final Dokter dokter = filterList.get(position);
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
        return (null != filterList ? filterList.size() : 0);
    }

    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Clear the filter list
                filterList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    filterList.addAll(dokterList);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (Dokter item : dokterList) {
                        if (item.namaDepan.toLowerCase().contains(text.toLowerCase())) {
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }

    class DokterViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imgDokter;
        protected TextView txtNama, txtUnitPerawatan, txtJadwal, txtViewName;

        protected LinearLayout linearLayoutListDokter;

        DokterViewHolder(View itemView) {
            super(itemView);
            this.txtViewName = (TextView) itemView.findViewById(R.id.txtViewName);
            this.imgDokter = (ImageView) itemView.findViewById(R.id.imgDokter);
            this.txtNama = (TextView) itemView.findViewById(R.id.txtNama);
            this.txtUnitPerawatan = (TextView) itemView.findViewById(R.id.txtUnitPerawatan);
            this.txtJadwal = (TextView) itemView.findViewById(R.id.txtJadwal);

            this.linearLayoutListDokter = (LinearLayout) itemView.findViewById(R.id.linearLayoutListDokter);
        }
    }
}
