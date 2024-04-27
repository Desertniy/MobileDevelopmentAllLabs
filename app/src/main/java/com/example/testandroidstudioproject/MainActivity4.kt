package com.example.testandroidstudioproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val dbHelper = DBHelper(this)

        val recyclerView: RecyclerView = findViewById(R.id.recuclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val notes = dbHelper.getAllNotes()
        val adapter = NotesAdapter(notes)
        recyclerView.adapter = adapter

    }
}