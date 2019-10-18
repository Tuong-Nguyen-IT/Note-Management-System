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
import android.widget.Toast;

import com.example.notemanagementsystem.MainActivity;
import com.example.notemanagementsystem.R;
import com.example.notemanagementsystem.model.User;

public class EditProfileFragment extends Fragment {
    EditProfileViewModel editProfileViewModel;
    private EditProfileViewModel mViewModel;
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtEmail;
    private Button btn_change;
    private Button btn_home;
    private User activeUser;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        editProfileViewModel = ViewModelProviders.of(this).get(EditProfileViewModel.class);
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
        loadUser();

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
//                startActivity(intent);
            }

            private void editProfile() {
                int ret;
                String email = txtEmail.getText().toString();
                String firstName = txtFirstName.getText().toString();
                String LastName = txtLastName.getText().toString();
                if (activeUser != null) {
                    activeUser.setFirstName(firstName);
                    activeUser.setLastName(LastName);
                    activeUser.setEmail(email);
                    ret = editProfileViewModel.editProfile(activeUser);
                    if (ret != 0) {
                        Toast.makeText(getContext(), "Update successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void loadUser() {
        activeUser = editProfileViewModel.getActiveUser();
        if (activeUser != null) {
            txtFirstName.setText(activeUser.getFirstName());
            txtLastName.setText(activeUser.getLastName());
            txtEmail.setText(activeUser.getEmail());
        }
    }


    private void initControls () {
        txtFirstName = getView().findViewById(R.id.txtFirstName);
        txtLastName = getView().findViewById(R.id.txtLastName);
        txtEmail = getView().findViewById(R.id.txtEmail);
        btn_change = getView().findViewById(R.id.btnChange);
        btn_home = getView().findViewById(R.id.btnHome);
    }

}
