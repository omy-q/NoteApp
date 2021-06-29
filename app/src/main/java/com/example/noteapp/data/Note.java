package com.example.noteapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private String noteName;
    private String noteDate;
    private String noteDescription;
    private int posIndex;

    public Note (int posIndex, String noteName, String noteDescription, String noteDate){
        this.posIndex = posIndex;
        this.noteName = noteName;
        this.noteDescription = noteDescription;
        this.noteDate = noteDate;
    }

    protected Note(Parcel in) {
        posIndex = in.readInt();
        noteName = in.readString();
        noteDescription = in.readString();
        noteDate = in.readString();
    }
    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }
        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public int getPosIndex() {
        return posIndex;
    }

    public String getNoteName() {
        return noteName;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public String getNoteDate() {
        return noteDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getPosIndex());
        dest.writeString(getNoteName());
        dest.writeString(getNoteDescription());
        dest.writeString(getNoteDate());
    }

}
