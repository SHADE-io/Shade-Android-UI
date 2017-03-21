
package com.shades.shade.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shades.shade.R;


public class FragmentDashboard extends Fragment {

    private static final String TAG = "FragmentCheckIn";
    private static Context context;


    public static FragmentDashboard newInstance(int position, Context mContext) {
        FragmentDashboard f = new FragmentDashboard();
        context = mContext;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, null);
        setupView(view);
        return view;
    }

    private void setupView(View v) {
    }
}
