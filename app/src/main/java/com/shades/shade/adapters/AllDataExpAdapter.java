package com.shades.shade.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shades.shade.R;
import com.shades.shade.model.AllDataSet;
import com.shades.shade.widgets.ShadeTextView;

import java.util.ArrayList;


public class AllDataExpAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<AllDataSet> listData;
    private LayoutInflater inflater;

    public AllDataExpAdapter(Context context, ArrayList<AllDataSet> listData) {
        this.context = context;
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
    }

    public void refreshDataSet(ArrayList<AllDataSet> listData) {
        this.listData.clear();
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }


    @Override
    public int getGroupCount() {
        return (listData == null) ? 0 : listData.size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }


    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int grpIndex, boolean isExpanded, View view, ViewGroup viewGroup) {
        GrpViewHolder grpViewHolder;
        if (view == null) {
            grpViewHolder = new GrpViewHolder();
            view = inflater.inflate(R.layout.inflate_alldata_grpview, null);
            grpViewHolder.img_Indicator = (ImageView) view.findViewById(R.id.alldata_grp_indicator);
            grpViewHolder.img_smileyIcon = (ImageView) view.findViewById(R.id.alldata_grp_smileyIcon);
            grpViewHolder.img_note = (ImageView) view.findViewById(R.id.alldata_grp_noteIcon);
            grpViewHolder.img_noData = (ImageView) view.findViewById(R.id.alldata_grp_noDataIcon);
            grpViewHolder.txt_uv = (ShadeTextView) view.findViewById(R.id.alldata_grp_UvUnit);
            grpViewHolder.txt_date = (ShadeTextView) view.findViewById(R.id.alldata_grp_date);
            grpViewHolder.line = view.findViewById(R.id.alldata_upperLine);
            view.setTag(grpViewHolder);
        } else {
            grpViewHolder = (GrpViewHolder) view.getTag();
        }


        AllDataSet dataSet = listData.get(grpIndex);
        grpViewHolder.img_Indicator.setImageResource(isExpanded ? R.drawable.chevron_up : R.drawable.chevron_down);
        grpViewHolder.line.setVisibility((isExpanded && listData.get(grpIndex).getNotes() != null && listData.get(grpIndex).getNotes().size() != 0) ? View.VISIBLE : View.GONE);

        if (dataSet.isHasNote() && dataSet.isHasSmiley()) {
            grpViewHolder.img_smileyIcon.setVisibility(View.VISIBLE);
            grpViewHolder.img_note.setVisibility(View.VISIBLE);
        } else if (dataSet.isHasNote()) {
            grpViewHolder.img_smileyIcon.setVisibility(View.INVISIBLE);
            grpViewHolder.img_note.setVisibility(View.VISIBLE);
        } else if (dataSet.isHasSmiley()) {
            grpViewHolder.img_smileyIcon.setVisibility(View.VISIBLE);
            grpViewHolder.img_note.setVisibility(View.INVISIBLE);
        } else {
            grpViewHolder.img_smileyIcon.setVisibility(View.INVISIBLE);
            grpViewHolder.img_note.setVisibility(View.INVISIBLE);
            grpViewHolder.img_noData.setVisibility(View.INVISIBLE);
        }
        grpViewHolder.txt_uv.setText((TextUtils.isEmpty(dataSet.getUvUnit())) ? "No UV" : dataSet.getUvUnit() + "UV Units");
        grpViewHolder.txt_date.setText(dataSet.getDate());

        return view;
    }

    @Override
    public int getChildrenCount(int i) {
        return (listData.get(i) == null) ? 0 : listData.get(i).getNotes().size();
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public View getChildView(int grpIndex, int childInd, boolean isExpanded, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            childViewHolder = new ChildViewHolder();
            view = inflater.inflate(R.layout.inflate_alldata_childview, null);
            childViewHolder.txtNote = (ShadeTextView) view.findViewById(R.id.alldata_child_details);
            childViewHolder.line = view.findViewById(R.id.alldata_lowerLine);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        childViewHolder.line.setVisibility(isExpanded ? View.VISIBLE : View.INVISIBLE);
        childViewHolder.txtNote.setText(listData.get(grpIndex).getNotes().get(childInd));

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }


    private class GrpViewHolder {
        private ImageView img_smileyIcon;
        private ImageView img_note;
        private ImageView img_noData;
        private TextView txt_uv;
        private TextView txt_date;
        private ImageView img_Indicator;
        private View line;
    }

    private class ChildViewHolder {
        private View line;
        private TextView txtNote;
    }
}
