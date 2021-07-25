package com.example.noteapp.data;


import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class NoteMapping {
    public static class Fields {
        public final static String INDEX = "index";
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
        public final static String DATE = "date";
        public final static String FAVORITE = "favorite";
    }

    public static Note toNote(String id, Map<String, Object> doc) {
        Note answer = new Note(Integer.valueOf(doc.get(Fields.INDEX).toString()) ,
                (String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DESCRIPTION),
                (String) doc.get(Fields.DATE),
                (boolean) doc.get(Fields.FAVORITE));
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(Note note) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.INDEX, note.getPosIndex());
        answer.put(Fields.TITLE, note.getNoteName());
        answer.put(Fields.DESCRIPTION, note.getNoteDescription());
        answer.put(Fields.DATE, note.getNoteDate());
        answer.put(Fields.FAVORITE, note.getNoteFavorite());
        return answer;
    }
}
