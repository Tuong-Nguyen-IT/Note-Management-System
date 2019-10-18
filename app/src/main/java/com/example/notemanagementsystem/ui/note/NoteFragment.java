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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
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
    private NoteViewModel mViewModel;
    private FloatingActionButton fab;
    private Button btncalender, btnClose;
    private TextView dateText;
    private NoteViewModel noteViewModel;

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
        //test();
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
                //Toast.makeText(getActivity(), DatabaseHandler.CreateNoteTable(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void dialog_note(final Note note) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_note);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        TextView textview = (TextView) dialog.findViewById(R.id.textViewPlaindate);
        Button btncalender = (Button) dialog.findViewById(R.id.buttoncalender);
        Button btnClose = (Button) dialog.findViewById(R.id.buttonclose);
        Button btnAdd = (Button) dialog.findViewById(R.id.buttonadd);
        dateText = (TextView) dialog.findViewById(R.id.textViewPlaindate);
        //final TextView txtgetcount =(TextView)dialog.findViewById(R.id.getcount);
        final EditText extName =(EditText) dialog.findViewById(R.id.editText);

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
                 //test();
                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter note name", Toast.LENGTH_LONG).show();
                    extName.requestFocus();
                    return;
                }
                long ret = 0;
                // Add new category
                //test();
                if (note == null) {
                    ret = noteViewModel.insertNote(new Note(name));
                   // noteViewModel.getNote().setValue(noteViewModel.getAllNote());
                    //test();
                    //txtgetcount.setText(""+ret);
                    if (ret > 0) {
                        Toast.makeText(getActivity(), "Add note successfully", Toast.LENGTH_LONG).show();
                    }
                } else { // Update category name
                    ret = noteViewModel.updateNote(note.getName(), new Note(name));
                    test();
                    //txtgetcount.setText(""+noteViewModel.getNoteCount());
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




