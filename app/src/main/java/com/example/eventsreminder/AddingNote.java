package com.example.eventsreminder;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eventsreminder.RoomClasses.Note;
import com.example.eventsreminder.RoomClasses.NoteRepository;
import com.example.eventsreminder.RoomClasses.NoteViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddingNote extends AppCompatActivity
{
    @BindView(R.id.notetitle_edittext) EditText titleEditText;
    @BindView(R.id.notepriority_edittext) EditText priorityEditText;
    @BindView(R.id.notetime_edittext) EditText timeEditText;
    @BindView(R.id.notedate_edittext) EditText dateEditText;

    @BindView(R.id.timedialog_linearlayout) LinearLayout timeLinearLayout;
    @BindView(R.id.datedialog_linearlayout) LinearLayout dateLinearLayout;

    @BindView(R.id.timepicker) TimePicker timePicker;
    @BindView(R.id.datepicker) DatePicker datePicker;

    @BindView(R.id.fields_layout) LinearLayout fieldsLayout;
    @BindView(R.id.InsertNewNote) Button saveNoteButton;


    String noteTitle;
    int notepriority;
    int noteHour, noteMinute;
    int noteDay, noteMonth, noteyear;

    Note note;
    NoteViewModel noteViewModel;


    // this value to differ from insert button or update button
    int insertBtnValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        noteViewModel  = ViewModelProviders.of(this).get(NoteViewModel.class);

        note = (Note) getIntent().getSerializableExtra("Note_Object");
        if (note != null)
        {
            noteTitle = note.getTitle();
            noteHour = note.getHour();
            noteMinute = note.getMinute();
            noteDay = note.getDay();
            noteMonth = note.getMonth();
            noteyear = note.getYear();
            notepriority = note.getPriority();

            setNoteValuesToInputFields();


            insertBtnValue = 1;
            saveNoteButton.setText("Update");
        }

        timeEditText.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                timeLinearLayout.setVisibility(View.VISIBLE);
                fieldsLayout.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        dateEditText.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                dateLinearLayout.setVisibility(View.VISIBLE);
                fieldsLayout.setVisibility(View.INVISIBLE);
                return false;
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.addactivity_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_save)
        {
            if (checkAllFields())
            {
                if(insertBtnValue == 1)
                {
                    noteViewModel.updateNote(note);
                }
                else
                {
                    noteViewModel.insertNote(note);
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            return true;
        }

        if (id == R.id.action_cancel)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    // this method get notes data and set edittexts to this values
    private void setNoteValuesToInputFields()
    {
        titleEditText.setText(noteTitle);
        timeEditText.setText(noteHour+":"+noteMinute);
        dateEditText.setText(noteDay + "/" + noteMonth + "/" + noteyear);
        priorityEditText.setText("" + notepriority);
    }


    // get hour and minute from timepicker
    @OnClick(R.id.savetime_button)
    public void SaveTime(View v)
    {
        noteHour = timePicker.getCurrentHour();
        noteMinute = timePicker.getCurrentMinute();

        timeEditText.setText(noteHour + ":" + noteMinute);

        fieldsLayout.setVisibility(View.VISIBLE);
        timeLinearLayout.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.savedate_button)
    public void SaveDate(View v)
    {
        noteDay = datePicker.getDayOfMonth();
        noteMonth = datePicker.getMonth()+1;
        noteyear = datePicker.getYear();

        dateEditText.setText(noteDay + "/" + noteMonth + "/" + noteyear);

        fieldsLayout.setVisibility(View.VISIBLE);
        dateLinearLayout.setVisibility(View.INVISIBLE);
    }


    public boolean checkAllFields()
    {
        if (TextUtils.isEmpty(titleEditText.getText().toString()))
        {
            titleEditText.setError("enter title");
            return false ;
        }
        else if (TextUtils.isEmpty(priorityEditText.getText().toString()))
        {
            priorityEditText.setError("enter priority");
            return false;
        }
        else
        {
            getDataFromInputFields();
            setDataToNoteObject();
            return true;
        }
    }

    public void getDataFromInputFields()
    {
        noteTitle = titleEditText.getText().toString();
        notepriority = Integer.parseInt(priorityEditText.getText().toString());
    }

    public void setDataToNoteObject()
    {
        if (insertBtnValue == 0)
        {
            note = new Note();
        }
        note.setTitle(noteTitle);
        note.setHour(noteHour);
        note.setMinute(noteMinute);
        note.setDay(noteDay);
        note.setMonth(noteMonth);
        note.setYear(noteyear);
        note.setPriority(notepriority);
    }


    @OnClick(R.id.InsertNewNote) void AddNote()
    {

       if (checkAllFields())
       {
           if(insertBtnValue == 1)
           {
               noteViewModel.updateNote(note);
           }
           else
           {
               noteViewModel.insertNote(note);
           }

           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
           startActivity(intent);
           finish();
       }

    }

}
