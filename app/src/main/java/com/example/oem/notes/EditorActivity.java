package com.example.oem.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.oem.notes.data.NotesContract;
import com.example.oem.notes.data.NotesDBHelper;
import java.util.Date;

public class EditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        saveButtonClickListener();
        initEditForm();

    }

    protected void initEditForm() {
        if(getIntent().getExtras()!=null) {
            EditText nameInput = (EditText) findViewById(R.id.noteNameInput);
            EditText contentInput = (EditText) findViewById(R.id.noteContentInput);
            EditText emailInput = (EditText) findViewById(R.id.noteEmailInput);
            int noteId = getIntent().getExtras().getInt("noteId");
            NoteModel note = (NoteModel) NoteModel.getNote(this, noteId);
            nameInput.setText(note.getName());
            emailInput.setText(note.getEmail());
            contentInput.setText(note.getContent());
        }
    }

    protected void saveButtonClickListener() {
        Button savebutton = (Button)findViewById(R.id.saveNoteButton);
        final EditText nameEditText = (EditText)findViewById(R.id.noteNameInput);
        final EditText contentEditText = (EditText)findViewById(R.id.noteContentInput);
        final EditText noteEmailText = (EditText)findViewById(R.id.noteEmailInput);
        savebutton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        String currentDate = sdf.format(new Date());

                        saveNote(
                                nameEditText.getText().toString(),
                                contentEditText.getText().toString(),
                                currentDate,
                                noteEmailText.getText().toString(),
                                ""
                        );

                        String successText = "Saved successfully !";

                        Intent intent = new Intent(EditorActivity.this, MainActivity.class);
                        intent.putExtra("message", successText);
                        startActivity(intent);
                    }
                }
        );
    }

    protected void saveNote(String name, String content, String date, String email, String picture) {

        NotesDBHelper mDbHelper = new NotesDBHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(NotesContract.NotesEntry.COLUMN_NAME, name);
        values.put(NotesContract.NotesEntry.COLUMN_CONTENT, content);
        values.put(NotesContract.NotesEntry.COLUMN_DATE, date);
        values.put(NotesContract.NotesEntry.COLUMN_EMAIL, email);
        values.put(NotesContract.NotesEntry.COLUMN_PICTURE, picture);

        String[] noteId = new String[1];
        if(getIntent().getExtras()==null) {
            long newRowId = db.insert(NotesContract.NotesEntry.TABLE_NAME, null, values);
        } else {
            noteId[0] = Integer.toString(getIntent().getExtras().getInt("noteId"));
            long newRowId = db.update(NotesContract.NotesEntry.TABLE_NAME, values, "_id = ?", noteId);
        }
    }
}
