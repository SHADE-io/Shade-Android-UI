package com.shades.shade.activities;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.shades.shade.R;
import com.shades.shade.interfaces.EditTextWatcherListener;
import com.shades.shade.utility.AppConstant;
import com.shades.shade.widgets.MyTextWatcher;
import com.shades.shade.widgets.ShadeEditText;
import com.shades.shade.widgets.ShadeTextView;

public class ChangePasswordActivity extends ShadeBaseActivity implements EditTextWatcherListener {

    private Context context;

    private ShadeEditText edt_email;
    private ShadeEditText edt_oldPassword;
    private ShadeEditText edt_newPassword;

    private ShadeTextView txtErrorEmail;
    private ShadeTextView txtErrorPsw;
    private ShadeTextView txtErrorNewPasw;

    private View parent_layout;
    private ImageView topBar_batteryStatus;
    private ShadeTextView btn_save;
    private boolean isShowingOldPsw = false;
    private boolean isShowingNewPsw = false;

    @Override
    protected void onUiLayout() {
        setContentView(R.layout.activity_resetpassword);
        context = ChangePasswordActivity.this;
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

        edt_email = (ShadeEditText) findViewById(R.id.resetPws_edt_email);
        edt_oldPassword = (ShadeEditText) findViewById(R.id.resetPws_edt_password);
        edt_newPassword = (ShadeEditText) findViewById(R.id.resetPws_edt_newPassword);

        txtErrorEmail = (ShadeTextView) findViewById(R.id.resetPws_error_email_message);
        txtErrorPsw = (ShadeTextView) findViewById(R.id.resetPws_error_password_message);
        txtErrorNewPasw = (ShadeTextView) findViewById(R.id.resetPws_error_newPassword_message);
        topBar_batteryStatus = (ImageView) findViewById(R.id.topBar_batteryStatus);

        edt_oldPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0, DRAWABLE_TOP = 1, DRAWABLE_RIGHT = 2, DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int drwWidth = edt_oldPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                    if (event.getRawX() >= (edt_oldPassword.getRight() - drwWidth)) {
                        showPassword();
                        return true;
                    }
                }
                return false;
            }
        });

        edt_newPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0, DRAWABLE_TOP = 1, DRAWABLE_RIGHT = 2, DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int drwWidth = edt_newPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                    if (event.getRawX() >= (edt_newPassword.getRight() - drwWidth)) {
                        showNewPassword();
                        return true;
                    }
                }
                return false;
            }
        });

        edt_newPassword.addTextChangedListener(new MyTextWatcher(this,edt_newPassword));
    }

    @Override
    protected void onListenersInit() {
        ((ImageView) findViewById(R.id.topBar_btn_back)).setOnClickListener(this);
        ((ShadeTextView) findViewById(R.id.resetPws_btn_cancel)).setOnClickListener(this);
        btn_save = (ShadeTextView) findViewById(R.id.resetPws_btn_save);
        btn_save.setOnClickListener(this);

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

            case R.id.resetPws_btn_cancel:
                onBackPressed();
                break;

            case R.id.resetPws_btn_save:
                if (!btn_save.isSelected())
                    return;
                onSubmitButtonEvent();
                break;
        }
    }

    @Override
    public void onTextChanges(View v) {
        switch (v.getId()) {
            case R.id.resetPws_edt_newPassword:
                btn_save.setSelected(true);
                break;
        }

    }

    @Override
    public void onTextChangesToZero(View v) {
        switch (v.getId()) {
            case R.id.resetPws_edt_newPassword:
                btn_save.setSelected(false);
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
        String oldPassword = edt_oldPassword.getText().toString();
        String newPassword = edt_newPassword.getText().toString();


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

        if (TextUtils.isEmpty(oldPassword)) {
            inValidEditTextInput(txtErrorPsw, "Enter your current password");
            return;
        } else {
            txtErrorPsw.setVisibility(View.INVISIBLE);
        }


        if (TextUtils.isEmpty(newPassword)) {
            inValidEditTextInput(txtErrorNewPasw, "Enter your new password");
            return;
        } else {
            txtErrorNewPasw.setVisibility(View.INVISIBLE);
        }

        apiCallChangePassword();

    }

    private void inValidEditTextInput(ShadeTextView edtText, String message) {
        requestFocus(edtText);
        edtText.setVisibility(View.VISIBLE);
        edtText.setText(message);
    }

    /**
     * Password visibility
     */
    private void showPassword() {
        if (edt_oldPassword.getText().length() > 0) {
            edt_oldPassword.setInputType(isShowingOldPsw ? (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD) : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            edt_oldPassword.setSelection(edt_oldPassword.length());
            isShowingOldPsw = !isShowingOldPsw;
        }
    }

    private void showNewPassword() {
        if (edt_newPassword.getText().length() > 0) {
            edt_newPassword.setInputType(isShowingNewPsw ? (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD) : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            edt_newPassword.setSelection(edt_newPassword.length());
            isShowingNewPsw = !isShowingNewPsw;
        }
    }

    private void apiCallChangePassword() {


        AppConstant.showSnakeBar(parent_layout, "Your password was successfully changed.", true, "OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
