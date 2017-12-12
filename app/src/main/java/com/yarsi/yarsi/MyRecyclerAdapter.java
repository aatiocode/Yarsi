package com.yarsi.yarsi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yarsi.yarsi.model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyCustomViewHolder> {

    private List<ListItem> listItems, filterList;
    private Context mContext;

    public MyRecyclerAdapter(Context context, List<ListItem> listItems) {
        this.listItems = listItems;
        this.mContext = context;
        this.filterList = new ArrayList<ListItem>();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.listItems);
    }

    @Override
    public MyRecyclerAdapter.MyCustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_row_item, null);
        MyCustomViewHolder viewHolder = new MyCustomViewHolder(view);
        return viewHolder;

    }

    @Override
        public void onBindViewHolder(MyCustomViewHolder customViewHolder, int position) {

        final ListItem listItem = filterList.get(position);
        Uri uri = Uri.parse(listItem.logo);
        customViewHolder.tvName.setText(listItem.name);
        Picasso.with(mContext).load(uri).into(customViewHolder.imgThumb);

        customViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idPoli = listItem.name;
                Intent intent = new Intent(mContext,DokterListActivity.class);
                intent.putExtra("poli_id",idPoli);
                mContext.startActivity(intent);
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

                    filterList.addAll(listItems);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (ListItem item : listItems) {
                        if (item.name.toLowerCase().contains(text.toLowerCase())) {
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }

    public class MyCustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvName;
        protected ImageView imgThumb;
        protected RelativeLayout relativeLayout;

        public MyCustomViewHolder(View view) {
            super(view);
            this.tvName = (TextView) view.findViewById(R.id.tvName);
            this.imgThumb = (ImageView) view.findViewById(R.id.imgThumb);
            this.relativeLayout = (RelativeLayout) view.findViewById(R.id.singleRow);

        }

    }

}
