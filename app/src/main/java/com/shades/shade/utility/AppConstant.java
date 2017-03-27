package com.shades.shade.utility;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;


public class AppConstant {
    public static enum DrawerItems {
        Settings, ChatWithUs, HelpArticles, LegalInformation, ChangePassword, SignOut
    }

    public static void showSnakeBar(View view, String message, boolean isShowingButton, String btnName, View.OnClickListener mOnClickListener) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        if (isShowingButton) {
            snackbar.setAction(btnName, mOnClickListener);
            snackbar.setActionTextColor(Color.RED);
        }
        snackbar.show();
    }

    public static void showSnakeBarShortTIme(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
