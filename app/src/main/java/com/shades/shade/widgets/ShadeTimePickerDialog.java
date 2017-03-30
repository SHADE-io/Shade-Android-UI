package com.shades.shade.widgets;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.shades.shade.R;

public class ShadeTimePickerDialog  extends TimePickerDialog {

    private final static int TIME_PICKER_INTERVAL = 5;
    private TimePicker mTimePicker;
    private final OnTimeSetListener mTimeSetListener;

    public ShadeTimePickerDialog(Context context, OnTimeSetListener listener,
                                  int hourOfDay, int minute, boolean is24HourView) {
        super(context, TimePickerDialog.THEME_HOLO_LIGHT, listener, hourOfDay,
                minute / TIME_PICKER_INTERVAL, is24HourView);
        mTimeSetListener = listener;
    }

    @Override
    public void updateTime(int hourOfDay, int minuteOfHour) {
        mTimePicker.setCurrentHour(hourOfDay);
        mTimePicker.setCurrentMinute(minuteOfHour / TIME_PICKER_INTERVAL);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                if (mTimeSetListener != null) {
                    mTimeSetListener.onTimeSet(mTimePicker, mTimePicker.getCurrentHour(),
                            mTimePicker.getCurrentMinute() * TIME_PICKER_INTERVAL);
                }
                break;
            case BUTTON_NEGATIVE:
                cancel();
                break;
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            Field timePickerField = classForid.getField("timePicker");
            mTimePicker = (TimePicker) findViewById(timePickerField.getInt(null));
            Field field = classForid.getField("minute");

            NumberPicker minuteSpinner = (NumberPicker) mTimePicker
                    .findViewById(field.getInt(null));
            minuteSpinner.setMinValue(0);
            minuteSpinner.setMaxValue((35 / TIME_PICKER_INTERVAL) - 1);
            List<String> displayedValues = new ArrayList<>();
            for (int i = 0; i < 35; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            minuteSpinner.setDisplayedValues(displayedValues.toArray(new String[displayedValues.size()]));

            try {
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Regular.ttf");
                Typeface typefaceM = Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Medium.ttf");

                int dividerId = getContext().getResources().getIdentifier
                        ("android:id/titleDivider", null, null);
                View divider = findViewById(dividerId);
                divider.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));

                int textViewId = getContext().getResources().getIdentifier
                        ("android:id/alertTitle", null, null);
                TextView tv = (TextView) findViewById(textViewId);
                tv.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
                tv.setText("Set time");
                tv.setTypeface(typeface, Typeface.NORMAL);

                int pButtonId = getContext().getResources().getIdentifier
                        ("android:id/button1", null, null);
                Button pBtn = (Button) findViewById(pButtonId);
                pBtn.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
                pBtn.setText("SET");
                pBtn.setTypeface(typefaceM, Typeface.NORMAL);


                int nButtonId = getContext().getResources().getIdentifier
                        ("android:id/button2", null, null);
                Button nBtn = (Button) findViewById(nButtonId);
                nBtn.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
                nBtn.setText("CANCEL");
                nBtn.setTypeface(typefaceM, Typeface.NORMAL);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                colorizeDatePicker(mTimePicker);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void colorizeDatePicker(TimePicker datePicker) {
        try {
            Resources system = Resources.getSystem();
            int dayId = system.getIdentifier("hour", "id", "android");
            int monthId = system.getIdentifier("minute", "id", "android");
            int yearId = system.getIdentifier("amPm", "id", "android");

            NumberPicker dayPicker = (NumberPicker) datePicker.findViewById(dayId);
            NumberPicker monthPicker = (NumberPicker) datePicker.findViewById(monthId);
            NumberPicker yearPicker = (NumberPicker) datePicker.findViewById(yearId);

            setDividerColor(dayPicker);
            setDividerColor(monthPicker);
            setDividerColor(yearPicker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setDividerColor(NumberPicker picker) {
        if (picker == null)
            return;

        final int count = picker.getChildCount();
        for (int i = 0; i < count; i++) {
            try {
                Field dividerField = picker.getClass().getDeclaredField("mSelectionDivider");
                dividerField.setAccessible(true);
                ColorDrawable colorDrawable = new ColorDrawable(picker.getResources().getColor(R.color.colorAccent));
                dividerField.set(picker, colorDrawable);
                picker.invalidate();
            } catch (Exception e) {
                Log.w("setDividerColor", e);
            }
        }
    }

}
