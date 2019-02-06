package com.apps.a7pl4y3r.mvvm;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    private int operationId = -1;

    public NoteRepository(Application application) {

        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();

    }


    public void insert(Note note) {
    }

    public void update(Note note) {
    }

    public void delete(Note note) {
    }

    public void deleteAllNotes(Note note) {
    }


    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public int getOperationId() {
        return operationId;
    }

    private static class BasicNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;
        private int operationId;

        public BasicNoteAsyncTask(NoteDao noteDao, int operationId) {
            this.noteDao = noteDao;
            this.operationId = operationId;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            switch (operationId) {

                case 1:
                    noteDao.insert(notes[0]);
                    return null;

                case 2:
                    noteDao.update(notes[0]);
                    return null;

                case 3:
                    noteDao.delete(notes[0]);
                    return null;

                case 4:
                    noteDao.deleteAllNotes();
                    return null;

                default:
                    return null;

            }

        }
    }

}
