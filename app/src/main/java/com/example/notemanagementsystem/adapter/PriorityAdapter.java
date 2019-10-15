package com.example.notemanagementsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.notemanagementsystem.R;
import com.example.notemanagementsystem.model.Priority;

import java.util.ArrayList;

public class PriorityAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    ArrayList<Priority> data;

    public PriorityAdapter(Context context, int layout, ArrayList<Priority> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PriorityAdapter.ViewHolder vh;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            vh = new PriorityAdapter.ViewHolder();
            vh.tvName = view.findViewById(R.id.tvName);
            vh.tvCreatedDate = view.findViewById(R.id.tvCreatedDate);
            view.setTag(vh);
        } else {
            vh = (PriorityAdapter.ViewHolder) view.getTag();
        }
        if (data.size() > 0) {
            Priority priority = data.get(i);
            vh.tvName.setText("Name: " + priority.getName());
            vh.tvCreatedDate.setText("Created Date: " + priority.getCreatedDate());
        }

        return view;
    }

    private class ViewHolder {
        TextView tvName;
        TextView tvCreatedDate;
    }
}
