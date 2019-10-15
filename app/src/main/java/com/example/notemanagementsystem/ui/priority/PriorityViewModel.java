package com.example.notemanagementsystem.ui.priority;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notemanagementsystem.database.DatabasePriority;
import com.example.notemanagementsystem.model.Priority;

import java.util.ArrayList;

public class PriorityViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private DatabasePriority database;
    private MutableLiveData<ArrayList<Priority>> priorities;

    public PriorityViewModel(Application application) {
        super(application);

        priorities = new MutableLiveData<ArrayList<Priority>>();

        database = new DatabasePriority(application);
        priorities.setValue(getAllPriorities());
    }
    public MutableLiveData<ArrayList<Priority>> getPriorities() {
        if (priorities == null) {
            priorities = new MutableLiveData<ArrayList<Priority>>();
        }
        return priorities;
    }
    public ArrayList<Priority> getAllPriorities() {
        return database.getAllPriority();
    }
    public long addPriority(Priority priority) {
        return database.insertPriority(priority);
    }
    public int updatePriority(String key, Priority priority) {
        return database.updatePriorityName(key, priority);
    }
    public int deletePriority(String key) {
        return database.deletePriority(key);
    }
}
