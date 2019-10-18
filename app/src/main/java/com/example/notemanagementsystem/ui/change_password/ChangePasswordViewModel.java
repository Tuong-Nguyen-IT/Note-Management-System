package com.example.notemanagementsystem.ui.change_password;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notemanagementsystem.database.DatabaseHandler;
import com.example.notemanagementsystem.model.User;

import java.util.ArrayList;

public class ChangePasswordViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private DatabaseHandler db;
    private MutableLiveData<ArrayList<User>> users;

    public ChangePasswordViewModel(Application application){
        super(application);
        db = new DatabaseHandler(application);
    }

    public User getActiveUser(){
        return db.getActiveUser();
    }

    public int changePassword(User user){
        return db.changePassword(user);
    }
}
