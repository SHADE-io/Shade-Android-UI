package com.shades.shade.dialogs;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.widget.LinearLayout;

import com.shades.shade.R;

public class ShadeProgressDialog {

    private Context context;
    private ProgressDialog pgDialog;

    public ShadeProgressDialog(Context context) {
        this.context = context;
    }

    public void prepareAndShowDialog() {
        pgDialog = new ProgressDialog(context);
        pgDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pgDialog.setCancelable(false);
        pgDialog.show();
        pgDialog.setContentView(R.layout.inflate_pgdialog);
        Window window = pgDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void dismissDialog() {
        if (pgDialog != null) {
            pgDialog.dismiss();
            pgDialog = null;
        }
    }
}
