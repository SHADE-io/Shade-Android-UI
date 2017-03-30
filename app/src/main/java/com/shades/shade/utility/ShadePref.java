package com.shades.shade.utility;


import android.content.Context;
import android.content.SharedPreferences;

public class ShadePref {

    public static final String SMILEY_SELECTED = "smiley_selected";
public static final String KEY_LOGGED_IN_USER = "logged_in_user";

    public static SharedPreferences getInstance(Context context) {
        return context.getSharedPreferences("shadePref", Context.MODE_PRIVATE);
    }

    private static void setSmileySelection(Context context, int selectedIndex) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putInt(SMILEY_SELECTED, selectedIndex);
        editor.commit();
    }

    private static int getSmileySelection(Context context) {
        return getInstance(context).getInt(SMILEY_SELECTED, -1);
    }
}
