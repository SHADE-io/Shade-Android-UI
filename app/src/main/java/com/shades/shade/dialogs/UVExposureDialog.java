package com.shades.shade.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shades.shade.R;


public class UVExposureDialog implements DialogInterface, View.OnClickListener {

    private Context context;
    private Dialog dialog;

    private UVExposureDialogClickListener buttonClickListener;
    private LinearLayout editDailyLimit;
    private LinearLayout zoomIn;
    private LinearLayout zoomOut;
    private ImageView img_zoomIn;
    private ImageView img_zoomOut;

    private static boolean isZoomIn = false;


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
                isZoomIn = true;
                zoomIn_Out();
                if (buttonClickListener != null)
                    buttonClickListener.onZoomInClick();
                break;
            case R.id.dialog_uvecposure_zoomOut:
                isZoomIn = false;
                zoomIn_Out();
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
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        editDailyLimit = (LinearLayout) dialog.findViewById(R.id.dialog_uvecposure_editDailyLimit);
        zoomIn = (LinearLayout) dialog.findViewById(R.id.dialog_uvecposure_zoomIn);
        zoomOut = (LinearLayout) dialog.findViewById(R.id.dialog_uvecposure_zoomOut);

        img_zoomIn = (ImageView) dialog.findViewById(R.id.dialog_uvecposure_img_zoomIn);
        img_zoomOut = (ImageView) dialog.findViewById(R.id.dialog_uvecposure_img_zoomOut);

        editDailyLimit.setOnClickListener(this);
        zoomIn.setOnClickListener(this);
        zoomOut.setOnClickListener(this);

        zoomIn_Out();

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

    private void zoomIn_Out() {
        if (isZoomIn) {
            img_zoomIn.setSelected(true);
            zoomIn.setSelected(true);
            img_zoomOut.setSelected(false);
            zoomOut.setSelected(false);
            isZoomIn = true;
        } else {
            img_zoomIn.setSelected(false);
            zoomIn.setSelected(false);
            img_zoomOut.setSelected(true);
            zoomOut.setSelected(true);
            isZoomIn = false;
        }
    }

    public interface UVExposureDialogClickListener {
        void onEditDailyLimitClick();

        void onZoomInClick();

        void onZoomOutClick();
    }

}
