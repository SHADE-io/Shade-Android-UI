package com.shades.shade.activities;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.shades.shade.R;
import com.shades.shade.utility.AppConstant;
import com.shades.shade.widgets.ShadeEditText;
import com.shades.shade.widgets.ShadeTextView;

public class ForgotPasswordActivity extends ShadeBaseActivity {

    private Context context;

    private ShadeEditText edt_email;
    private ShadeTextView txtErrorEmail;
    private View parent_layout;


    @Override
    protected void onUiLayout() {
        setContentView(R.layout.activity_forgotpasw);
        context = ForgotPasswordActivity.this;
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

        ((ShadeTextView) findViewById(R.id.topBar_txt_title)).setText(getString(R.string.forgotPsw_title));
        parent_layout = findViewById(R.id.parent_layout);

        edt_email = (ShadeEditText) findViewById(R.id.forgotPws_edt_email);
        txtErrorEmail = (ShadeTextView) findViewById(R.id.forgotPws_error_email_message);
    }

    @Override
    protected void onListenersInit() {
        ((ImageView) findViewById(R.id.topBar_btn_back)).setOnClickListener(this);
        ((ShadeTextView) findViewById(R.id.forgotPws_btn_submit)).setOnClickListener(this);

    }

    @Override
    protected void onListenersRemove() {

    }

    @Override
    protected void onInitDataLoad() {
        //TODO After UI Load complete, if you want to do something, do it here
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topBar_btn_back:
                onBackPressed();
                break;

            case R.id.forgotPws_btn_submit:
                onSubmitButtonEvent();
                break;
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


    private void onSubmitButtonEvent() {
        String strEmail = edt_email.getText().toString();

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


        AppConstant.showSnakeBar(parent_layout, "Your are send wrong email address", true, "OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO YOUR WORK
            }
        });

    }

    private void inValidEditTextInput(ShadeTextView edtText, String message) {
        requestFocus(edtText);
        edtText.setVisibility(View.VISIBLE);
        edtText.setText(message);
    }

}
