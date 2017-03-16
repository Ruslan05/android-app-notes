package com.example.oem.notes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = NotesDBHelper.class.getSimpleName();

    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "notes.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 2;

    /**
     * Конструктор {@link NotesDBHelper}.
     *
     * @param context Контекст приложения
     */
    public NotesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Вызывается при создании базы данных
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        String SQL_CREATE_NOTES_TABLE = "CREATE TABLE " + NotesContract.NotesEntry.TABLE_NAME + " ("
                + NotesContract.NotesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NotesContract.NotesEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + NotesContract.NotesEntry.COLUMN_CONTENT + " TEXT, "
                + NotesContract.NotesEntry.COLUMN_DATE + " DATE NOT NULL, "
                + NotesContract.NotesEntry.COLUMN_PICTURE + " TEXT)";

        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_NOTES_TABLE);
    }

    /**
     * Вызывается при обновлении схемы базы данных
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_ALTER_NOTES_TABLE = "ALTER TABLE " + NotesContract.NotesEntry.TABLE_NAME
                + " ADD COLUMN "
                + NotesContract.NotesEntry.COLUMN_EMAIL + " TEXT NOT NULL DEFAULT 'ruslandovg291@gmail.com'";
        db.execSQL(SQL_ALTER_NOTES_TABLE);
    }
}
