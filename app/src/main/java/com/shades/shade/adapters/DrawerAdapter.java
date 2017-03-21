package com.shades.shade.adapters;


import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shades.shade.R;
import com.shades.shade.model.DrawerItem;

import java.util.ArrayList;

import static com.shades.shade.R.id.recyclerViewDrawer;


public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    private ArrayList<DrawerItem> drawerItems;
    private int color;

    public DrawerAdapter(Context context, ArrayList<DrawerItem> items) {
        this.drawerItems = items;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        color = typedValue.data;
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
        DrawerItem mDrawerItem = drawerItems.get(position);

        holder.title.setText(mDrawerItem.getTitle());
        holder.icon.setImageDrawable(mDrawerItem.getIcon());
        setItemSelected(holder, mDrawerItem.isSelected());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        RelativeLayout parent;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textViewDrawerItemTitle);
            icon = (ImageView) view.findViewById(R.id.imageViewDrawerIcon);
            parent = (RelativeLayout) view.findViewById(R.id.relativeLayoutDrawerItem);
        }
    }

    private void setItemSelected(ViewHolder holder, boolean selected) {
        ImageView imageViewDrawerIcon = holder.icon;
        TextView textViewDrawerTitle = holder.title;
        RelativeLayout relativeLayoutDrawerItem = holder.parent;

        if (selected) {
            imageViewDrawerIcon.setColorFilter(color);
            if (Build.VERSION.SDK_INT > 15) {
                imageViewDrawerIcon.setImageAlpha(255);
            } else {
                imageViewDrawerIcon.setAlpha(255);
            }
            textViewDrawerTitle.setTextColor(color);
            TypedValue typedValueDrawerSelected = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValueDrawerSelected, true);
            int colorDrawerItemSelected = typedValueDrawerSelected.data;
            colorDrawerItemSelected = (colorDrawerItemSelected & 0x00FFFFFF) | 0x30000000;
            relativeLayoutDrawerItem.setBackgroundColor(colorDrawerItemSelected);
        } else {
            imageViewDrawerIcon.setColorFilter(context.getResources().getColor(R.color.textColor));
            if (Build.VERSION.SDK_INT > 15) {
                imageViewDrawerIcon.setImageAlpha(230);
            } else {
                imageViewDrawerIcon.setAlpha(230);
            }
            textViewDrawerTitle.setTextColor(context.getResources().getColor(R.color.textColor));
            relativeLayoutDrawerItem.setBackgroundColor(context.getResources().getColor(R.color.md_white_1000));
        }
    }
}


