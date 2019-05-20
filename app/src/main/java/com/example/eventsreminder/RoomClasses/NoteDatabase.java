package com.example.eventsreminder.RoomClasses;

import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;


@Database(entities = {Note.class} , version = 1)
public abstract class NoteDatabase extends RoomDatabase
{
    static Context context;
    static NoteDatabase INSTANCE;

    public abstract NoteDao noteDao();

    public static NoteDatabase getDatabaseInstance(Context context)
    {
        Log.d("Note" , "room database");
        if (INSTANCE == null)
        {
            synchronized (NoteDatabase.class)
            {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                 NoteDatabase.class,
                                                 "notesdatabase").
                           allowMainThreadQueries().build();
            }
        }

        return INSTANCE;
    }




}
