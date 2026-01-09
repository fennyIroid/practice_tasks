package com.example.practice_tasks.task14Retrofit.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_tasks.R
import com.example.practice_tasks.task14Retrofit.data.model.NotesModel

class NotesAdapter: RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val notes = mutableListOf<NotesModel>()

    fun submitList(newNotes: List<NotesModel>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent,false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesAdapter.NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size

    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val txtTitle: TextView = itemView.findViewById<TextView>(R.id.txtTitle)
        private val txtBody: TextView = itemView.findViewById<TextView>(R.id.txtBody)


        fun bind(note: NotesModel) {
            txtTitle.text = note.title
            txtBody.text = note.body
        }
    }

}