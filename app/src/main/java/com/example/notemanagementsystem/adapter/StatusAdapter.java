package com.example.notemanagementsystem.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.notemanagementsystem.R;
import com.example.notemanagementsystem.model.Category;
import com.example.notemanagementsystem.model.Status;

import java.util.ArrayList;

public class StatusAdapter extends BaseAdapter {
    ArrayList<Status> data;
    Activity activity;
    int layout;

    public StatusAdapter(Activity activity, int layout, ArrayList<Status> data) {
        this.activity = activity;
        this.layout = layout;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewLvStatus vls = new viewLvStatus();

        LayoutInflater inflater = (LayoutInflater) activity.getLayoutInflater();

        convertView = inflater.inflate(R.layout.lv_status,null);

        vls.name = (TextView)convertView.findViewById(R.id.tv_status_name);
        vls.date = (TextView)convertView.findViewById(R.id.tv_status_date);

        Status note = data.get(position);

        vls.name.setText("Name: "+note.getName());
        vls.date.setText("Date: "+note.getDate());
        return convertView;
    }
    class viewLvStatus{
        TextView name,date;
    }
}
