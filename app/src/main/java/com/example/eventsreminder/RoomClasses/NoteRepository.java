package com.example.eventsreminder.RoomClasses;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class NoteRepository
{
    NoteDao noteDao;
    LiveData<List<Note>> mAllNotes;


    public NoteRepository(Context context)
    {
        NoteDatabase noteDatabase = NoteDatabase.getDatabaseInstance(context);
        noteDao = noteDatabase.noteDao();
        mAllNotes = noteDao.getAllNotes();
    }


    // adding new note to database
    public void insertNote(Note note) { new InsertAsyncTask(noteDao).execute(note);}

    // updating note data
    public void updateNote(Note note) { new UpdateNoteAsyncTask(noteDao).execute(note);  }

    /*// getting new data for specific id or title or.... to display in editing activity
    public void getNote(Note note) { new GetNoteAsyncTask(noteDao).execute(note);  }
*/
    // getting all notes to display them in recyclerview
    public LiveData<List<Note>> getmAllNotes() { return mAllNotes;  }

    // deleting one note by swipping or aler dialog
    public void deleteNote(Note note) { new DeleteNoteAsyncTask(noteDao).execute(note);  }

    // delete all note which in database
    public void deleteAllNotes() { new DeleteNoteAsyncTask(noteDao).execute();  }




    // Asynctask for each CRUD operation

    public class InsertAsyncTask extends AsyncTask<Note, Void ,Void>
    {
        NoteDao noteDaoObject;

        public InsertAsyncTask(NoteDao noteDaoObject)
        {
            this.noteDaoObject = noteDaoObject;
            Log.d("Note" , "insert asynctask constructor");
        }

        @Override
        protected Void doInBackground(Note... notes)
        {
            noteDaoObject.insertNote(notes[0]);
            Log.d("Note" , "doinback insertasynctask");
            return null;
        }
    }

   /* public class GetNoteAsyncTask extends AsyncTask<Note, Void ,Void>
    {
        NoteDao noteDaoObject;

        public GetNoteAsyncTask(NoteDao noteDaoObject)
        {
            this.noteDaoObject = noteDaoObject;
        }

        @Override
        protected Void doInBackground(Note... notes)
        {
            noteDaoObject.getNote(notes[0]);
            return null;
        }
    }*/


    public class UpdateNoteAsyncTask extends AsyncTask<Note, Void ,Void>
    {
        NoteDao noteDaoObject;

        public UpdateNoteAsyncTask(NoteDao noteDaoObject)
        {
            this.noteDaoObject = noteDaoObject;
        }

        @Override
        protected Void doInBackground(Note... notes)
        {
            noteDaoObject.updateNote(notes[0]);
            Log.d("note" , "update asynctask");
            return null;
        }
    }

    public class DeleteNoteAsyncTask extends AsyncTask<Note, Void ,Void>
    {
        NoteDao noteDaoObject;

        public DeleteNoteAsyncTask(NoteDao noteDaoObject)
        {
            this.noteDaoObject = noteDaoObject;
        }

        @Override
        protected Void doInBackground(Note... notes)
        {
            noteDaoObject.deleteAllNotes();
            return null;
        }
    }

    public class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void ,Void>
    {
        NoteDao noteDaoObject;

        public DeleteAllNotesAsyncTask(NoteDao noteDaoObject)
        {
            this.noteDaoObject = noteDaoObject;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            noteDaoObject.deleteAllNotes();
            return null;
        }
    }




}
