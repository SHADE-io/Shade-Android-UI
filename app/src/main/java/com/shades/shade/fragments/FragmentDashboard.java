
package com.shades.shade.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shades.shade.R;
import com.shades.shade.widgets.ShadeTextView;
import com.shades.shade.widgets.UVIndexProgress;


public class FragmentDashboard extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentCheckIn";
    private static Context context;

    private enum SensorSection {
        VIEW_HISTORY, DAILY_LIMIT, SENSOR_NOT_DETECTING
    }

    private RelativeLayout relativeLayout;
    private LayoutInflater inflater;
    private UVIndexProgress UVIndex;
    private ShadeTextView txt_UVIndex;

    public static FragmentDashboard newInstance(int position, Context mContext) {
        FragmentDashboard f = new FragmentDashboard();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, null);
        setupView(view);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_txt_viewHistory:
                break;
            case R.id.img_rightArrow:
                break;
        }
    }

    private void setupView(View v) {
        context = getActivity();
        inflater = LayoutInflater.from(context);

        relativeLayout = (RelativeLayout) v.findViewById(R.id.frg_sensor_statusLayout);
        UVIndex = (UVIndexProgress) v.findViewById(R.id.frg_prg_UVIndex);
        UVIndex = (UVIndexProgress) v.findViewById(R.id.frg_prg_UVIndex);
        txt_UVIndex = (ShadeTextView) v.findViewById(R.id.frg_txt_UVIndex);

        //Test Data
        setLayoutFor(SensorSection.DAILY_LIMIT);
        showUVIndex(5.3f);
    }

    private void setLayoutFor(SensorSection layoutFor) {
        if (relativeLayout != null && relativeLayout.getChildCount() >= 1) {
            relativeLayout.removeAllViews();
        }
        switch (layoutFor) {
            case VIEW_HISTORY:
                loadViewHistory();
                break;

            case DAILY_LIMIT:
                loadDailyLimit(53.5f);
                break;

            case SENSOR_NOT_DETECTING:
                loadSensorDetecting();
                break;
        }
    }

    /**
     * Set your UV Index as per your device calculate
     *
     * @param uvIndex
     */
    private void showUVIndex(float uvIndex) {
        if (uvIndex > 0) {
            UVIndex.setPGIndex(uvIndex);
            txt_UVIndex.setText("" + uvIndex);
        } else {
            UVIndex.setPGIndex(0);
            txt_UVIndex.setText("0.00");
        }

    }

    /**
     * Load View History Layout
     */
    private void loadViewHistory() {
        View view = inflater.inflate(R.layout.inflate_dashboard_viewhistory, null);
        ((ShadeTextView) view.findViewById(R.id.frg_txt_viewHistory)).setOnClickListener(this);
        relativeLayout.addView(view);
    }

    /**
     * Load View Daily Limit Layout
     */
    private void loadDailyLimit(float value) {
        try {
            View view = inflater.inflate(R.layout.inflate_dashboard_dailylimit, null);
            ((ImageView) view.findViewById(R.id.img_rightArrow)).setOnClickListener(this);
            ((ShadeTextView) view.findViewById(R.id.frg_txt_currentLimit)).setText("" + value);
            relativeLayout.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load View Sensor Not Detecting Layout
     */
    private void loadSensorDetecting() {
        View view = inflater.inflate(R.layout.inflate_dashboard_viewhistory, null);
        ((ShadeTextView) view.findViewById(R.id.frg_txt_viewHistory)).setOnClickListener(this);
        relativeLayout.addView(view);
    }

}
