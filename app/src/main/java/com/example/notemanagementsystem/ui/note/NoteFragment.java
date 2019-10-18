package com.example.notemanagementsystem.ui.note;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.notemanagementsystem.R;
import com.example.notemanagementsystem.adapter.NoteAdapter;
import com.example.notemanagementsystem.database.DatabaseHandler;
import com.example.notemanagementsystem.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class NoteFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private TextView dateText,extName,txtCategoryName,txtStatusName,txtPriorityName;
    private NoteViewModel noteViewModel;
    private Spinner spnCategory,spnPriority,spnStatus;
    private ListView lvNote;
    private NoteAdapter adapter;
    private ArrayList<Note> data;
    private Note note;


    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        View root = inflater.inflate(R.layout.fragment_note, container, false);

        lvNote = root.findViewById(R.id.lvNote);

        // Event
        lvNote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                note = data.get(i);
                /*return true means: that the event has been handled. No events will be fired after this point.
                return false means: the event has NOT been handled. Any other events to do with this click will still fire.
                */
                return false;
            }
        });
        // Context menu
        registerForContextMenu(lvNote);
        data = new ArrayList<>();
        adapter = new NoteAdapter(getActivity(), R.layout.lv_note, data);
        lvNote.setAdapter(adapter);

        // Create the observer which updates the UI.
        final Observer<ArrayList<Note>> noteObserver = new Observer<ArrayList<Note>>() {
            @Override
            public void onChanged(ArrayList<Note> note) {
                // Reload data
                data.clear();
                data.addAll(note);
                adapter.notifyDataSetChanged();
            }
        };
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        noteViewModel.getNote().observe(this, noteObserver);
        addNote(root);
        return root;

    }

    public void test(){
        registerForContextMenu(lvNote);
        data = new ArrayList<>();
        adapter = new NoteAdapter(getActivity(), R.layout.lv_note, data);
        lvNote.setAdapter(adapter);
    }

    private void addNote(View root) {
        FloatingActionButton btnAdd = root.findViewById(R.id.fab_note);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_note(note);

            }

        });
    }

    private void dialog_note(final Note note) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_note);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Button btncalender = (Button) dialog.findViewById(R.id.buttoncalender);
        Button btnClose = (Button) dialog.findViewById(R.id.buttonclose);
        Button btnAdd = (Button) dialog.findViewById(R.id.buttonadd);
        spnCategory =(Spinner)dialog.findViewById(R.id.spinner_category);
        spnPriority =(Spinner)dialog.findViewById(R.id.spinner_priority);
        spnStatus =(Spinner)dialog.findViewById(R.id.spinner_status);


        dateText = (TextView) dialog.findViewById(R.id.textViewPlaindate);
        extName =(EditText) dialog.findViewById(R.id.editText);
        txtCategoryName =(TextView) dialog.findViewById(R.id.textViewCategory);
        txtPriorityName =(TextView) dialog.findViewById(R.id.textViewPriority);
        txtStatusName =(TextView) dialog.findViewById(R.id.textViewStatus);

        spinner(spnCategory,txtCategoryName,new DatabaseHandler(getContext()).getAllCategoryName(),"Choose Category");
        spinner(spnPriority,txtPriorityName,new DatabaseHandler(getContext()).getAllPriorityName(), "Chopse Priority");
        spinner(spnStatus,txtStatusName,new DatabaseHandler(getContext()).getAllStatusName(),"Choose Status");

        btncalender.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                showDatePicker();

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (note != null) {
            extName.setText(note.getName());
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = extName.getText().toString();
                String category_name = txtCategoryName.getText().toString();
                String priority_name = txtPriorityName.getText().toString();
                String status_name = txtStatusName.getText().toString();
                String plan_date = dateText.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter note name", Toast.LENGTH_LONG).show();
                    extName.requestFocus();
                    return;
                }

                if (category_name.equals("Select category...")) {
                    Toast.makeText(getActivity(), "Please enter category name", Toast.LENGTH_LONG).show();
                    txtCategoryName.requestFocus();
                    return;
                }

                if (priority_name.equals("Select priority...")) {
                    Toast.makeText(getActivity(), "Please enter priority name", Toast.LENGTH_LONG).show();
                    txtPriorityName.requestFocus();
                    return;
                }

                if (status_name.equals("Select status...")) {
                    Toast.makeText(getActivity(), "Please enter status name", Toast.LENGTH_LONG).show();
                    txtStatusName.requestFocus();
                    return;
                }

                if (plan_date.equals("Select plain date")) {
                    Toast.makeText(getActivity(), "Please enter status name", Toast.LENGTH_LONG).show();
                    dateText.requestFocus();
                    return;
                }

                long ret = 0;
                if (note == null) {
                    ret = noteViewModel.insertNote(new Note(name,category_name,priority_name,status_name,plan_date));
                    if (ret > 0) {
                        Toast.makeText(getActivity(), "Add note successfully", Toast.LENGTH_LONG).show();
                    }
                } else { // Update category name
                    ret = noteViewModel.updateNote(note.getName(), new Note(name,category_name,priority_name,status_name,plan_date));
                    //test();
                    if (ret > 0) {
                        Toast.makeText(getActivity(), "Update note successfully", Toast.LENGTH_LONG).show();
                    }
                }
                // Successful
                if (ret > 0) {
                    noteViewModel.getNote().setValue(noteViewModel.getAllNote());
                    dialog.dismiss();
                }
            }
        });
    }

    private void spinner(final Spinner spinner, final TextView textView, ArrayList<String> arrayList, final String caption){
        arrayList.add(0,caption);
        ArrayAdapter<String> adapterCate = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,arrayList);
        adapterCate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCate);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(adapterView.getItemAtPosition(i).equals(caption))
                {

                }
                else
                {
                    textView.setText(spinner.getSelectedItem().toString());
                    Toast.makeText(getActivity(), spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    private void showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),R.style.FullScreenDialog,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setTitle("Select the date");
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date =dayOfMonth +"/"+month+"/"+year;
        dateText.setText(date);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @NonNull ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.edit: // Update category name
                if (note != null) {
                    dialog_note(note);
                }
                break;
            case R.id.delete: // Delete category
                deleteNote();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteNote() {

        if ( note == null) {
            return;
        }

        AlertDialog.Builder al = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        al.setTitle("Confirm Delete...");
        al.setCancelable(false);

        // Setting Dialog Message
        al.setMessage("Are you sure you want delete this " + note.getName() + "?");

        // Setting al "Yes" Btn
        al.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (noteViewModel.deleteNote(note.getName()) > 0) {
                            Toast.makeText(getActivity(), "Delete note successfully", Toast.LENGTH_LONG).show();
                            // Refresh data
                            noteViewModel.getNote().setValue(noteViewModel.getAllNote());
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




