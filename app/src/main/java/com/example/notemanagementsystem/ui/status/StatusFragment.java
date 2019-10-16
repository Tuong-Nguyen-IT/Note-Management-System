package com.example.notemanagementsystem.ui.status;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.notemanagementsystem.R;
import com.example.notemanagementsystem.adapter.StatusAdapter;
import com.example.notemanagementsystem.model.Status;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StatusFragment extends Fragment {
    private StatusViewModel statusViewModel;

    private ListView lvStatus;
    private StatusAdapter adapter;
    private ArrayList<Status> data;
    private Status status;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statusViewModel =
                ViewModelProviders.of(this).get(StatusViewModel.class);

        View root = inflater.inflate(R.layout.fragment_status, container, false);

        lvStatus = root.findViewById(R.id.listStatus);

        // Event
        lvStatus.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                status = data.get(i);
                /*return true means: that the event has been handled. No events will be fired after this point.
                return false means: the event has NOT been handled. Any other events to do with this click will still fire.
                */
                return false;
            }
        });

        // Context menu
        registerForContextMenu(lvStatus);

        data = new ArrayList<>();
        adapter = new StatusAdapter(getActivity(), R.layout.lv_status, data);
        lvStatus.setAdapter(adapter);

        // Create the observer which updates the UI.
        final Observer<ArrayList<Status>> statusObserver = new Observer<ArrayList<Status>>() {
            @Override
            public void onChanged(ArrayList<Status> categories) {
                // Reload data
                data.clear();
                data.addAll(categories);
                adapter.notifyDataSetChanged();
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        statusViewModel.getStatus().observe(this, statusObserver);

        addStatus(root);

        return root;
    }

    private void addStatus(View root) {
        FloatingActionButton btnAdd = root.findViewById(R.id.floatingActionButton2);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(null);
            }
        });
    }

    private void showDialog(final Status status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);

        final View dialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_status
                , viewGroup, false);
        builder.setView(dialog);

        final EditText etName = dialog.findViewById(R.id.statusName);

        builder.setPositiveButton("Save", null);
        builder.setNegativeButton("Cancel", null);
        builder.setCancelable(false);

        // Edit category name
        if (status != null) {
            etName.setText(status.getName());
        }

        // Handling event for save button (BUTTON_POSITIVE)
        final AlertDialog mAlertDialog = builder.create();
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
                        // Add new category
                        if (status == null) {
                            ret = statusViewModel.addStatus(new Status(name));
                            if (ret > 0) {
                                Toast.makeText(getActivity(), "Add status successfully", Toast.LENGTH_LONG).show();
                            }
                        } else { // Update category name
                            ret = statusViewModel.updateStatus(status.getName(), new Status(name));
                            if (ret > 0) {
                                Toast.makeText(getActivity(), "Update status successfully", Toast.LENGTH_LONG).show();
                            }
                        }

                        // Successful
                        if (ret > 0) {
                            statusViewModel.getStatus().setValue(statusViewModel.getAllStatus());
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        mAlertDialog.show();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.edit: // Update category name
                if (status != null) {
                    showDialog(status);
                }
                break;
            case R.id.delete: // Delete category
                deleteStatus();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteStatus() {

        if (status == null) {
            return;
        }

        AlertDialog.Builder al = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        al.setTitle("Confirm Delete...");
        al.setCancelable(false);

        // Setting Dialog Message
        al.setMessage("Are you sure you want delete this " + status.getName() + "?");

        // Setting al "Yes" Btn
        al.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (statusViewModel.deleteStatus(status.getName()) > 0) {
                            Toast.makeText(getActivity(), "Delete category successfully", Toast.LENGTH_LONG).show();
                            // Refresh data
                            statusViewModel.getStatus().setValue(statusViewModel.getAllStatus());
                        }
                    }
                });
        // Setting Negative "NO" Btn
        al.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        // Showing Alert Dialog
        al.show();
    }

}
