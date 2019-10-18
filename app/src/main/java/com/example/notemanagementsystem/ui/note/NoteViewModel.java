package com.example.notemanagementsystem.ui.note;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notemanagementsystem.database.DatabaseHandler;
import com.example.notemanagementsystem.model.Note;

import java.util.ArrayList;

public class NoteViewModel extends AndroidViewModel {
    private DatabaseHandler databaseNote;
    private MutableLiveData<ArrayList<Note>> note;


    public NoteViewModel(Application application) {
        super(application);
        note = new MutableLiveData<ArrayList<Note>>();
        databaseNote = new DatabaseHandler(application);
        note.setValue(getAllNote());

    }


    public MutableLiveData<ArrayList<Note>> getNote() {
        if (note == null) {
            note = new MutableLiveData<ArrayList<Note>>();
        }
        return note;
    }

    public ArrayList<Note> getAllNote() {
        return databaseNote.getAllNote();
    }

    public int updateNote(String key, Note note) {
        return databaseNote.updateNoteName(key, note);
    }

    public long insertNote(Note note) {
        return databaseNote.insertNote(note);
    }

    public int deleteNote(String key) {
        return databaseNote.deleteNote(key);
    }
}

