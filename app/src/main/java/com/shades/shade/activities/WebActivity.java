package com.shades.shade.activities;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.shades.shade.R;
import com.shades.shade.dialogs.ShadeProgressDialog;
import com.shades.shade.widgets.ShadeTextView;

public class WebActivity extends ShadeBaseActivity {

    private Context context;
    private View parent_layout;
    private ImageView topBar_batteryStatus;
    private WebView link_web;
    private ShadeProgressDialog pgDialog;

    private Bundle bundle;

    @Override
    protected void onUiLayout() {
        setContentView(R.layout.activity_web);
        context = WebActivity.this;

        bundle = getIntent().getExtras();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onUiComponentInit() {
        ((ShadeTextView) findViewById(R.id.topBar_txt_title)).setText(bundle.getString("key_title"));
        parent_layout = findViewById(R.id.parent_layout);

        topBar_batteryStatus = (ImageView) findViewById(R.id.topBar_batteryStatus);
        topBar_batteryStatus.setImageResource(R.drawable.battery_full);//Battery statusChange

        link_web = (WebView) findViewById(R.id.link_web);
    }

    @Override
    protected void onListenersInit() {
        ((ImageView) findViewById(R.id.topBar_btn_back)).setOnClickListener(this);
    }

    @Override
    protected void onListenersRemove() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topBar_btn_back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onInitDataLoad() {
        try {
            link_web.getSettings().setJavaScriptEnabled(true);
            link_web.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    pgDialog = new ShadeProgressDialog(context);
                    pgDialog.prepareAndShowDialog();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (pgDialog != null)
                        pgDialog.dismissDialog();
                }

                @Override
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                }
            });
            link_web.loadUrl(bundle.getString("key_link"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

