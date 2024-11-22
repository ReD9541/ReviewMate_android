package com.example.reviewmate.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "movie_actors",
        primaryKeys = {"movie_id", "actor_id"},
        foreignKeys = {
                @ForeignKey(entity = Movie.class,
                        parentColumns = "movie_id",
                        childColumns = "movie_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Actor.class,
                        parentColumns = "actor_id",
                        childColumns = "actor_id",
                        onDelete = ForeignKey.CASCADE)
        })
public class MovieActor {

    @ColumnInfo(name = "movie_id")
    private Integer movieId;

    @ColumnInfo(name = "actor_id")
    private Integer actorId;

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public MovieActor(Integer movieId, Integer actorId) {
        this.movieId = movieId;
        this.actorId = actorId;
    }


}
