package com.example.noteapp.data

interface NoteSource {
    fun init(noteSourceResponse: NoteSourceResponse): NoteSource
    fun getNote(position: Int): Note
    fun size(): Int
    fun deleteNote(note: Note)
    fun updateNote(position: Int, note: Note)
    fun addNote(note: Note)
}