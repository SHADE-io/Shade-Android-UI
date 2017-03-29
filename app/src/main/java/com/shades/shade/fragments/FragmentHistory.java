
package com.shades.shade.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shades.shade.R;
import com.shades.shade.activities.AllDataActivity;
import com.shades.shade.activities.ChangePasswordActivity;
import com.shades.shade.dialogs.UVExposureDialog;
import com.shades.shade.widgets.ShadeTextView;


public class FragmentHistory extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentCheckIn";
    private static Context context;
    private ShadeTextView dailyLimit;
    private ShadeTextView txt_TotalDays;
    private ShadeTextView txt_AvgDays;
    private ShadeTextView txt_YDailyLimit;
    private ShadeTextView txt_YHighUV;
    private ImageView img_AvgCheckIn;
    private UVExposureDialog uvExposureDialog = null;

    public static FragmentHistory newInstance(int position, Context mContext) {
        FragmentHistory f = new FragmentHistory();
        context = mContext;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, null);
        setupView(view);
        return view;
    }

    private void setupView(View v) {
        context = getActivity();
        ((ImageView) v.findViewById(R.id.frg_history_imgMenu)).setOnClickListener(this);

        ((LinearLayout) v.findViewById(R.id.frg_history_allData)).setOnClickListener(this);
        dailyLimit = (ShadeTextView) v.findViewById(R.id.frg_history_dailyLimit);
        txt_TotalDays = (ShadeTextView) v.findViewById(R.id.frg_history_txt_TotalDays);
        txt_AvgDays = (ShadeTextView) v.findViewById(R.id.frg_history_txt_AvgDays);
        txt_YDailyLimit = (ShadeTextView) v.findViewById(R.id.frg_history_txt_YDailyLimit);
        txt_YHighUV = (ShadeTextView) v.findViewById(R.id.frg_history_txt_YHighUV);
        img_AvgCheckIn = (ImageView) v.findViewById(R.id.frg_history_img_AvgCheckIn);


        dailyLimit.setText("Daily Limit: 25 UV Units");
        txt_TotalDays.setText("1 Day");
        txt_AvgDays.setText("34 UV Units");
        txt_YDailyLimit.setText("—");
        txt_YHighUV.setText("—");
        img_AvgCheckIn.setImageResource(R.drawable.alldata_emoji_1);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_history_imgMenu:
                onOptionMenuClick();
                break;
            case R.id.frg_history_allData:
                getActivity().startActivity(new Intent(context, AllDataActivity.class));
                getActivity().overridePendingTransition(0, 0);
                break;
        }
    }


    private void onOptionMenuClick() {
        uvExposureDialog = new UVExposureDialog(context, new UVExposureDialog.UVExposureDialogClickListener() {
            @Override
            public void onEditDailyLimitClick() {
                uvExposureDialog.dismissDialog();
            }

            @Override
            public void onZoomInClick() {
                uvExposureDialog.dismissDialog();
            }

            @Override
            public void onZoomOutClick() {
                uvExposureDialog.dismissDialog();
            }
        });
        uvExposureDialog.prepareDialog();
        uvExposureDialog.showDialog();
    }
}
