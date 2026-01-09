package com.example.practice_tasks.task14Retrofit.ui.notes

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_tasks.R
import com.example.practice_tasks.task14Retrofit.data.util.ApiResult
import kotlinx.coroutines.launch

class NotesActivity : AppCompatActivity() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notes)

        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        val edtTitle = findViewById<EditText>(R.id.edtTxtTitle)
        val edtBody = findViewById<EditText>(R.id.edtTxtNote)
        val btnAddNote = findViewById<Button>(R.id.btnAddNote)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val txtError = findViewById<TextView>(R.id.txtError)
        val btnRetry = findViewById<Button>(R.id.btnRetry)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = NotesAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnAddNote.setOnClickListener {
            val title = edtTitle.text.toString()
            val body = edtBody.text.toString()

            if (title.isEmpty() || body.isEmpty()) {
                Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.addNote(title, body)
        }

        lifecycleScope.launch {
            viewModel.notesState.collect {result ->
                when(result) {
                    is ApiResult.Idle -> {

                    }
                    is ApiResult.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        txtError.visibility = View.GONE
                        btnRetry.visibility = View.GONE
                    }
                    is ApiResult.Success -> {
                        progressBar.visibility = View.GONE
                        btnRetry.visibility = View.GONE
                        txtError.visibility = View.GONE
                        adapter.submitList(result.data)
                    }
                    is ApiResult.Error -> {
                        progressBar.visibility = View.GONE
                        txtError.visibility = View.VISIBLE
                        btnRetry.visibility = View.VISIBLE
                        txtError.text = result.message
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.addNoteState.collect { result ->

                when(result) {
                   is ApiResult.Idle -> {

                   }
                is ApiResult.Loading -> {
                    Toast.makeText(this@NotesActivity, "Adding note...",Toast.LENGTH_LONG).show()
                }
                    is ApiResult.Success -> {
                        Toast.makeText(this@NotesActivity, "Note added successfully",Toast.LENGTH_LONG).show()
                        edtTitle.text.clear()
                        edtBody.text.clear()
                        viewModel.fetchNotes()
                    }

                    is ApiResult.Error -> {
                        Toast.makeText(this@NotesActivity, "Error: ${result.message}",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        viewModel.fetchNotes()

    }
}