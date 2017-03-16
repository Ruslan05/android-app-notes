package com.example.oem.notes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.oem.notes.data.NotesContract.NotesEntry;
import com.example.oem.notes.data.NotesDBHelper;

import java.util.ArrayList;
import java.util.List;

public class NoteModel {

    private int id;
    private String name;
    private String content;
    private String date;
    private String email;
    private String picture;
    private boolean selected;

    public NoteModel(){}

    public NoteModel(
            int id ,
            String name,
            String content,
            String date,
            String email,
            String picture
    ) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.date = date;
        this.email = email;
        this.date = picture;
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public static List<NoteModel> getNotesList(Context context) {
        NotesDBHelper mDbHelper = new NotesDBHelper(context);
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                NotesEntry._ID,
                NotesEntry.COLUMN_NAME,
                NotesEntry.COLUMN_CONTENT,
                NotesEntry.COLUMN_DATE,
                NotesEntry.COLUMN_EMAIL,
                NotesEntry.COLUMN_PICTURE
        };

        // Делаем запрос
        Cursor cursor = db.query(
                NotesEntry.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки

        List<NoteModel> notesList = new ArrayList<NoteModel>();

        try {
            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа

                notesList.add(
                        new NoteModel(
                                cursor.getInt(cursor.getColumnIndex(NotesEntry._ID)),
                                cursor.getString(cursor.getColumnIndex(NotesEntry.COLUMN_NAME)),
                                cursor.getString(cursor.getColumnIndex(NotesEntry.COLUMN_CONTENT)),
                                cursor.getString(cursor.getColumnIndex(NotesEntry.COLUMN_DATE)),
                                cursor.getString(cursor.getColumnIndex(NotesEntry.COLUMN_EMAIL)),
                                cursor.getString(cursor.getColumnIndex(NotesEntry.COLUMN_PICTURE))
                        ));
            }

            return notesList;
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }

    public static NoteModel getNote(Context context, int id) {
        NotesDBHelper mDbHelper = new NotesDBHelper(context);
        SQLiteDatabase mDatabase = mDbHelper.getWritableDatabase();
        NoteModel note = new NoteModel();

        String query = "SELECT * FROM " + NotesEntry.TABLE_NAME + " WHERE _id = " + id;
        Cursor cursor2 = mDatabase.rawQuery(query, null);

        while (cursor2.moveToNext()) {
            note = new NoteModel(
                    cursor2.getInt(cursor2.getColumnIndex(NotesEntry._ID)),
                    cursor2.getString(cursor2.getColumnIndex(NotesEntry.COLUMN_NAME)),
                    cursor2.getString(cursor2.getColumnIndex(NotesEntry.COLUMN_CONTENT)),
                    cursor2.getString(cursor2.getColumnIndex(NotesEntry.COLUMN_DATE)),
                    cursor2.getString(cursor2.getColumnIndex(NotesEntry.COLUMN_EMAIL)),
                    cursor2.getString(cursor2.getColumnIndex(NotesEntry.COLUMN_PICTURE)

            ));
        }
        cursor2.close();

        return note;
    }
}
