package com.example.notemanagementsystem.ui.note;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

import com.example.notemanagementsystem.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class NoteFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private NoteViewModel mViewModel;
    private FloatingActionButton fab;
    private Button btncalender, btnClose;
    private TextView dateText;


    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = (FloatingActionButton) getView().findViewById(R.id.fab_note);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_note();
            }
        });

    }

    private void dialog_note() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_note);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        TextView textview = (TextView) dialog.findViewById(R.id.textViewPlaindate);
        Button btncalender = (Button) dialog.findViewById(R.id.buttoncalender);
        Button btnClose = (Button) dialog.findViewById(R.id.buttonclose);
        dateText = (TextView) dialog.findViewById(R.id.textViewPlaindate);
        btncalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showDatePicker();

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    private void showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date =dayOfMonth +"/"+month+"/"+year;
        dateText.setText(date);
    }
}




