package com.example.testandroidstudioproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.sql.SQLException

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val activity3ToActivity1 = findViewById<Button>(R.id.button5)
        activity3ToActivity1.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val activity3ToActivity2 = findViewById<Button>(R.id.button6)
        activity3ToActivity2.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        val activity3ToActivity4 = findViewById<Button>(R.id.button11)
        activity3ToActivity4.setOnClickListener{
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)
        }
        val dbHelper = DBHelper(this)
        val inputId = findViewById<TextInputEditText>(R.id.textInputEditText)
        val inputDate = findViewById<TextInputEditText>(R.id.textInputEditText2)
        val inputNote = findViewById<TextInputEditText>(R.id.textInputEditText3)
        val buttonAdd = findViewById<Button>(R.id.button7)
        val buttonUpdate = findViewById<Button>(R.id.button8)
        val buttonDelete = findViewById<Button>(R.id.button10)

        buttonAdd.setOnClickListener {
            val note = Note(inputId.text.toString().toInt(), inputDate.text.toString(), inputNote.text.toString())
            dbHelper.addNote(note)
        }
        buttonUpdate.setOnClickListener {
            val updatedNote = Note(inputId.text.toString().toInt(), inputDate.text.toString(), inputNote.text.toString())
            dbHelper.updateNote(updatedNote)
        }
        buttonDelete.setOnClickListener {
            dbHelper.deleteNoteById(inputId.text.toString().toInt())
        }
    }
}
