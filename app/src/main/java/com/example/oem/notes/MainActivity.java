package com.example.oem.notes;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.oem.notes.data.NotesContract.NotesEntry;
import com.example.oem.notes.data.NotesDBHelper;


public class MainActivity extends AppCompatActivity {

    InteractiveArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.initList();
        this.onListViewTapListener();
        this.createNoteButtonListener();
        this.shoMessages();
        this.onlistViewLongTaplListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add("Delete marked");
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle()=="Delete marked"){
            for(int i=0; i < adapter.getCheckedList().size(); i++) {
                if(adapter.getCheckedList().get(i).isSelected()) {
                    deleteCheckedNotes(adapter.getCheckedList().get(i).getId());
                }
            }

            Toast.makeText(this, "Deleted successfully !", Toast.LENGTH_LONG).show();
            initList();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void deleteCheckedNotes(int id) {
        NotesDBHelper mDbHelper = new NotesDBHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] noteId = new String[1];
        noteId[0] = Integer.toString(id);
        db.delete(NotesEntry.TABLE_NAME, "_id = ?", noteId);
    }

    protected void createNoteButtonListener() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void initList() {
        ListView lvMain = (ListView) findViewById(R.id.notes_list);
        adapter = new InteractiveArrayAdapter(this, NoteModel.getNotesList(this));
        lvMain.setAdapter(adapter);
    }

    protected void onListViewTapListener() {
        ListView lvMain = (ListView) findViewById(R.id.notes_list);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra("noteId", NoteModel.getNotesList(MainActivity.this).get(position).getId());
                startActivity(intent);
            }
        });
    }

    protected void onlistViewLongTaplListener() {
        ListView lvMain = (ListView) findViewById(R.id.notes_list);
        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setType("message/rfc822");
                //i.setType("plain/text");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{adapter.getItem(position).getEmail()});
                i.putExtra(Intent.EXTRA_SUBJECT, adapter.getItem(position).getName());
                i.putExtra(Intent.EXTRA_TEXT   , adapter.getItem(position).getContent());
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    protected void shoMessages() {
        if (getIntent().getExtras() != null) {
            Context context = getApplicationContext();
            CharSequence text = getIntent().getExtras().getString("message");

            if(text != null) {
                Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
