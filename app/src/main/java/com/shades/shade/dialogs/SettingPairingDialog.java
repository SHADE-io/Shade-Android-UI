package com.shades.shade.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.shades.shade.R;
import com.shades.shade.widgets.ShadeTextView;


public class SettingPairingDialog implements DialogInterface, View.OnClickListener {

    private Context context;
    private Dialog dialog;

    private DialogButtonClickListener buttonClickListener;


    /**
     * @param context
     * @param buttonClickListener
     */
    public SettingPairingDialog(Context context, DialogButtonClickListener buttonClickListener) {
        this.context = context;
        this.buttonClickListener = buttonClickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alert_dialog_negetiveButton:
                if (buttonClickListener != null)
                    buttonClickListener.onNegativeButtonClick();
                break;
        }
    }

    @Override
    public void prepareDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.inflate_alert_dialog);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ShadeTextView txtNegetiveButton = (ShadeTextView) dialog.findViewById(R.id.settings_dialog_negativeButton);
        txtNegetiveButton.setOnClickListener(this);
    }

    @Override
    public void showDialog() {
        if (dialog != null)
            dialog.show();
    }

    @Override
    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
