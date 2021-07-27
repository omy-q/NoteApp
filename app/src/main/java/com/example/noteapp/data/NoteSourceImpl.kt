package com.example.noteapp.data

import android.content.res.Resources
import com.example.noteapp.R
import java.util.*

class NoteSourceImpl(private var resources: Resources) : NoteSource {
    private var dataSource: MutableList<Note> = ArrayList(7)

    override fun init(noteSourceResponse: NoteSourceResponse): NoteSource {
        val names = resources.getStringArray(R.array.notesName)
        val descriptions = resources.getStringArray(R.array.notesDescription)
        val dates = resources.getStringArray(R.array.notesDate)
        for (i in names.indices) {
            dataSource.add(Note(i, names[i], descriptions[i], dates[i], false))
        }
        noteSourceResponse.initialized(this)
        return this
    }

    override fun getNote(position: Int): Note {
        return dataSource[position]
    }

    override fun size(): Int {
        return dataSource.size
    }

    override fun deleteNote(note: Note) {
        dataSource.remove(note)
    }

    override fun updateNote(position: Int, note: Note) {
        dataSource[position] = note
    }

    override fun addNote(note: Note) {
        dataSource.add(note)
    }

}