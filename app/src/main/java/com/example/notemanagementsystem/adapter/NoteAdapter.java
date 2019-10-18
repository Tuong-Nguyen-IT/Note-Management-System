package com.example.notemanagementsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.notemanagementsystem.R;
import com.example.notemanagementsystem.model.Note;

import java.util.ArrayList;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    ArrayList<Note> data;

    public NoteAdapter(Context context, int layout, ArrayList<Note> data) {
        this.context = context;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            vh = new ViewHolder();
            vh.tvName = convertView.findViewById(R.id.tvName);
            vh.tvCategory = convertView.findViewById(R.id.tvCategory);
            vh.tvPriority = convertView.findViewById(R.id.tvPriority);
            vh.tvStatus = convertView.findViewById(R.id.tvStatus);
            vh.tvPlandate = convertView.findViewById(R.id.tvPlaindate);
            vh.tvCreatedDate = convertView.findViewById(R.id.tvCreateddate);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        // Set data
        if (data.size() > 0) {
            Note note = data.get(position);
            vh.tvName.setText("Name: " + note.getName());
            vh.tvCategory.setText("Category: " + note.getCategory_name());
            vh.tvPriority.setText("Priority: " + note.getPriority_name());
            vh.tvStatus.setText("Status: " + note.getStatus_name());
            vh.tvPlandate.setText("Plan Date: " + note.getPlan_date());
            vh.tvCreatedDate.setText("Created Date: " + note.getCreatedDate());
        }

        return convertView;
    }

    private class ViewHolder {
        TextView tvName;
        TextView tvCategory;
        TextView tvPriority;
        TextView tvStatus;
        TextView tvPlandate;
        TextView tvCreatedDate;
    }
}
