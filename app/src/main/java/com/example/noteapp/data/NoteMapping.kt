package com.example.noteapp.data

import java.util.*

object NoteMapping {

    object Fields {
        const val INDEX = "index"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val DATE = "date"
        const val FAVORITE = "favorite"
    }

    fun toNote(id: String, doc: Map<String, Any>): Note {
        val answer = Note(Integer.valueOf(doc[Fields.INDEX].toString()),
                doc[Fields.TITLE] as String,
                doc[Fields.DESCRIPTION] as String,
                doc[Fields.DATE] as String,
                doc[Fields.FAVORITE] as Boolean)
        answer.id = id
        return answer
    }

    fun toDocument(note: Note): Map<String, Any> {
        val answer: MutableMap<String, Any> = HashMap()
        answer[Fields.INDEX] = note.posIndex
        answer[Fields.TITLE] = note.noteName
        answer[Fields.DESCRIPTION] = note.noteDescription
        answer[Fields.DATE] = note.noteDate
        answer[Fields.FAVORITE] = note.isFavorite
        return answer
    }
}