package com.example.notemanagementsystem.ui.edit_profile;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.notemanagementsystem.database.DatabaseHandler;
import com.example.notemanagementsystem.model.User;

import java.util.ArrayList;

public class EditProfileViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private DatabaseHandler db;
    private MutableLiveData<ArrayList<User>> users;

    public EditProfileViewModel(Application application){
        super(application);
        db = new DatabaseHandler(application);
    }

    public User getActiveUser(){
        return db.getActiveUser();
    }

    public int editProfile(User user){
        return db.updateProfile(user);
    }
}

