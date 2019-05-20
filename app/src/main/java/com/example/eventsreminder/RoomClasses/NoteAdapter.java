package com.example.eventsreminder.RoomClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eventsreminder.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>
{
    Context context;
    public static List<Note> notesList;

    SetOnRecyclerViewTemClick setOnRecyclerViewTemClick;

    public NoteAdapter(Context context, List<Note> notesList)
    {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.itemlist_format, viewGroup, false);
        NoteViewHolder noteViewHolder = new NoteViewHolder(v);

        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i)
    {
        Note currentNote = notesList.get(i);

        noteViewHolder.noteTitleTextView.setText(currentNote.getTitle());
        String notetime = currentNote.getHour() + ":" + currentNote.getMinute();
        noteViewHolder.noteTimeTextView.setText(notetime);

        int priorityColor = setPriorityColor(currentNote.getPriority());
        noteViewHolder.notePriorityTextView.setText(String.valueOf(currentNote.getPriority()));
        noteViewHolder.notePriorityTextView.setBackgroundResource(priorityColor);

    }

    @Override
    public int getItemCount()
    {
        return notesList.size();
    }

    public void setNotes(List<Note> notes)
    {
        this.notesList = notes;
        updateAdapter();

    }

    public Note getNote(int itemPosition) { return notesList.get(itemPosition);  }

    public void updateAdapter()
    {
        notifyDataSetChanged();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder
    {

        TextView notePriorityTextView, noteTitleTextView, noteTimeTextView;

        public NoteViewHolder(@NonNull View itemView)
        {
            super(itemView);

            notePriorityTextView = (TextView) itemView.findViewById(R.id.notepriority_textview);
            noteTitleTextView = (TextView) itemView.findViewById(R.id.notetitle_textview);
            noteTimeTextView = (TextView) itemView.findViewById(R.id.notetime_textview);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position = getAdapterPosition();

                   setOnRecyclerViewTemClick.onRecyclerItemClicked(notesList.get(position));
                }
            });
        }
    }


    public interface SetOnRecyclerViewTemClick
    {
        public void onRecyclerItemClicked(Note note);
    }

    public void setOnItemClicked(SetOnRecyclerViewTemClick setOnRecyclerViewTemClick)
    {
        this.setOnRecyclerViewTemClick = setOnRecyclerViewTemClick;

    }






    public int setPriorityColor(int priorityValue)
    {
        int priorityColorResourceId;
        switch (priorityValue)
        {
            case 0:
            case 1:
                priorityColorResourceId = R.color.priority1;
                break;
            case 2:
                priorityColorResourceId = R.color.priority2;
                break;
            case 3:
                priorityColorResourceId = R.color.priority3;
                break;
            case 4:
                priorityColorResourceId = R.color.priority4;
                break;
            case 5:
                priorityColorResourceId = R.color.priority5;
                break;
            case 6:
                priorityColorResourceId = R.color.priority6;
                break;
            case 7:
                priorityColorResourceId = R.color.priority7;
                break;
            case 8:
                priorityColorResourceId = R.color.priority8;
                break;
            case 9:
                priorityColorResourceId = R.color.priority9;
                break;
            default:
                priorityColorResourceId = R.color.priority10;
                break;
        }

        return priorityColorResourceId;
       // return ContextCompat.getColor(getClass() ,priorityColorResourceId);
    }




}
