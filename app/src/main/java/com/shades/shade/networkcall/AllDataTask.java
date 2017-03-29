package com.shades.shade.networkcall;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.shades.shade.dialogs.ShadeProgressDialog;
import com.shades.shade.model.AllDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AllDataTask {
    private Context context;
    private AllDataTaskListener allDataTaskListener;
    private Map<String, String> postBody;

    public AllDataTask(Context context) {
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
                createData();
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

            if (allDataTaskListener != null) {
                if (isAPICallSuccess) {
                    allDataTaskListener.onSuccess(list);
                } else {
                    allDataTaskListener.onFail();
                }
            }

        }
    }

    public AllDataTaskListener getAllDataTaskListener() {
        return allDataTaskListener;
    }

    public void setAllDataTaskListener(AllDataTaskListener allDataTaskListener) {
        this.allDataTaskListener = allDataTaskListener;
    }

    public interface AllDataTaskListener {
        void onSuccess(ArrayList<AllDataSet> list);

        void onFail();
    }


    //Test Data prepare
    private ArrayList<AllDataSet> list;
    private boolean hasNote = true;
    private boolean hasSmiley = false;

    private void createData() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AllDataSet dataSet = new AllDataSet();
            dataSet.setDate("March " + (10 + i) + ", 2017");
            dataSet.setUvUnit("" + (10.0f + i));
            if (i % 2 == 0) {
                dataSet.setHasNote(false);
                dataSet.setHasSmiley(false);
                dataSet.setNotes(new ArrayList<String>());
            } else {
                dataSet.setHasNote(hasNote);
                dataSet.setHasSmiley(hasSmiley);

                if (hasNote) {
                    ArrayList<String> note = new ArrayList<>();
                    note.add("This is a longer note about things that are bothering me like my terrible symptoms. I have had joint pain in my wrists and I need to take meds, wear a wrist guard. Designing on the trackpad doesn’t help.");
                    dataSet.setNotes(note);
                } else {
                    dataSet.setNotes(new ArrayList<String>());
                }
                hasNote = !hasNote;
                hasSmiley = !hasSmiley;
            }
            list.add(dataSet);
        }

    }
}
