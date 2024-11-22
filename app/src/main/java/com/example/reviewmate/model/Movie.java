package com.example.reviewmate.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    private Integer movieId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "genre")
    private String genre;

    @ColumnInfo(name = "runtime")
    private Integer runtime;

    @ColumnInfo(name = "imdb_rating")
    private Float imdbRating;

    @ColumnInfo(name = "user_rating")
    private Float userRating;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "director")
    private String director;

    @ColumnInfo(name = "cast")
    private String cast;

    @ColumnInfo(name = "language")
    private String language;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "poster_url")
    private String posterUrl;

    @ColumnInfo(name = "trailer_url")
    private String trailerUrl;

    @ColumnInfo(name = "age_rating")
    private String ageRating;

    @ColumnInfo(name = "budget")
    private Long budget;

    @ColumnInfo(name = "box_office")
    private Long boxOffice;

    public Movie() {
    }

    public Long getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(Long boxOffice) {
        this.boxOffice = boxOffice;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getUserRating() {
        return userRating;
    }

    public void setUserRating(Float userRating) {
        this.userRating = userRating;
    }

    public Float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Movie(String title, String releaseDate, String genre, Integer runtime, Float imdbRating, Float userRating, String description, String director, String cast, String language, String country, String posterUrl, String trailerUrl, String ageRating, Long budget, Long boxOffice) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.runtime = runtime;
        this.imdbRating = imdbRating;
        this.userRating = userRating;
        this.description = description;
        this.director = director;
        this.cast = cast;
        this.language = language;
        this.country = country;
        this.posterUrl = posterUrl;
        this.trailerUrl = trailerUrl;
        this.ageRating = ageRating;
        this.budget = budget;
        this.boxOffice = boxOffice;
    }


}
