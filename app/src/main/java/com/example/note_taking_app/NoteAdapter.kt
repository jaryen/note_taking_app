package com.example.note_taking_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    private val notes: MutableList<Note>
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    // Hold the views of the RecyclerView
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Called when recylerview needs a new view holder
    // by inflating the layout.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    // Bind the data to our items from Note to the view holder
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val curNote = notes[position]
        val noteTitle = holder.itemView.findViewById<TextView>(R.id.note_title)
        noteTitle.text = notes[position].title

        val checkBoxDone = holder.itemView.findViewById<CheckBox>(R.id.cbDone)
        checkBoxDone.isChecked = notes[position].checked

        checkBoxDone.setOnCheckedChangeListener { _, checked -> curNote.checked = !curNote.checked }
    }

    // Returns number of items in RecyclerView
    override fun getItemCount(): Int {
        return notes.size
    }

    // Adds a new Note
    fun addNote(note: Note) {
        notes.add(note)
        notifyItemInserted(notes.size - 1)
    }

    // Deletes all checked Notes
    fun deleteNote() {
        notes.removeAll { note -> note.checked }
        notifyDataSetChanged()
    }
}