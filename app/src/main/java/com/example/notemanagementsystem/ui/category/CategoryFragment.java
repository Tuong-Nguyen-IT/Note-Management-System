package com.example.notemanagementsystem.ui.category;

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
import com.example.notemanagementsystem.adapter.CategoryAdapter;
import com.example.notemanagementsystem.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    private CategoryViewModel mViewModel;
    private FloatingActionButton fab;
    private ListView lvCategory;
    private CategoryAdapter adapter;
    private ArrayList<Category> data;
    private Category category;
    private CategoryViewModel categoryViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        lvCategory = root.findViewById(R.id.lvcate);
        lvCategory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                category = data.get(i);
                /*return true means: that the event has been handled. No events will be fired after this point.
                return false means: the event has NOT been handled. Any other events to do with this click will still fire.
                */
                return false;
            }
        });
        registerForContextMenu(lvCategory);

        data = new ArrayList<>();
        adapter = new CategoryAdapter(getActivity(), R.layout.lv_category, data);
        lvCategory.setAdapter(adapter);

        // Create the observer which updates the UI.
        final Observer<ArrayList<Category>> categoryObserver = new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> categories) {
                // Reload data
                data.clear();
                data.addAll(categories);
                adapter.notifyDataSetChanged();
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        categoryViewModel.getCategories().observe(this, categoryObserver);

        addCategory(root);
        return root;
    }
    private void addCategory(View root) {
        FloatingActionButton btnAdd = root.findViewById(R.id.fab_cate);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(null);
            }
        });
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void showDialog(final Category category) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);

        final View dialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_category
                , viewGroup, false);
        builder.setView(dialog);

        final EditText etName = dialog.findViewById(R.id.etname);

        builder.setPositiveButton("Save", null);
        builder.setNegativeButton("Cancel", null);
        builder.setCancelable(false);

        // Edit category name
        if (category != null) {
            etName.setText(category.getName());
        }

        // Handling event for save button (BUTTON_POSITIVE)
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
                        // Add new category
                        if (category == null) {
                            ret = categoryViewModel.addCategory(new Category(name));
                            if (ret > 0) {
                                Toast.makeText(getActivity(), "Add catetory successfully", Toast.LENGTH_LONG).show();
                            }
                        } else { // Update category name
                            ret = categoryViewModel.updateCategory(category.getName(), new Category(name));
                            if (ret > 0) {
                                Toast.makeText(getActivity(), "Update catetory successfully", Toast.LENGTH_LONG).show();
                            }
                        }

                        // Successful
                        if (ret > 0) {
                            categoryViewModel.getCategories().setValue(categoryViewModel.getAllCategories());
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        mAlertDialog.show();
    }

}
