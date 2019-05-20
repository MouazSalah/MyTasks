package com.example.eventsreminder.RoomClasses;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NoteDao
{
    @Insert
    public void insertNote(Note note);


    /*// fetching data
   @Query("SELECT * FROM Note WHERE id = id")
   public Note getNote(Note note);*/


    @Query("SELECT * FROM Note")
    public LiveData<List<Note>> getAllNotes();


    // updating
    @Update
    public void updateNote(Note note);


    // deleting
    @Delete
    public void deleteNote(Note note);


    @Query("DELETE FROM Note")
    public void deleteAllNotes();


}
