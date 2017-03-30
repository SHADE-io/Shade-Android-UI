package com.shades.shade.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.shades.shade.utility.ShadePref;

public class UserDetail {
    public String userId = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void saveUser(Context context) {
        SharedPreferences.Editor prefsEditor = ShadePref.getInstance(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(this);
        prefsEditor.putString(ShadePref.KEY_LOGGED_IN_USER, json);
        prefsEditor.commit();
    }

    public static UserDetail getLoggedInUser(Context context) {
        SharedPreferences mPrefs = ShadePref.getInstance(context);
        Gson gson = new Gson();
        String json = mPrefs.getString(ShadePref.KEY_LOGGED_IN_USER, "");
        UserDetail obj = gson.fromJson(json, UserDetail.class);
        return obj;
    }

    public static void logoutUser(Context context) {
        SharedPreferences.Editor prefsEditor = ShadePref.getInstance(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(null);
        prefsEditor.putString(ShadePref.KEY_LOGGED_IN_USER, json);
        prefsEditor.commit();
    }
}
