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
import android.widget.Toast;

import com.example.notemanagementsystem.MainActivity;
import com.example.notemanagementsystem.R;
import com.example.notemanagementsystem.model.User;

public class ChangePasswordFragment extends Fragment {

    ChangePasswordViewModel changePasswordViewModel;
    private ChangePasswordViewModel mViewModel;
    private EditText txtCurrentPwd;
    private EditText txtNewPwd;
    private EditText txtConfirmPwd;
    private Button btnHome;
    private Button btnChange;
    private User activeUser;


    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        changePasswordViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
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
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                startActivity(intent);
            }

            private void changePassword() {
                //check and save new password to db
                activeUser = changePasswordViewModel.getActiveUser();
                if (activeUser!=null){
                    String currentPassword = txtCurrentPwd.getText().toString();
                    if (activeUser.getPwd().equals(currentPassword)){
                        String newPassword = txtNewPwd.getText().toString();
                        String confirmPassword = txtConfirmPwd.getText().toString();
                        if (!checkPasswordlength(newPassword)){
                            Toast.makeText(getContext(), "Password must contain at least 8 characters", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (newPassword.equals(confirmPassword)){
                            int ret;
                            activeUser.setPwd(newPassword);
                            ret = changePasswordViewModel.changePassword(activeUser);
                            if (ret != 0){
                                Toast.makeText(getContext(), "Change password successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(), "Unable to change password", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getContext(), "Password dose not match", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
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

    private boolean checkPasswordlength(String password){
        if (password.length() < 8)
            return false;
        return true;
    }


}
