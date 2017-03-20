package com.shades.shade.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public abstract class ShadeBaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onUiLayout();
        onUiComponentInit();
        onListenersInit();
    }

    protected abstract void onUiLayout();

    protected abstract void onUiComponentInit();

    protected abstract void onListenersInit();

    protected abstract void onListenersRemove();

}
