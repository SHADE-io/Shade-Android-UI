package com.shades.shade.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.shades.shade.R;


public class UVExposureDialog implements DialogInterface, View.OnClickListener {

    private Context context;
    private Dialog dialog;

    private UVExposureDialogClickListener buttonClickListener;


    /**
     * @param context
     * @param buttonClickListener
     */
    public UVExposureDialog(Context context, UVExposureDialogClickListener buttonClickListener) {
        this.context = context;
        this.buttonClickListener = buttonClickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_uvecposure_editDailyLimit:
                if (buttonClickListener != null)
                    buttonClickListener.onEditDailyLimitClick();
                break;
            case R.id.dialog_uvecposure_zoomIn:
                if (buttonClickListener != null)
                    buttonClickListener.onZoomInClick();
                break;
            case R.id.dialog_uvecposure_zoomOut:
                if (buttonClickListener != null)
                    buttonClickListener.onZoomOutClick();
                break;
        }
    }

    @Override
    public void prepareDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.inflate_dialog_uvexposure);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout editDailyLimit = (LinearLayout) dialog.findViewById(R.id.dialog_uvecposure_editDailyLimit);
        LinearLayout zoomIn = (LinearLayout) dialog.findViewById(R.id.dialog_uvecposure_zoomIn);
        LinearLayout zoomOut = (LinearLayout) dialog.findViewById(R.id.dialog_uvecposure_zoomOut);

        editDailyLimit.setOnClickListener(this);
        zoomIn.setOnClickListener(this);
        zoomOut.setOnClickListener(this);

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

    public interface UVExposureDialogClickListener {
        void onEditDailyLimitClick();

        void onZoomInClick();

        void onZoomOutClick();
    }

}
