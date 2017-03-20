package com.shades.shade.widgets;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.shades.shade.interfaces.EditTextWatcherListener;


public class MyTextWatcher implements TextWatcher {

    private View view;
    private EditTextWatcherListener editTextWatcherListener;

    public MyTextWatcher(EditTextWatcherListener watcherListener, View view) {
        this.view = view;
        this.editTextWatcherListener = watcherListener;
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void afterTextChanged(Editable editable) {

        if (editable.length() == 0) {
            if (editTextWatcherListener != null) {
                editTextWatcherListener.onTextChanges(view);
            }
        } else {
            if (editTextWatcherListener != null) {
                editTextWatcherListener.onTextChanges(view);
            }
        }

    }
}