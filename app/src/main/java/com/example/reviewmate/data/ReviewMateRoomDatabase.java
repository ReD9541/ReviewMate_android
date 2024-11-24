package com.example.reviewmate.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.reviewmate.dao.*;
import com.example.reviewmate.model.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Actor.class, Director.class, Movie.class, MovieActor.class, MovieDirector.class, MoviesWatched.class, Review.class, Userinfo.class, Watchlist.class}, version = 1, exportSchema = false)
public abstract class ReviewMateRoomDatabase extends RoomDatabase {

    // Declare all DAOs
    public abstract UserDAO userDAO();
    public abstract ActorDAO actorDAO();
    public abstract DirectorDAO directorDAO();
    public abstract MovieDAO movieDAO();
    public abstract MovieActorDAO movieActorDAO();
    public abstract MovieDirectorDAO movieDirectorDAO();
    public abstract MoviesWatchedDAO moviesWatchedDAO();
    public abstract ReviewsDAO reviewsDAO();
    public abstract UserinfoDAO userinfoDAO();
    public abstract WatchlistDAO watchlistDAO();

    private static final int NUMBERS_OF_THREADS = 4;

    // Creating an instance of our Database
    private static volatile ReviewMateRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);

    public static ReviewMateRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ReviewMateRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ReviewMateRoomDatabase.class, "reviewmate_db")
                            .addCallback(roomCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            populateInitialData(INSTANCE);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static void populateInitialData(ReviewMateRoomDatabase instance) {
        ReviewMateRoomDatabase.databaseWriteExecutor.execute(
                () -> {
                    UserDAO userDAO = instance.userDAO();
                    userDAO.insert(new User("admin", "admin", "admin@example.com"));
                    userDAO.insert(new User("ritesh", "1234", "ritesh@example.com"));
                    userDAO.insert(new User("user", "user", "user@example.com"));
                    userDAO.insert(new User("john", "password", "john@example.com"));

                    ActorDAO actorDAO = instance.actorDAO();
                    actorDAO.insert(new Actor("Tim Robbins", "American actor known for \"The Shawshank Redemption\"."));
                    actorDAO.insert(new Actor("Morgan Freeman", "American actor known for his deep voice and roles in \"The Shawshank Redemption\"."));
                    actorDAO.insert(new Actor("Marlon Brando", "American actor and film director known for \"The Godfather\"."));

                    DirectorDAO directorDAO = instance.directorDAO();
                    directorDAO.insert(new Director("Frank Darabont", "American director known for \"The Shawshank Redemption\"."));
                    directorDAO.insert(new Director("Francis Ford Coppola", "American director known for \"The Godfather\"."));
                    directorDAO.insert(new Director("Christopher Nolan", "British-American director known for \"Inception\" and \"The Dark Knight\"."));

                    MovieDAO movieDAO = instance.movieDAO();
                    movieDAO.insert(new Movie("The Shawshank Redemption", "1994-09-23", "Drama", 142, 9.3f, 9.5f, "Two imprisoned men bond over a number of years.", "Frank Darabont", "Tim Robbins, Morgan Freeman", "English", "USA", "https://i.ibb.co/M6WSxsy/The-Shawshank-Redemption.jpg", "https://www.youtube.com/embed/6hB3S9bIaco", "R", 25000000L, 28341469L));
                    movieDAO.insert(new Movie("The Godfather", "1972-03-24", "Crime, Drama", 175, 9.2f, 9.4f, "An organized crime dynasty's aging patriarch transfers control of his clandestine empire to his reluctant son.", "Francis Ford Coppola", "Marlon Brando, Al Pacino", "English", "USA", "https://i.ibb.co/j6VXFdr/The-Godfather.jpg", "https://www.youtube.com/embed/sY1S34973zA", "R", 6000000L, 134966411L));

                    MovieActorDAO movieActorDAO = instance.movieActorDAO();
                    movieActorDAO.insert(new MovieActor(1, 1));
                    movieActorDAO.insert(new MovieActor(1, 2));
                    movieActorDAO.insert(new MovieActor(2, 3));

                    MovieDirectorDAO movieDirectorDAO = instance.movieDirectorDAO();
                    movieDirectorDAO.insert(new MovieDirector(1, 1));
                    movieDirectorDAO.insert(new MovieDirector(2, 2));
                    movieDirectorDAO.insert(new MovieDirector(2, 3));

                    MoviesWatchedDAO moviesWatchedDAO = instance.moviesWatchedDAO();
                    moviesWatchedDAO.insert(new MoviesWatched(1, 1, "2024-11-10"));
                    moviesWatchedDAO.insert(new MoviesWatched(2, 2, "2024-11-09"));
                    moviesWatchedDAO.insert(new MoviesWatched(1, 3, "2024-11-08"));

                    ReviewsDAO reviewsDAO = instance.reviewsDAO();
                    reviewsDAO.insert(new Review(1, 1, 1, "This is one of the best movies", "2024-11-10"));
                    reviewsDAO.insert(new Review(2, 3, 2, "Nice one!", "2024-11-09"));
                    reviewsDAO.insert(new Review(3, 2, 1, "Outstanding movie!", "2024-11-08"));

                    UserinfoDAO userinfoDAO = instance.userinfoDAO();
                    userinfoDAO.insert(new Userinfo(1, "John", "Doe", "USA", "123 Main St", "Avid movie watcher.", "2024-11-11", "https://i.ibb.co/YyL4T0v/profile-placeholder.png"));
                    userinfoDAO.insert(new Userinfo(2, "Ritesh", "Patel", "India", "456 Elm St", "Loves action movies.", "2024-11-10", "https://i.ibb.co/YyL4T0v/profile-placeholder.png"));
                    userinfoDAO.insert(new Userinfo(3, "Admin", "User", "USA", "789 Oak St", "Administrator of the site.", "2024-11-09", "https://i.ibb.co/YyL4T0v/profile-placeholder.png"));

                    WatchlistDAO watchlistDAO = instance.watchlistDAO();
                    watchlistDAO.insert(new Watchlist(1, 1, "2024-11-12"));
                    watchlistDAO.insert(new Watchlist(2, 2, "2024-11-13"));
                    watchlistDAO.insert(new Watchlist(3, 1, "2024-11-14"));
                });
    }
}
