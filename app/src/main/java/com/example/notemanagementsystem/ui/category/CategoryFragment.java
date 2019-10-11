package com.example.notemanagementsystem.ui.category;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.DialogInterface;
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

public class CategoryFragment extends Fragment {

    private CategoryViewModel mViewModel;
    private FloatingActionButton fab;
    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        // TODO: Use the ViewModel
     }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String[] abc = new String[10];
        for (int i = 0; i < 10; i++) {
            abc[i] = "00";
        }
        ListView lv = (ListView) getView().findViewById(R.id.lvcate);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, abc);
        lv.setAdapter(adapter);
        fab = (FloatingActionButton) getView().findViewById(R.id.fab_cate);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_category);
                Button btn_cancel = dialog.findViewById(R.id.btn_cate_cancel);
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
