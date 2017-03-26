package com.shades.shade.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.shades.shade.R;
import com.shades.shade.utility.Constant;
import com.shades.shade.utility.RegisterActivities;
import com.shades.shade.widgets.ShadeEditText;
import com.shades.shade.widgets.ShadeTextView;

import java.util.List;

public class SignInActivity extends ShadeBaseActivity {

    private Context context;
    private boolean isShowing = false;
    private ShadeEditText edt_email, edt_password;
    private ShadeTextView txtErrorEmail, txtErrorPassword;

    @Override
    protected void onUiLayout() {
        RegisterActivities.registerActivity(this);
        setContentView(R.layout.activity_signin);
        context = SignInActivity.this;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        RegisterActivities.removeActivity();
        super.onDestroy();
    }

    @Override
    protected void onUiComponentInit() {

        ((ShadeTextView) findViewById(R.id.topBar_txt_title)).setText(getString(R.string.splash_btn_signIn));

        edt_email = (ShadeEditText) findViewById(R.id.signIn_edt_email);
        edt_password = (ShadeEditText) findViewById(R.id.signIn_edt_password);
        txtErrorEmail = (ShadeTextView) findViewById(R.id.signIn_error_email_message);
        txtErrorPassword = (ShadeTextView) findViewById(R.id.signIn_error_password_message);

        edt_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0, DRAWABLE_TOP = 1, DRAWABLE_RIGHT = 2, DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int drwWidth = edt_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                    if (event.getRawX() >= (edt_password.getRight() - drwWidth)) {
                        showPassword();
                        return true;
                    }
                }
                return false;
            }
        });

        //Test
        edt_email.setText("dibkar.ece@gmail.com");
        edt_password.setText("123456");
    }

    @Override
    protected void onListenersInit() {
        ((ImageView) findViewById(R.id.topBar_btn_back)).setOnClickListener(this);
        ((ShadeTextView) findViewById(R.id.signIn_btn_signIn)).setOnClickListener(this);
        ((ShadeTextView) findViewById(R.id.signIn_btn_forgotPassword)).setOnClickListener(this);
        ((ShadeTextView) findViewById(R.id.signIn_btn_emailSup)).setOnClickListener(this);
        ((ShadeTextView) findViewById(R.id.signIn_btn_callSup)).setOnClickListener(this);

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

            case R.id.signIn_btn_signIn:
                onSinInButtonEvent();
                break;

            case R.id.signIn_btn_forgotPassword:
                goToResetPassword();
                break;

            case R.id.signIn_btn_emailSup:
                emailUs();
                break;

            case R.id.signIn_btn_callSup:
                callUs();
                break;
        }
    }

    private void goToResetPassword() {
        startActivity(new Intent(context, ForgotPasswordActivity.class));
        overridePendingTransition(0, 0);
    }

    private static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     * Password visibility
     */
    private void showPassword() {
        if (edt_password.getText().length() > 0) {
            edt_password.setInputType(isShowing ? (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD) : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            edt_password.setSelection(edt_password.length());
            isShowing = !isShowing;
        }
    }

    private void onSinInButtonEvent() {
        String strEmail = edt_email.getText().toString();
        String strPassword = edt_password.getText().toString();

        if (TextUtils.isEmpty(strEmail)) {
            inValidEditTextInput(txtErrorEmail, "Enter your email address");
            return;
        } else {
            txtErrorEmail.setVisibility(View.INVISIBLE);
        }

        if (!isValidEmail(strEmail)) {
            inValidEditTextInput(txtErrorEmail, "Enter valid email address");
            return;
        } else {
            txtErrorEmail.setVisibility(View.INVISIBLE);
        }

        if (TextUtils.isEmpty(strPassword)) {
            inValidEditTextInput(txtErrorPassword, "Enter your password");
            return;
        } else {
            txtErrorPassword.setVisibility(View.INVISIBLE);
        }
        goToSignIn();
    }


    private void inValidEditTextInput(ShadeTextView edtText, String message) {
        requestFocus(edtText);
        edtText.setVisibility(View.VISIBLE);
        edtText.setText(message);
    }

    private void goToSignIn() {
        startActivity(new Intent(context, HomeActivity.class));
        overridePendingTransition(0, 0);
        RegisterActivities.removeAllActivities();
    }

    private void emailUs() {
        try {
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{Constant.EMAILUS_TO});
            intent.putExtra(Intent.EXTRA_SUBJECT, Constant.EMAILUS_SUB);
            intent.putExtra(Intent.EXTRA_TEXT, Constant.EMAILUS_MESSAGE);

            final PackageManager pm = getPackageManager();
            final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
            ResolveInfo best = null;
            for (final ResolveInfo info : matches) {
                if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                    best = info;
                if (best != null)
                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
            }
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callUs() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + Constant.CALLUS_NO));
        startActivity(intent);
    }

}