package com.shades.shade.activities;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.shades.shade.R;
import com.shades.shade.adapters.AllDataExpAdapter;
import com.shades.shade.model.AllDataSet;
import com.shades.shade.networkcall.AllDataTask;
import com.shades.shade.widgets.ShadeTextView;

import java.util.ArrayList;

public class AllDataActivity extends ShadeBaseActivity {

    private Context context;
    private View parent_layout;

    private ImageView topBar_batteryStatus;
    private ExpandableListView expListView;
    private AllDataExpAdapter mDataExpAdapter;

    @Override
    protected void onUiLayout() {
        setContentView(R.layout.activity_alldata);
        context = AllDataActivity.this;
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
        ((ShadeTextView) findViewById(R.id.topBar_txt_title)).setText(getString(R.string.allData_title));
        parent_layout = findViewById(R.id.parent_layout);

        expListView = (ExpandableListView) findViewById(R.id.allData_expListView);
        topBar_batteryStatus = (ImageView) findViewById(R.id.topBar_batteryStatus);
        topBar_batteryStatus.setImageResource(R.drawable.battery_full);//Battery statusChange

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
        mDataExpAdapter = new AllDataExpAdapter(context, new ArrayList<AllDataSet>());
        expListView.setAdapter(mDataExpAdapter);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    expListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        loadApiData();
    }

    private void loadApiData() {
        AllDataTask allDataTask = new AllDataTask(context);
        allDataTask.setAllDataTaskListener(new AllDataTask.AllDataTaskListener() {
            @Override
            public void onSuccess(ArrayList<AllDataSet> list) {
                mDataExpAdapter.refreshDataSet((list == null) ? new ArrayList<AllDataSet>() : list);
            }

            @Override
            public void onFail() {

            }
        });
        allDataTask.processTask(null);
    }
}
