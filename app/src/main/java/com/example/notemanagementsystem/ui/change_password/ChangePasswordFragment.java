package com.example.notemanagementsystem.ui.change_password;

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

public class ChangePasswordFragment extends Fragment {

    private ChangePasswordViewModel mViewModel;
    private EditText txtCurrentPwd;
    private EditText txtNewPwd;
    private EditText txtConfirmPwd;
    private Button btnHome;
    private Button btnChange;


    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        // TODO: Use the ViewModel
        initControls();
        addEvents();
    }

    private void addEvents() {
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }

            private void changePassword() {
                //check and save new password to db
            }
        });
        
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initControls() {
        txtCurrentPwd = getView().findViewById(R.id.txtCurrentPwd);
        txtNewPwd = getView().findViewById(R.id.txtNewPwd);
        txtConfirmPwd = getView().findViewById(R.id.txtConfirmPwd);
        btnChange = getView().findViewById(R.id.btnChange);
        btnHome = getView().findViewById(R.id.btnHome);
    }

}
