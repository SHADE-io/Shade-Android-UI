package com.shades.shade.networkcall;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.shades.shade.dialogs.ShadeProgressDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginTask {
    private Context context;
    private LoginTaskListener loginTaskListener;
    private Map<String, String> postBody;

    public LoginTask(Context context) {
        this.context = context;
    }

    public void processTask(Map<String, String> postDate) {
        this.postBody = postDate;
        Task task = new Task();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            task.execute();
        }
    }

    private class Task extends AsyncTask<Void, Void, Void> {

        private ShadeProgressDialog pgDialog;
        private boolean isAPICallSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgDialog = new ShadeProgressDialog(context);
            pgDialog.prepareAndShowDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //TODO YOUR API CALL HERE
            try {
                //THIS FOR TEST ONLY
                //TODO WITH *** postBody
                Thread.sleep(3000);
                isAPICallSuccess = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (pgDialog != null)
                pgDialog.dismissDialog();

            if (loginTaskListener != null) {
                if (isAPICallSuccess) {
                    loginTaskListener.onSuccess();
                } else {
                    loginTaskListener.onFail();
                }
            }

        }
    }


    public Map<String, String> postData(String email, String password) {
        Map<String, String> postData = new HashMap<>();
        postData.put("Email", email);
        postData.put("Password", password);
        return postData;
    }

    public LoginTaskListener getLoginTaskListener() {
        return loginTaskListener;
    }

    public void setLoginTaskListener(LoginTaskListener loginTaskListener) {
        this.loginTaskListener = loginTaskListener;
    }

    public interface LoginTaskListener {
        void onSuccess();

        void onFail();
    }
}
