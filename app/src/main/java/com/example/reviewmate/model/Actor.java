package com.example.reviewmate.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "actors")
public class Actor {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "actor_id")
    private Integer actorId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "bio_text")
    private String bioText;

    public Actor(int i, String timRobbins, String s) {
    }

    public Actor(String name, String bioText) {
        this.name = name;
        this.bioText = bioText;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
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
        return "Actor{" +
                "actorId=" + actorId +
                ", name='" + name + '\'' +
                ", bioText='" + bioText + '\'' +
                '}';
    }
}
