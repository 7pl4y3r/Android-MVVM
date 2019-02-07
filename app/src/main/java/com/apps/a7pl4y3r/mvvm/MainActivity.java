package com.apps.a7pl4y3r.mvvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRecyclerView();

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(MainActivity.this, AddEditNote.class), AppData.Add_Note_Request);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == AppData.Add_Note_Request) {

                String title = data.getStringExtra(AppData.EXTRA_TITLE);
                String description = data.getStringExtra(AppData.EXTRA_DESCRIPTION);
                int priority = data.getIntExtra(AppData.EXTRA_PRIORITY, 1);

                Note note = new Note(title, description, priority);
                noteViewModel.insert(note);

                Toast.makeText(this, "Note was saved!", Toast.LENGTH_SHORT).show();

            } else if (requestCode == AppData.EDIT_NOTE_REQUEST) {

                int id = data.getIntExtra(AppData.EXTRA_ID, -1);

                if (id == -1) {
                    Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                    return;
                }

                String title = data.getStringExtra(AppData.EXTRA_TITLE);
                String description = data.getStringExtra(AppData.EXTRA_DESCRIPTION);
                int priority = data.getIntExtra(AppData.EXTRA_PRIORITY, 1);

                Note note = new Note(title, description, priority);
                note.setId(id);
                noteViewModel.update(note);

                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

            }

        } else { Toast.makeText(this, "Note was not saved!", Toast.LENGTH_SHORT).show(); }

    }

    private void setRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        setNoteViewModel(noteAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();

            }

        }).attachToRecyclerView(recyclerView);

        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {

                Intent intent = new Intent(MainActivity.this, AddEditNote.class);

                intent.putExtra(AppData.EXTRA_ID, note.getId());
                intent.putExtra(AppData.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AppData.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AppData.EXTRA_PRIORITY, note.getPriority());

                startActivityForResult(intent, AppData.EDIT_NOTE_REQUEST);

            }
        });

    }

    private void setNoteViewModel(final NoteAdapter adapter) {

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

    }

}
