package com.example.testandroidstudioproject

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "NotesDB"
        private const val TABLE_NAME = "notes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_NOTE = "note"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val create = ("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_DATE TEXT, $COLUMN_NOTE TEXT)")
        db.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun addNote(note: Note): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_DATE, note.date)
        values.put(COLUMN_NOTE, note.note)
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun getAllNotes(): ArrayList<Note> {
        val noteList = ArrayList<Note>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        if (cursor != null) {
            do {
                if (cursor.getColumnIndex(COLUMN_ID) != -1 && cursor.getColumnIndex(COLUMN_DATE) != -1 && cursor.getColumnIndex(COLUMN_NOTE) != -1) {
                    val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                    val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                    val note = cursor.getString(cursor.getColumnIndex(COLUMN_NOTE))
                    val noteObj = Note(id, date, note)
                    noteList.add(noteObj)
                }
            } while (cursor.moveToNext())
        }
        cursor?.close()
        db.close()
        return noteList
    }

    fun updateNote(note: Note): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_DATE, note.date)
        values.put(COLUMN_NOTE, note.note)
        return db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(note.id.toString()))
    }

    fun deleteNoteById(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}
