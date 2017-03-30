
package com.shades.shade.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shades.shade.R;
import com.shades.shade.activities.HomeActivity;
import com.shades.shade.interfaces.EditTextWatcherListener;
import com.shades.shade.widgets.MyTextWatcher;
import com.shades.shade.widgets.ShadeEditText;
import com.shades.shade.widgets.ShadeTextView;

import java.util.ArrayList;

public class FragmentCheckIn extends Fragment implements EditTextWatcherListener {

    private static final String TAG = "FragmentCheckIn";
    private static Context context;
    private String[] state = {"High", "Medium", "Normal", "Low", "Very Low"};
    private ArrayList<ImageView> listSmiley = new ArrayList<>();
    private ShadeEditText edtFelling;
    private ShadeTextView smileyState;


    public static FragmentCheckIn newInstance(int position, Context mContext) {
        FragmentCheckIn f = new FragmentCheckIn();
        context = mContext;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkin, null);
        setupView(view);
        initSmileyClickListener();
        return view;
    }

    @Override
    public void onTextChanges(View v) {
        showContextMenu();
    }

    @Override
    public void onTextChangesToZero(View v) {
        hideContextMenu();
    }

    private void setupView(View v) {
        smileyState = (ShadeTextView) v.findViewById(R.id.frg_checkIn_smileyState);
        edtFelling = (ShadeEditText) v.findViewById(R.id.frg_checkIn_edtFelling);
        listSmiley.add((ImageView) v.findViewById(R.id.frg_checkIn_imgSmiley1));
        listSmiley.add((ImageView) v.findViewById(R.id.frg_checkIn_imgSmiley2));
        listSmiley.add((ImageView) v.findViewById(R.id.frg_checkIn_imgSmiley3));
        listSmiley.add((ImageView) v.findViewById(R.id.frg_checkIn_imgSmiley4));
        listSmiley.add((ImageView) v.findViewById(R.id.frg_checkIn_imgSmiley5));

        edtFelling.addTextChangedListener(new MyTextWatcher(this, edtFelling));
    }

    private void initSmileyClickListener() {
        for (int i = 0; i < listSmiley.size(); i++) {
            listSmiley.get(i).setTag(i);
            listSmiley.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int TapIndex = Integer.valueOf(String.valueOf(view.getTag()));
                    selectSmiley(TapIndex);
                    showContextMenu();
                }
            });
        }
    }

    private void selectSmiley(int index) {
        for (int i = 0; i < listSmiley.size(); i++) {
            listSmiley.get(i).setSelected((index == i) ? true : false);
            setState(state[index]);
        }
    }

    private void setState(String state) {
        smileyState.setText(state);
    }

    private void showContextMenu() {
        if (((HomeActivity) getActivity()).layoutMain.getChildCount() == 1)
            ((HomeActivity) getActivity()).showCheckInContextMenu(true);
    }

    private void hideContextMenu() {
        ((HomeActivity) getActivity()).showCheckInContextMenu(false);
    }

    public void saveDataCall() {
        //TODO YOUR CHANGE DATA

        //TODO AFTER COMPLETE YOUR TASK HIDE THE MENU
        hideContextMenu();
    }

}