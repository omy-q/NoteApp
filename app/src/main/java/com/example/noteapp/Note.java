package com.example.noteapp;

public class Note {

    private String noteName;
    private String noteDate;
    private String noteDescription;

    public Note (String noteName, String noteDescription, String noteDate){
        this.noteName = noteName;
        this.noteDescription = noteDescription;
        this.noteDate = noteDate;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }
}
