package com.example.notemanagementsystem.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notemanagementsystem.database.DatabaseHandler;
import com.example.notemanagementsystem.model.Note;
import com.example.notemanagementsystem.model.Status;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {

    DatabaseHandler database;
    MutableLiveData<ArrayList<Note>> note;

    public HomeViewModel(Application application){
        super(application);
        database = new DatabaseHandler(application);
    }
    public ArrayList<Status> getStatusArray(){
        ArrayList<Status> data;
        data = database.getAllStatus();
        return data;
    }

    private ArrayList<Note> setNote() {
        return database.getAllNote();
    }

    public float countStatus(String nameStatus){
        float count = 0;
        count = count+database.countStatus(nameStatus);
        return count;
    }
    public float sumStatus(){
        float count = 0;
        count = count+database.sumStatus();
        return count;
    }

}