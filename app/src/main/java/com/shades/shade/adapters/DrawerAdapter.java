package com.shades.shade.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shades.shade.R;
import com.shades.shade.model.DrawerItem;

import java.util.ArrayList;


public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    private ArrayList<DrawerItem> drawerItems;

    public DrawerAdapter(Context context, ArrayList<DrawerItem> items) {
        this.drawerItems = items;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return drawerItems.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.inflate_drawer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(drawerItems.get(position).getTitle());
        holder.icon.setImageDrawable(drawerItems.get(position).getIcon());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textViewDrawerItemTitle);
            icon = (ImageView) view.findViewById(R.id.imageViewDrawerIcon);
        }
    }
}


