package com.apps.a7pl4y3r.mvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {


    private OnItemClickListener listener;


    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {

        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        Note currentNote = getItem(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));

    }



    public Note getNoteAt(int position) {
        return getItem(position);
    }


    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



    class NoteHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public NoteHolder(View view) {
            super(view);

            textViewTitle = view.findViewById(R.id.text_view_title);
            textViewDescription = view.findViewById(R.id.text_view_description);
            textViewPriority = view.findViewById(R.id.text_view_priority);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(position));

                }
            });

        }

    }

}
