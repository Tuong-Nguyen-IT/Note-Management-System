package com.example.notemanagementsystem.ui.status;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notemanagementsystem.database.DatabaseHandler;
import com.example.notemanagementsystem.model.Status;

import java.util.ArrayList;

public class StatusViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private DatabaseHandler database;
    private MutableLiveData<ArrayList<Status>> status;

    public StatusViewModel(Application application) {
        super(application);
        status = new MutableLiveData<ArrayList<Status>>();
        database = new DatabaseHandler(application);
        status.setValue(getAllStatus());
    }

    public ArrayList<Status> getAllStatus() {
        return database.getAllStatus();
    }

    public MutableLiveData<ArrayList<Status>> getStatus() {
        if (status == null) {
            status = new MutableLiveData<ArrayList<Status>>();
        }
        return status;
    }
    public long addStatus(Status status) {
        return database.addStatus(status);
    }
    public int updateStatus(String key, Status status) {
        return database.updateStatusName(key, status);
    }
    public int deleteStatus(String key) {
        return database.deleteStatus(key);
    }
}
