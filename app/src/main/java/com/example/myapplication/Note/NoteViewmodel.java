package com.example.myapplication.Note;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.Note.Note;
import com.example.myapplication.Note.NoteRepository;

import java.util.List;

public class NoteViewmodel extends AndroidViewModel {
    private NoteRepository Noterepository;
    private LiveData<List<Note>> allnotes;
    public NoteViewmodel(@NonNull Application application) {
        super(application);
        Noterepository = new NoteRepository(application);
        allnotes = Noterepository.getAllNotes();
    }
    public void insert (Note note)
    {
        Noterepository.insert(note);
    }
    public void delete (Note note)
    {
        Noterepository.delete(note);
    }
    public void update (Note note)
    {
        Noterepository.update(note);
    }


    public LiveData<List<Note>> getAllnotes () {
        return allnotes;
    }
    public void deleteallnotes ()
    {
        Noterepository.deleteallnotes();
    }

}
