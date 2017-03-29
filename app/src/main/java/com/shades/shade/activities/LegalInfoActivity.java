package com.shades.shade.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shades.shade.R;
import com.shades.shade.adapters.AllDataExpAdapter;
import com.shades.shade.model.AllDataSet;
import com.shades.shade.networkcall.AllDataTask;
import com.shades.shade.widgets.ShadeTextView;

import java.util.ArrayList;

public class LegalInfoActivity extends ShadeBaseActivity {

    private Context context;
    private View parent_layout;

    private ImageView topBar_batteryStatus;
    private ListView listView;
    private String[] infoList;
    private String[] links;

    @Override
    protected void onUiLayout() {
        setContentView(R.layout.activity_legalinfo);
        context = LegalInfoActivity.this;
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
        ((ShadeTextView) findViewById(R.id.topBar_txt_title)).setText("Legal Information");
        parent_layout = findViewById(R.id.parent_layout);

        listView = (ListView) findViewById(R.id.info_ListView);
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
        infoList = getResources().getStringArray(R.array.legalInfo);
        links = getResources().getStringArray(R.array.legalInfoLink);

        listView.setAdapter(new InfoAdapter(context));
    }

    private class InfoAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;

        public InfoAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return (infoList == null) ? 0 : infoList.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = inflater.inflate(R.layout.inflate_legalinfo, null);
                viewHolder.infoLegal = (ShadeTextView) view.findViewById(R.id.legalInfo_title);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.infoLegal.setText(infoList[position]);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(links[position]));
                    startActivity(i);
                }
            });
            return view;
        }

        private class ViewHolder {
            private TextView infoLegal;
        }
    }

}
