package com.example.notemanagementsystem.ui.priority;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.notemanagementsystem.R;
import com.example.notemanagementsystem.adapter.PriorityAdapter;
import com.example.notemanagementsystem.model.Priority;
import com.example.notemanagementsystem.ui.priority.PriorityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PriorityFragment extends Fragment {

    private PriorityViewModel mViewModel;
    private FloatingActionButton fab;
    private ListView lvPriority;
    private PriorityAdapter adapter;
    private ArrayList<Priority> data;
    private Priority priority;
    private PriorityViewModel priorityViewModel;

    public static PriorityFragment newInstance() {
        return new PriorityFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        priorityViewModel = ViewModelProviders.of(this).get(PriorityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_priority, container, false);
        lvPriority = root.findViewById(R.id.lvPriority);
        lvPriority.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                priority = data.get(i);
                return false;
            }
        });
        registerForContextMenu(lvPriority);

        data = new ArrayList<>();
        adapter = new PriorityAdapter(getActivity(), R.layout.lv_category, data);
        lvPriority.setAdapter(adapter);

        final Observer<ArrayList<Priority>> categoryObserver = new Observer<ArrayList<Priority>>() {
            @Override
            public void onChanged(ArrayList<Priority> categories) {
                data.clear();
                data.addAll(categories);
                adapter.notifyDataSetChanged();
            }
        };

        priorityViewModel.getPriorities().observe(this, categoryObserver);

        addPriority(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PriorityViewModel.class);
        // TODO: Use the ViewModel
    }

    private void addPriority(View root) {
        FloatingActionButton btnAdd = root.findViewById(R.id.fab_prio);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(null);
            }
        });
    }
    private void showDialog(final Priority priority) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);

        final View dialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_priority
                , viewGroup, false);
        builder.setView(dialog);

        final EditText etName = dialog.findViewById(R.id.etName);

        builder.setPositiveButton("Save", null);
        builder.setNegativeButton("Cancel", null);
        builder.setCancelable(false);

        if (priority != null) {
            etName.setText(priority.getName());
        }

        final android.app.AlertDialog mAlertDialog = builder.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = etName.getText().toString();
                        if (name.isEmpty()) {
                            Toast.makeText(getActivity(), "Please enter category name", Toast.LENGTH_LONG).show();
                            etName.requestFocus();
                            return;
                        }

                        long ret = 0;
                        if (priority == null) {
                            ret = priorityViewModel.addPriority(new Priority(name));
                            if (ret > 0) {
                                Toast.makeText(getActivity(), "Add catetory successfully", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            ret = priorityViewModel.updatePriority(priority.getName(), new Priority(name));
                            if (ret > 0) {
                                Toast.makeText(getActivity(), "Update catetory successfully", Toast.LENGTH_LONG).show();
                            }
                        }
                        if (ret > 0) {
                            priorityViewModel.getPriorities().setValue(priorityViewModel.getAllPriorities());
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        mAlertDialog.show();
    }
}
