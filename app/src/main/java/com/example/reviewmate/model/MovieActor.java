package com.example.reviewmate.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "movie_actors",
        foreignKeys = {
                @ForeignKey(entity = Movie.class,
                        parentColumns = "movie_id",
                        childColumns = "movie_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Actor.class,
                        parentColumns = "actor_id",
                        childColumns = "actor_id",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index(value = "movie_id"), @Index(value = "actor_id")}
)
public class MovieActor implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "movie_id")
    @NonNull
    private Integer movieId;

    @ColumnInfo(name = "actor_id")
    @NonNull
    private Integer actorId;

    @Ignore
    public MovieActor() {
    }

    @Ignore
    public MovieActor(@NonNull Integer movieId, @NonNull Integer actorId) {
        this.movieId = movieId;
        this.actorId = actorId;
    }

    public MovieActor(Integer id, @NonNull Integer movieId, @NonNull Integer actorId) {
        this.id = id;
        this.movieId = movieId;
        this.actorId = actorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(@NonNull Integer actorId) {
        this.actorId = actorId;
    }

    @NonNull
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(@NonNull Integer movieId) {
        this.movieId = movieId;
    }
}
