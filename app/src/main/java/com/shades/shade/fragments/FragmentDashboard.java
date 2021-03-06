
package com.shades.shade.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shades.shade.R;
import com.shades.shade.activities.SettingActivity;
import com.shades.shade.widgets.ShadeTextView;
import com.shades.shade.widgets.UVIndexProgress;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;


public class FragmentDashboard extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentCheckIn";
    private static Context context;

    private enum SensorSection {
        VIEW_HISTORY, DAILY_LIMIT, SENSOR_NOT_DETECTING, BLUETOOTH_OFF, TROUBLE_DETECTING, PAIR_SENSOR, WEAR_SHADE, OVER_DAILY_LIMIT, MORE_THEN_HR
    }

    private enum DashboardState {
        DASHBOARD_NOT_PAIRED, DASHBOARD_FIRST_DAY_USE, DASHBOARD_DAILY_LIMIT_NOT_SET, DASHBOARD_NO_UV, DASHBOARD_NORMAL, DASHBOARD_OVER_DAILY_LIMIT
    }

    private enum BluetoothState {
        BLUETOOTH_OFF, BLUETOOTH_NOT_CONNECTED, BLUETOOTH_CONNECTED
    }

    private DashboardState dashboardState = DashboardState.DASHBOARD_NORMAL;
    private BluetoothState bluetoothState = BluetoothState.BLUETOOTH_CONNECTED;

    private enum SPF {
        SHOW_ADDSPF, SHOW_SPF_APPLY, SHOW_SPF_TIMER
    }

    private RelativeLayout relativeLayout;
    private LayoutInflater inflater;
    private UVIndexProgress UVIndex;
    private ShadeTextView txt_UVIndex;
    private ShadeTextView txt_accumulated_unit;

    private RelativeLayout frg_SPFLayout;
    private ShadeTextView spf_txt_time;
    private ShadeTextView spf_btn_add;

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
        }
    }

    private void setupView(View v) {
        context = getActivity();
        inflater = LayoutInflater.from(context);

        relativeLayout = (RelativeLayout) v.findViewById(R.id.frg_sensor_statusLayout);
        UVIndex = (UVIndexProgress) v.findViewById(R.id.frg_prg_UVIndex);
        UVIndex = (UVIndexProgress) v.findViewById(R.id.frg_prg_UVIndex);
        txt_UVIndex = (ShadeTextView) v.findViewById(R.id.frg_txt_UVIndex);

        txt_accumulated_unit = (ShadeTextView) v.findViewById(R.id.frg_accumulated_unit);

        frg_SPFLayout = (RelativeLayout) v.findViewById(R.id.frg_SPFLayout);
        spf_txt_time = (ShadeTextView) v.findViewById(R.id.frg_spf_txt_time);
        spf_btn_add = (ShadeTextView) v.findViewById(R.id.frg_spf_btn_add);

        //Test Data
//        setLayoutFor(SensorSection.PAIR_SENSOR);
//        setLayoutFor(SensorSection.VIEW_HISTORY);
//        setLayoutFor(SensorSection.DAILY_LIMIT);
//        setLayoutFor(SensorSection.SENSOR_NOT_DETECTING);
//        setLayoutFor(SensorSection.BLUETOOTH_OFF);
//        setLayoutFor(SensorSection.TROUBLE_DETECTING);
//        setLayoutFor(SensorSection.WEAR_SHADE);
//        setLayoutFor(SensorSection.OVER_DAILY_LIMIT);
//        setLayoutFor(SensorSection.MORE_THEN_HR);

        setLayoutForTopCard();

        showUVIndex(5.3f);
        showAccumulativeUnit(46f);
        setLayoutForSPF(SPF.SHOW_ADDSPF, true);
    }

    //TODO: Implement this function to show the top card
    private void setLayoutForTopCard() {
        if (dashboardState == DashboardState.DASHBOARD_NOT_PAIRED) {
            removeAllLayout();
            setLayoutFor(SensorSection.PAIR_SENSOR);
        } else if (dashboardState == DashboardState.DASHBOARD_FIRST_DAY_USE) {
            removeAllLayout();
            setLayoutFor(SensorSection.WEAR_SHADE);
            if (bluetoothState == BluetoothState.BLUETOOTH_OFF) {
                // TODO: Add right arrow and underlying card with layout for SensorSection.BLUETOOTH_OFF
                setLayoutFor(SensorSection.BLUETOOTH_OFF);
            } else if (bluetoothState == BluetoothState.BLUETOOTH_NOT_CONNECTED) {
                // TODO: Add right arrow and underlying card with layout for SensorSection.TROUBLE_DETECTING
                setLayoutFor(SensorSection.TROUBLE_DETECTING);
            }
        } else if (dashboardState == DashboardState.DASHBOARD_DAILY_LIMIT_NOT_SET) {
            removeAllLayout();
            setLayoutFor(SensorSection.VIEW_HISTORY);
            if (bluetoothState == BluetoothState.BLUETOOTH_OFF) {
                // TODO: Add right arrow and underlying card with layout for SensorSection.BLUETOOTH_OFF
                setLayoutFor(SensorSection.BLUETOOTH_OFF);
            } else if (bluetoothState == BluetoothState.BLUETOOTH_NOT_CONNECTED) {
                // TODO: Add right arrow and underlying card with layout for SensorSection.TROUBLE_DETECTING
                setLayoutFor(SensorSection.TROUBLE_DETECTING);
            }
        } else if (dashboardState == DashboardState.DASHBOARD_NO_UV) {
            removeAllLayout();
            setLayoutFor(SensorSection.DAILY_LIMIT);
            if (bluetoothState == BluetoothState.BLUETOOTH_OFF) {
                // TODO: Add right arrow and underlying card with layout for SensorSection.BLUETOOTH_OFF
                setLayoutFor(SensorSection.BLUETOOTH_OFF);
            } else if (bluetoothState == BluetoothState.BLUETOOTH_NOT_CONNECTED) {
                // TODO: Add right arrow and underlying card with layout for SensorSection.TROUBLE_DETECTING
                setLayoutFor(SensorSection.TROUBLE_DETECTING);
            } else {
                // TODO: Add right arrow and underlying card with layout for SensorSection.SENSOR_NOT_DETECTING
                setLayoutFor(SensorSection.SENSOR_NOT_DETECTING);
            }
        } else if (dashboardState == DashboardState.DASHBOARD_NORMAL || dashboardState == DashboardState.DASHBOARD_OVER_DAILY_LIMIT) {
            removeAllLayout();
            setLayoutFor(SensorSection.DAILY_LIMIT);
            if (bluetoothState == BluetoothState.BLUETOOTH_OFF) {
                // TODO: Add right arrow and underlying card with layout for SensorSection.BLUETOOTH_OFF
                setLayoutFor(SensorSection.BLUETOOTH_OFF);
            } else if (bluetoothState == BluetoothState.BLUETOOTH_NOT_CONNECTED) {
                // TODO: Add right arrow and underlying card with layout for SensorSection.TROUBLE_DETECTING
                setLayoutFor(SensorSection.TROUBLE_DETECTING);
            } else {
                if (dashboardState == DashboardState.DASHBOARD_OVER_DAILY_LIMIT) {
                    //TODO: Add right arrow and underlying card with layout for SensorSection.OVER_DAILY_LIMIT
                    setLayoutFor(SensorSection.OVER_DAILY_LIMIT);
                } else {
                    //TODO: Add right arrow and underlying card with layout for SensorSection.MORE_THEN_HR
                    setLayoutFor(SensorSection.MORE_THEN_HR);
                }
            }
        }
    }

    private void removeTopLayout() {
        if (relativeLayout != null && relativeLayout.getChildCount() == 2) {
            relativeLayout.removeViewAt(1);
        }
    }

    private void removeBottomLayout() {
        if (relativeLayout != null && relativeLayout.getChildCount() == 1) {
            relativeLayout.removeViewAt(0);
        }
    }

    private void removeAllLayout() {
        if (relativeLayout != null && relativeLayout.getChildCount() >= 1) {
            relativeLayout.removeAllViews();
        }
    }

    private void bluetoothCaseForForwardClick() {
        if (bluetoothState == BluetoothState.BLUETOOTH_OFF) {
            // TODO: Add right arrow and underlying card with layout for SensorSection.BLUETOOTH_OFF
            setLayoutFor(SensorSection.BLUETOOTH_OFF);
        } else if (bluetoothState == BluetoothState.BLUETOOTH_NOT_CONNECTED) {
            // TODO: Add right arrow and underlying card with layout for SensorSection.TROUBLE_DETECTING
            setLayoutFor(SensorSection.TROUBLE_DETECTING);
        } else {
            if (dashboardState == DashboardState.DASHBOARD_OVER_DAILY_LIMIT) {
                //TODO: Add right arrow and underlying card with layout for SensorSection.OVER_DAILY_LIMIT
                setLayoutFor(SensorSection.OVER_DAILY_LIMIT);
            } else {
                //TODO: Add right arrow and underlying card with layout for SensorSection.MORE_THEN_HR
                setLayoutFor(SensorSection.MORE_THEN_HR);
            }
        }
    }

    private void setLayoutFor(SensorSection layoutFor) {
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

            case BLUETOOTH_OFF:
                loadBluetoothOff();
                break;

            case TROUBLE_DETECTING:
                loadTroubleDetecting();
                break;

            case PAIR_SENSOR:
                loadPairSensor();
                break;

            case WEAR_SHADE:
                loadWearShade();
                break;

            case OVER_DAILY_LIMIT:
                loadOverDailyLimit();
                break;

            case MORE_THEN_HR:
                loadMoreThenHr();
                break;
        }
    }

    private void setLayoutForSPF(SPF layoutFor, boolean isEnable) {
        if (frg_SPFLayout != null && frg_SPFLayout.getChildCount() >= 1) {
            frg_SPFLayout.removeAllViews();
        }
        switch (layoutFor) {
            case SHOW_ADDSPF:
                loadSpfAdd(isEnable);
                break;

            case SHOW_SPF_TIMER:
                loadSpfTimer();
                break;

            case SHOW_SPF_APPLY:
                loadSpfApply();
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
            txt_UVIndex.setTextColor(getResources().getColor(R.color.textColor));
        } else {
            UVIndex.setPGIndex(0);
            txt_UVIndex.setText("0.00");
            txt_UVIndex.setTextColor(Color.parseColor("#C7C7CC"));
        }
    }

    private void showAccumulativeUnit(float value) {
        txt_accumulated_unit.setText("Accumulated so far: " + value + " UV Units");
    }

    /**
     * Load View History Layout
     */
    private void loadViewHistory() {
        View view = inflater.inflate(R.layout.inflate_dashboard_viewhistory, null);
        view.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        ((ShadeTextView) view.findViewById(R.id.frg_txt_viewHistory)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        if (bluetoothState == BluetoothState.BLUETOOTH_NOT_CONNECTED || bluetoothState == BluetoothState.BLUETOOTH_OFF) {
            ImageView arrow = (ImageView) view.findViewById(R.id.img_rightArrow);
            arrow.setVisibility(View.VISIBLE);
            arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bluetoothCaseForForwardClick();
                }
            });
        }
        relativeLayout.addView(view);
    }

    /**
     * Pair Sensor
     */
    private void loadPairSensor() {
        View view = inflater.inflate(R.layout.inflate_dashboard_viewhistory, null);
        view.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        ((ShadeTextView) view.findViewById(R.id.frg_txt_viewHistoryDsc)).setText(context.getString(R.string.frag_dashboard_sensor_pair_sensordsc));
        ShadeTextView btnPairSensor = (ShadeTextView) view.findViewById(R.id.frg_txt_viewHistory);
        btnPairSensor.setText(context.getString(R.string.frag_dashboard_sensor_pair_sensor_btn));
        btnPairSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SettingActivity.class));
                getActivity().overridePendingTransition(0, 0);
            }
        });
        relativeLayout.addView(view);


    }


    /**
     * Load View Daily Limit Layout
     */
    private void loadDailyLimit(float value) {
        try {
            View view = inflater.inflate(R.layout.inflate_dashboard_dailylimit, null);
            view.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            ((ShadeTextView) view.findViewById(R.id.frg_txt_currentLimit)).setText("" + value);
            ((ImageView) view.findViewById(R.id.img_rightArrow)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bluetoothCaseForForwardClick();
                }
            });
            relativeLayout.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load View Sensor Not Detecting Layout
     */
    private void loadSensorDetecting() {
        View view = inflater.inflate(R.layout.inflate_dashboard_sensor_notdetecting, null);
        view.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        ((ImageView) view.findViewById(R.id.img_rightArrow)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeTopLayout();
            }
        });
        relativeLayout.addView(view);
    }

    /**
     * Load View Bluetooth Off Layout
     */
    private void loadBluetoothOff() {
        View view = inflater.inflate(R.layout.inflate_dashboard_bluetooth_off, null);
        view.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        ((ImageView) view.findViewById(R.id.img_rightArrow)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeTopLayout();
            }
        });
        relativeLayout.addView(view);
    }

    /**
     * Load View Bluetooth Off Layout
     */
    private void loadTroubleDetecting() {
        try {
            View view = inflater.inflate(R.layout.inflate_dashboard_bluetooth_off, null);
            view.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            ((ShadeTextView) view.findViewById(R.id.frg_txt_Dsc)).setText(context.getString(R.string.frag_dashboard_sensor_troubleDetecting));
            ((ImageView) view.findViewById(R.id.img_rightArrow)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeTopLayout();
                }
            });
            relativeLayout.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadWearShade() {
        try {
            View view = inflater.inflate(R.layout.inflate_dashboard_bluetooth_off, null);
            view.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            ((ShadeTextView) view.findViewById(R.id.frg_txt_Dsc)).setText(context.getString(R.string.frag_dashboard_sensor_wearShade));
            ((ImageView) view.findViewById(R.id.img_rightArrow)).setVisibility(View.INVISIBLE);
            relativeLayout.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadOverDailyLimit() {
        try {
            View view = inflater.inflate(R.layout.inflate_dashboard_overdailylimit, null);
            view.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            ((ImageView) view.findViewById(R.id.img_rightArrow)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeTopLayout();
                }
            });
            relativeLayout.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMoreThenHr() {
        try {
            View view = inflater.inflate(R.layout.inflate_dashboard_morethenhr, null);
            view.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            ((ImageView) view.findViewById(R.id.img_rightArrow)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeTopLayout();
                }
            });
            relativeLayout.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * SPF Section
     */

    private void loadSpfAdd(boolean isDeviceConnected) {
        try {
            View view = inflater.inflate(R.layout.inflate_dashboard_spfadd, null);
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            ShadeTextView btn_add = (ShadeTextView) view.findViewById(R.id.frg_spf_btn_add);
            if (isDeviceConnected) {
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setLayoutForSPF(SPF.SHOW_SPF_APPLY, true);
                    }
                });
                btn_add.setBackgroundResource(R.drawable.button_fill_small_coral);
            } else {
                btn_add.setBackgroundResource(R.drawable.button_fill_small_disabled);
            }
            frg_SPFLayout.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSpfTimer() {
        try {
            View view = inflater.inflate(R.layout.inflate_dashboard_spfadd, null);
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

            ShadeTextView txt_time = (ShadeTextView) view.findViewById(R.id.frg_spf_txt_time);
            ShadeTextView btn_add = (ShadeTextView) view.findViewById(R.id.frg_spf_btn_add);
            ((LinearLayout) view.findViewById(R.id.frg_spf_layoutTime)).setVisibility(View.VISIBLE);
            btn_add.setText("CANCEL SPF");
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setLayoutForSPF(SPF.SHOW_ADDSPF, true);
                }
            });
            frg_SPFLayout.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSpfApply() {
        try {
            View view = inflater.inflate(R.layout.inflate_dashboard_spfapply, null);
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            ((ShadeTextView) view.findViewById(R.id.frg_spf_btn_cancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setLayoutForSPF(SPF.SHOW_ADDSPF, true);
                }
            });
            ((ShadeTextView) view.findViewById(R.id.frg_spf_btn_apply)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setLayoutForSPF(SPF.SHOW_SPF_TIMER, true);
                }
            });
            DiscreteSeekBar seekBar = (DiscreteSeekBar) view.findViewById(R.id.frg_spf_seekbar);
            seekBar.setMin(0);
            seekBar.setMax(100);
            seekBar.setProgress(60);
            seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                @Override
                public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

                }
            });
            frg_SPFLayout.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
