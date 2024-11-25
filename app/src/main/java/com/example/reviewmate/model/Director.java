package com.example.reviewmate.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "directors")
public class Director implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "director_id")
    private Integer directorId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "bio_text")
    private String bioText;
    @Ignore
    public Director(int directorId, String name, String bioText) {
    }

    public Director(String name, String bioText) {
        this.name = name;
        this.bioText = bioText;
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBioText() {
        return bioText;
    }

    public void setBioText(String bioText) {
        this.bioText = bioText;
    }

    @Override
    public String toString() {
        return "Director{" +
                "directorId=" + directorId +
                ", name='" + name + '\'' +
                ", bioText='" + bioText + '\'' +
                '}';
    }
}
