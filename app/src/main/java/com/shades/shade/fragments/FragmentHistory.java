
package com.shades.shade.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shades.shade.R;


public class FragmentHistory extends Fragment {

    private static final String TAG = "FragmentCheckIn";
    private static Context context;


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
    }
}
