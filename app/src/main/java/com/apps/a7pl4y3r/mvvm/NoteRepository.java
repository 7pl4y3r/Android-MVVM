package com.apps.a7pl4y3r.mvvm;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {


    private static final int insertOperation = 1;
    private static final int updateOperation = 2;
    private static final int deleteOperation = 3;
    private static final int deleteAllOperation = 4;


    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;


    public NoteRepository(Application application) {

        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();

    }


    public void insert(Note note) {
        new NoteAsyncTask(noteDao, insertOperation).execute(note);
    }

    public void update(Note note) {
        new NoteAsyncTask(noteDao, updateOperation).execute(note);
    }

    public void delete(Note note) {
        new NoteAsyncTask(noteDao, deleteOperation).execute(note);
    }

    public void deleteAllNotes() {
        new NoteAsyncTask(noteDao, deleteAllOperation).execute();
    }


    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class NoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;
        private int operationId;

        public NoteAsyncTask(NoteDao noteDao, int operationId) {
            this.noteDao = noteDao;
            this.operationId = operationId;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            switch (operationId) {

                case NoteRepository.insertOperation:
                    noteDao.insert(notes[0]);
                    return null;

                case NoteRepository.updateOperation:
                    noteDao.update(notes[0]);
                    return null;

                case NoteRepository.deleteOperation:
                    noteDao.delete(notes[0]);
                    return null;

                case NoteRepository.deleteAllOperation:
                    noteDao.deleteAllNotes();
                    return null;

                default:
                    return null;

            }

        }

    }

}
