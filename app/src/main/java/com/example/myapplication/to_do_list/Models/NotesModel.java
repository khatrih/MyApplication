package com.example.myapplication.to_do_list.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class NotesModel implements Parcelable {
    private String noteTitle;
    private String noteContent;
    private String noteId;

    public NotesModel() {
    }

    public NotesModel(String noteTitle, String noteContent) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    protected NotesModel(Parcel in) {
        noteTitle = in.readString();
        noteContent = in.readString();
        noteId = in.readString();
    }

    public static final Creator<NotesModel> CREATOR = new Creator<NotesModel>() {
        @Override
        public NotesModel createFromParcel(Parcel in) {
            return new NotesModel(in);
        }

        @Override
        public NotesModel[] newArray(int size) {
            return new NotesModel[size];
        }
    };

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteTitle);
        dest.writeString(noteContent);
        dest.writeString(noteId);
    }
}
