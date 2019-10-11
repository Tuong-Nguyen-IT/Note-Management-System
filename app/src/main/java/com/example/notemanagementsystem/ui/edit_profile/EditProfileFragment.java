package com.example.notemanagementsystem.ui.edit_profile;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.notemanagementsystem.MainActivity;
import com.example.notemanagementsystem.R;

public class EditProfileFragment extends Fragment {

    private EditProfileViewModel mViewModel;
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtEmail;
    private Button btn_change;
    private Button btn_home;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EditProfileViewModel.class);
        // TODO: Use the ViewModel
        initControls();
        addEvents();
    }

    private void addEvents() {
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
            private void editProfile() {
                // check info and save profile to db
            }
        });
    }



    private void initControls() {
        txtFirstName = getView().findViewById(R.id.txtFirstName);
        txtLastName = getView().findViewById(R.id.txtLastName);
        txtEmail = getView().findViewById(R.id.txtEmail);
        btn_change = getView().findViewById(R.id.btnChange);
        btn_home = getView().findViewById(R.id.btnHome);
    }

}
