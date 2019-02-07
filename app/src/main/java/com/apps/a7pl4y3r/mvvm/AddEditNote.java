package com.apps.a7pl4y3r.mvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Objects;

public class AddEditNote extends AppCompatActivity {


    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        setWidgets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.save_note:
                saveNote();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void setWidgets() {

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(AppData.EXTRA_ID)) {

            setTitle("Edit note");
            editTextTitle.setText(intent.getStringExtra(AppData.EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(AppData.EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(AppData.EXTRA_PRIORITY, 1));

        } else { setTitle("Add Note"); }

    }

    private void saveNote() {

        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {

            Toast.makeText(this, "Fill all fields first!", Toast.LENGTH_SHORT).show();
            return;

        }

        Intent data = new Intent();
        data.putExtra(AppData.EXTRA_TITLE, title);
        data.putExtra(AppData.EXTRA_DESCRIPTION, description);
        data.putExtra(AppData.EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(AppData.EXTRA_ID, -1);
        if (id != -1)
            data.putExtra(AppData.EXTRA_ID, id);

        setResult(RESULT_OK, data);
        finish();

    }

}