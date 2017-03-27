package com.shades.shade.activities;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.shades.shade.R;
import com.shades.shade.interfaces.EditTextWatcherListener;
import com.shades.shade.utility.RegisterActivities;
import com.shades.shade.widgets.MyTextWatcher;
import com.shades.shade.widgets.ShadeEditText;
import com.shades.shade.widgets.ShadeTextView;

public class SignUpActivity extends ShadeBaseActivity implements EditTextWatcherListener {

    private Context context;
    private boolean isShowing = false;
    private ShadeEditText edt_email, edt_password;
    private ShadeTextView txtErrorEmail, txtErrorPassword;
    private ShadeTextView btnSignUp;

    @Override
    protected void onUiLayout() {
        RegisterActivities.registerActivity(this);
        setContentView(R.layout.activity_signup);
        context = SignUpActivity.this;
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

        ((ShadeTextView) findViewById(R.id.topBar_txt_title)).setText(getString(R.string.splash_btn_signUp));

        edt_email = (ShadeEditText) findViewById(R.id.signUp_edt_email);
        edt_password = (ShadeEditText) findViewById(R.id.signUp_edt_password);
        txtErrorEmail = (ShadeTextView) findViewById(R.id.signUp_error_email_message);
        txtErrorPassword = (ShadeTextView) findViewById(R.id.signUp_error_password_message);

        btnSignUp = (ShadeTextView) findViewById(R.id.signUp_btn_signUp);
        ((ImageView) findViewById(R.id.signUp_img_terms)).setOnClickListener(this);

        edt_email.addTextChangedListener(new MyTextWatcher(this,edt_email));
        edt_password.addTextChangedListener(new MyTextWatcher(this,edt_password));

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
    }

    @Override
    protected void onListenersInit() {
        ((ImageView) findViewById(R.id.topBar_btn_back)).setOnClickListener(this);
        ((ShadeTextView) findViewById(R.id.signUp_btn_signUp)).setOnClickListener(this);
        ((ShadeTextView) findViewById(R.id.signUp_btn_emailSup)).setOnClickListener(this);
        ((ShadeTextView) findViewById(R.id.signUp_btn_callSup)).setOnClickListener(this);

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

            case R.id.signUp_img_terms:
                view.setSelected(view.isSelected() ? false : true);
                btnSignUp.setSelected(view.isSelected() ? true : false);
                break;

            case R.id.signUp_btn_signUp:
                onSinInButtonEvent();
                break;

            case R.id.signUp_btn_emailSup:
                break;

            case R.id.signUp_btn_callSup:
                break;
        }
    }


    @Override
    public void onTextChanges(View v) {
        switch (v.getId()) {
            case R.id.signUp_edt_email:
                txtErrorEmail.setText("Use same email used to purchase Shade.");
                txtErrorEmail.setSelected(false);
                txtErrorEmail.setVisibility(View.VISIBLE);
                break;

            case R.id.signUp_edt_password:
                txtErrorPassword.setText("Minimum 8 characters, with 1 non-alphabet character.");
                txtErrorPassword.setSelected(false);
                txtErrorPassword.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public void onTextChangesToZero(View v) {
        switch (v.getId()) {
            case R.id.signUp_edt_email:
                txtErrorEmail.setSelected(true);
                txtErrorEmail.setVisibility(View.INVISIBLE);
                break;

            case R.id.signUp_edt_password:
                txtErrorPassword.setSelected(true);
                txtErrorPassword.setVisibility(View.INVISIBLE);
                break;
        }
    }


    private void showPassword() {
        if (edt_password.getText().length() > 0) {
            edt_password.setInputType(isShowing ? (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD) : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            edt_password.setSelection(edt_password.length());
            isShowing = !isShowing;
        }
    }

    private static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private void onSinInButtonEvent() {
        if (!btnSignUp.isSelected())
            return;

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


    }


    private void inValidEditTextInput(ShadeTextView edtText, String message) {
        requestFocus(edtText);
        edtText.setVisibility(View.VISIBLE);
        edtText.setText(message);
    }

}
