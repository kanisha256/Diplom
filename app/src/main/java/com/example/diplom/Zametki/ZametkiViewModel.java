package com.example.diplom.Zametki;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.diplom.Room.Note;

import java.util.List;

public class ZametkiViewModel extends AndroidViewModel {
    private ZametkiRepository repository;
    private LiveData<List<Note>> allNotes;

    public ZametkiViewModel(@NonNull Application application) {
        super(application);
        repository = new ZametkiRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }
    public void delete(Note note) {repository.delete(note);}

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}

