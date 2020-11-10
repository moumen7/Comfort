package com.example.myapplication.Note;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")

public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String priority;
    @ColumnInfo(name = "bArray",typeAffinity = ColumnInfo.BLOB)
    public byte[] bArray;

    public Note(String title, String description,byte[] bArray, String priority) {
        this.title = title;
        this.description = description;
        this.bArray = bArray;
        this.priority = priority;
    }

    @Ignore
    public Note(String title, String description) {
        this.title = title;
        this.description = description;

    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public byte[] getbArray() {
        return bArray;
    }

    public void setbArray(byte[] bArray) {
        this.bArray = bArray;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
