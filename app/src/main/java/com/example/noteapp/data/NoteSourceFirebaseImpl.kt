package com.example.noteapp.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class NoteSourceFirebaseImpl: NoteSource {
    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val TAG = "NotesSourceFirebaseImp"
    }

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection(NOTES_COLLECTION)

    private var notes: MutableList<Note> = ArrayList()

    override fun init(noteSourceResponse: NoteSourceResponse): NoteSource {
        collection.get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        notes = ArrayList()
                        for (document in task.result!!) {
                            val doc = document.data
                            val id = document.id
                            val note = NoteMapping.toNote(id, doc)
                            notes.add(note)
                        }
                        Log.d(TAG, "success " + notes.size + " qnt")
                        noteSourceResponse.initialized(this@NoteSourceFirebaseImpl)
                    } else {
                        Log.d(TAG, "get failed with ", task.exception)
                    }
                }
                .addOnFailureListener { e -> Log.d(TAG, "get failed with ", e) }
        return this
    }

    override fun getNote(position: Int): Note {
        return notes[position]
    }

    override fun size(): Int {
        return notes.size
    }

    override fun deleteNote(note: Note) {
        val id = note.id
        collection.document(id).delete()
        notes.remove(note)
    }

    override fun updateNote(position: Int, note: Note) {
        val id = note.id
        collection.document(id).set(NoteMapping.toDocument(note))
    }

    override fun addNote(note: Note) {
        collection.add(NoteMapping.toDocument(note)).addOnSuccessListener { documentReference -> note.id = documentReference.id }
        notes.add(note)
    }

}