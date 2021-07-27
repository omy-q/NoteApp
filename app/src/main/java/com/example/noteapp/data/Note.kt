package com.example.noteapp.data

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator

open class Note(
        var posIndex: Int,
        var noteName: String,
        var noteDescription: String,
        var noteDate: String,
        var isFavorite: Boolean) : Parcelable {

    lateinit var id : String

    protected constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readByte().toInt() != 0)

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(posIndex)
        dest.writeString(noteName)
        dest.writeString(noteDescription)
        dest.writeString(noteDate)
        dest.writeByte((if (isFavorite) 1 else 0).toByte())
    }

    companion object CREATOR : Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }

}