package com.example.eventsreminder.RoomClasses;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

public class NoteViewModel extends AndroidViewModel
{
    Context context;
    NoteRepository noteRepository;
    LiveData<List<Note>> mAllNotes;

    public NoteViewModel(Application application)
    {
        super(application);
        noteRepository = new NoteRepository(application);
        mAllNotes = noteRepository.mAllNotes;

    }


    // adding new note to database
    public void insertNote(Note note) { noteRepository.insertNote(note);}

    // updating note data
    public void updateNote(Note note) {noteRepository.updateNote(note);  }

    // getting al notes data
    public LiveData<List<Note>> getmAllNotes()
    {
        return mAllNotes;
    }


    /*// getting specific note data
    public void getNote(Note note) { noteRepository.getNote(note);  }

*/
    // deleting one note by swipping or aler dialog
    public void deleteNote(Note note) {noteRepository.deleteNote(note);  }

    // delete all note which in database
    public void deleteAllNotes() { noteRepository.deleteAllNotes();  };


}
