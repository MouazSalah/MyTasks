package com.example.eventsreminder;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.eventsreminder.RoomClasses.Note;
import com.example.eventsreminder.RoomClasses.NoteAdapter;
import com.example.eventsreminder.RoomClasses.NoteViewModel;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.empty_view) RelativeLayout emptyView;
    @BindView(R.id.recyclerview) RecyclerView noteRecyclerView;
    @BindView(R.id.addnew_imageview)  ImageView addNewImgView;


    NoteAdapter noteAdapter;
    NoteViewModel noteViewModel;
    public static List<Note> noteList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        noteViewModel  = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getmAllNotes().observe(this, new Observer<List<Note>>()
        {
            @Override
            public void onChanged(@Nullable List<Note> notes)
            {
                if (notes.size() == 0)
                {
                    emptyView.setVisibility(View.VISIBLE);
                    noteRecyclerView.setVisibility(View.GONE);
                }

                for (int i =0; i < notes.size(); i++)
                {
                    noteAdapter.setNotes(notes);
                    noteList.add(notes.get(i));
                }
            }
        });

        noteAdapter = new NoteAdapter(this, noteList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        noteRecyclerView.setLayoutManager(mLayoutManager);
        noteRecyclerView.setAdapter(noteAdapter);


        noteAdapter.setOnItemClicked(new NoteAdapter.SetOnRecyclerViewTemClick()
        {
            @Override
            public void onRecyclerItemClicked(Note note)
            {
                ShowItemAlertDialog(note);

            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1)
            {

                Toast.makeText(MainActivity.this, "moved", Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i)
            {
                Toast.makeText(MainActivity.this, "swipped", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(myBroadcastReceiver, filter);
    }


    public void AddNewNote(View v)
    {
        Intent intent = new Intent(getApplicationContext(), AddingNote.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            noteViewModel.deleteAllNotes();
            noteAdapter.notifyDataSetChanged();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void ShowItemAlertDialog(Note note)
    {
        String [] options = {"Update" , "Delete"};

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Choose Option")
                .setCancelable(false)
                    .setItems(options, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if ("Update" == options[which])
                            {
                                UpdateSelectedItem(note);
                            }
                            if ("Delete" == options[which])
                            {
                                DeleteSelectedItem(note);
                            }

                        }
                    })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();;

    }



    private void DeleteSelectedItem(Note note)
    {
        noteViewModel.deleteNote(note);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }



    private void UpdateSelectedItem(Note note)
    {
           Intent intent = new Intent(getApplicationContext(), AddingNote.class);
           intent.putExtra("Note_Object" , note);
           startActivity(intent);
    }
}
