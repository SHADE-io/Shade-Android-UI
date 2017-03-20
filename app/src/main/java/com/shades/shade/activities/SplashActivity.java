package com.shades.shade.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.shades.shade.R;
import com.shades.shade.widgets.ShadeTextView;

public class SplashActivity extends ShadeBaseActivity {

    private Context context;

    @Override
    protected void onUiLayout() {
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;
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
    }

    @Override
    protected void onListenersInit() {
        ((ShadeTextView) findViewById(R.id.splash_btn_signIn)).setOnClickListener(this);
        ((ShadeTextView) findViewById(R.id.splash_btn_signUp)).setOnClickListener(this);
    }

    @Override
    protected void onListenersRemove() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.splash_btn_signIn:
                goToSignIn();
                break;
            case R.id.splash_btn_signUp:
                goToSignUp();
                break;
        }

    }

    /**
     * Go to SignIn Screen
     */
    private void goToSignIn() {
        startActivity(new Intent(context, SignInActivity.class));
        overridePendingTransition(0, 0);
    }


    private void goToSignUp() {
        startActivity(new Intent(context, SignUpActivity.class));
        overridePendingTransition(0, 0);
    }
}
