package com.shades.shade.model;

import android.graphics.drawable.Drawable;

public class DrawerItem {
    String title;
    Drawable icon;
    boolean isSelected;

    public DrawerItem(String title, Drawable icon, boolean isSelected) {
        this.title = title;
        this.icon = icon;
        this.isSelected = isSelected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
