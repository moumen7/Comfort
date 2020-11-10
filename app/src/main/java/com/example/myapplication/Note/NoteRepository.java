package com.example.myapplication.Note;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.Note.Note;
import com.example.myapplication.Note.NoteDao;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    public NoteRepository(Application application)
    {
       NoteDatabase database = NoteDatabase.getInstance(application);
       noteDao = database.noteDao();
       allNotes = noteDao.selectallnotes();
    }
    public void insert(Note note)
    {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }
    public void delete(Note note)
    {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }
    public void update(Note note)
    {
        new UpdateNoteAsyncTask(noteDao).execute(note);

    }
    public void deleteallnotes()
    {
        new DeleteallNotesAsyncTask(noteDao).execute();
    }
    public LiveData<List<Note>> getAllNotes()
    {
        return allNotes;
    }
    private static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao notedao;
        private InsertNoteAsyncTask(NoteDao notedao)
        {
            this.notedao = notedao;
        }

        protected Void doInBackground(Note... notes) {
            notedao.insert(notes[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao notedao;
        private UpdateNoteAsyncTask(NoteDao notedao)
        {
            this.notedao = notedao;
        }

        protected Void doInBackground(Note... notes) {
            notedao.Update(notes[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao notedao;
        private DeleteNoteAsyncTask(NoteDao notedao)
        {
            this.notedao = notedao;
        }

        protected Void doInBackground(Note... notes) {
            notedao.delete(notes[0]);
            return null;
        }
    }
    private static class DeleteallNotesAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private NoteDao notedao;
        private DeleteallNotesAsyncTask(NoteDao notedao)
        {
            this.notedao = notedao;
        }

        protected Void doInBackground(Void... Void) {
            notedao.deleteallnotes();
            return null;
        }
    }


}
