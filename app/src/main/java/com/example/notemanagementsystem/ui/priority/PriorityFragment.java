package com.example.notemanagementsystem.ui.priority;

import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.notemanagementsystem.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PriorityFragment extends Fragment {

    private PriorityViewModel mViewModel;
    private FloatingActionButton fab;

    public static PriorityFragment newInstance() {
        return new PriorityFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_priority, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PriorityViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String[] abc = new String[10];
        for (int i = 0; i < 10; i++) {
            abc[i] = "00";
        }
        ListView lv = (ListView) getView().findViewById(R.id.lvprio);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, abc);
        lv.setAdapter(adapter);
        fab = (FloatingActionButton) getView().findViewById(R.id.fab_prio);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_priority);
                dialog.setCancelable(false);
                Button btn_cancel = dialog.findViewById(R.id.btn_prio_cancel);
                Button btn_add;
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}
