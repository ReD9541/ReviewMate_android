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

@Database(entities = {User.class, Actor.class, Director.class, Movie.class, MovieActor.class, MovieDirector.class, MoviesWatched.class, Review.class, Userinfo.class, Watchlist.class}, version = 2, exportSchema = false)
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

    private static final int NUMBER_OF_THREADS = 4;

    // Creating an instance of our Database
    private static volatile ReviewMateRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

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

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
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

        databaseWriteExecutor.execute(() -> {
            UserDAO userDAO = instance.userDAO();
            userDAO.insert(new User("admin", "admin", "admin@gmail.com"));
            userDAO.insert(new User("ritesh", "1234", "ritesh@gmail.com"));
            userDAO.insert(new User("user", "user", "user@gmail.com"));
            userDAO.insert(new User("john", "password", "john@gmail.com"));

            UserinfoDAO userinfoDAO = instance.userinfoDAO();
            userinfoDAO.insert(new Userinfo(1, "John", "Doe", "admin", "USA", "123 Main St", "Avid movie watcher.", "2024-11-11", "https://i.ibb.co/bW4k5v0/default.png"));
            userinfoDAO.insert(new Userinfo(2, "Ritesh", "Dhungel", "ritesh", "Nepal", "456 Elm St", "Loves action movies.", "2024-11-10", "https://i.ibb.co/bW4k5v0/default.png"));
            userinfoDAO.insert(new Userinfo(3, "Admin", "User", "admin", "USA", "789 Oak St", "Administrator of the site.", "2024-11-09", "https://i.ibb.co/bW4k5v0/default.png"));

            ActorDAO actorDAO = instance.actorDAO();
            actorDAO.insert(new Actor("Tim Robbins", "American actor known for \"The Shawshank Redemption\"."));
            actorDAO.insert(new Actor("Morgan Freeman", "American actor known for his deep voice and roles in \"The Shawshank Redemption\"."));
            actorDAO.insert(new Actor("Marlon Brando", "American actor and film director known for \"The Godfather\"."));
            actorDAO.insert(new Actor("Leonardo DiCaprio", "Famous for Titanic, Inception, and The Revenant."));
            actorDAO.insert(new Actor("Kate Winslet", "Known for Titanic and Eternal Sunshine of the Spotless Mind."));
            actorDAO.insert(new Actor("Christian Bale", "Famous for The Dark Knight trilogy and American Psycho."));
            actorDAO.insert(new Actor("Heath Ledger", "Known for his iconic role as the Joker in The Dark Knight."));
            actorDAO.insert(new Actor("Tom Hanks", "Famous for Forrest Gump, Cast Away, and Saving Private Ryan."));
            actorDAO.insert(new Actor("Matt Damon", "Known for The Martian, Good Will Hunting, and Jason Bourne."));
            actorDAO.insert(new Actor("Meryl Streep", "One of the greatest actresses of all time, known for multiple Oscar-winning performances."));
            actorDAO.insert(new Actor("Scarlett Johansson", "Known for Black Widow in the Marvel Cinematic Universe."));

            DirectorDAO directorDAO = instance.directorDAO();
            directorDAO.insert(new Director("Frank Darabont", "American director known for \"The Shawshank Redemption\"."));
            directorDAO.insert(new Director("Francis Ford Coppola", "American director known for \"The Godfather\"."));
            directorDAO.insert(new Director("Christopher Nolan", "British-American director known for \"Inception\" and \"The Dark Knight\"."));
            directorDAO.insert(new Director("Steven Spielberg", "Famous for Jurassic Park, E.T., and Schindler's List."));
            directorDAO.insert(new Director("James Cameron", "Famous for Titanic, Avatar, and Terminator 2."));
            directorDAO.insert(new Director("Quentin Tarantino", "Known for Pulp Fiction, Django Unchained, and Kill Bill."));
            directorDAO.insert(new Director("Martin Scorsese", "Known for The Wolf of Wall Street, Goodfellas, and Taxi Driver."));
            directorDAO.insert(new Director("Ridley Scott", "Famous for Gladiator, Alien, and Blade Runner."));

            MovieDAO movieDAO = instance.movieDAO();
            movieDAO.insert(new Movie("The Shawshank Redemption", "1994-09-23", "Drama", 142, 9.3f, 9.5f, "Two imprisoned men bond over a number of years.", "Frank Darabont", "Tim Robbins, Morgan Freeman", "English", "USA", "https://i.ibb.co/M6WSxsy/The-Shawshank-Redemption.jpg", "https://www.youtube.com/embed/6hB3S9bIaco", "R", 25000000L, 28341469L));
            movieDAO.insert(new Movie("The Godfather", "1972-03-24", "Crime, Drama", 175, 9.2f, 9.4f, "An organized crime dynasty's aging patriarch transfers control of his clandestine empire to his reluctant son.", "Francis Ford Coppola", "Marlon Brando, Al Pacino", "English", "USA", "https://i.ibb.co/j6VXFdr/The-Godfather.jpg", "https://www.youtube.com/embed/sY1S34973zA", "R", 6000000L, 134966411L));
            movieDAO.insert(new Movie("Titanic", "1997-12-19", "Romance, Drama", 195, 7.8f, 8.5f, "A love story set on the ill-fated Titanic.", "James Cameron", "Leonardo DiCaprio, Kate Winslet", "English", "USA", "https://i.ibb.co/ZYW3VTp/Titanic.jpg", "https://www.youtube.com/embed/kVrqfYjkTdQ", "PG-13", 200000000L, 2187463944L));
            movieDAO.insert(new Movie("Inception", "2010-07-16", "Action, Sci-Fi", 148, 8.8f, 9.1f, "A thief who steals corporate secrets through the use of dream-sharing technology.", "Christopher Nolan", "Leonardo DiCaprio, Joseph Gordon-Levitt", "English", "USA", "https://i.ibb.co/wSg14TC/Inception.jpg", "https://www.youtube.com/embed/YoHD9XEInc0", "PG-13", 160000000L, 836848102L));
            movieDAO.insert(new Movie("The Dark Knight", "2008-07-18", "Action, Crime", 152, 9.0f, 9.2f, "When the menace known as the Joker wreaks havoc.", "Christopher Nolan", "Christian Bale, Heath Ledger", "English", "USA", "https://i.ibb.co/vB5d5Wq/The-Dark-Knight.jpg", "https://www.youtube.com/embed/EXeTwQWrcwY", "PG-13", 185000000L, 1004558444L));

            MovieActorDAO movieActorDAO = instance.movieActorDAO();
            movieActorDAO.insert(new MovieActor(1, 1));
            movieActorDAO.insert(new MovieActor(1, 2));
            movieActorDAO.insert(new MovieActor(2, 3));
            movieActorDAO.insert(new MovieActor(3, 4));
            movieActorDAO.insert(new MovieActor(4, 5));

            MovieDirectorDAO movieDirectorDAO = instance.movieDirectorDAO();
            movieDirectorDAO.insert(new MovieDirector(1, 1));
            movieDirectorDAO.insert(new MovieDirector(2, 2));
            movieDirectorDAO.insert(new MovieDirector(3, 3));
            movieDirectorDAO.insert(new MovieDirector(4, 4));

            MoviesWatchedDAO moviesWatchedDAO = instance.moviesWatchedDAO();
            moviesWatchedDAO.insert(new MoviesWatched(1, 1, "2024-11-10"));
            moviesWatchedDAO.insert(new MoviesWatched(2, 2, "2024-11-09"));
            moviesWatchedDAO.insert(new MoviesWatched(1, 2, "2024-11-08"));

            ReviewsDAO reviewsDAO = instance.reviewsDAO();
            reviewsDAO.insertReview(new Review(1, 1, 5, "This is one of the best movies", "2024-11-10"));
            reviewsDAO.insertReview(new Review(2, 2, 4, "Nice one!", "2024-11-09"));
            reviewsDAO.insertReview(new Review(3, 2, 5, "Outstanding movie!", "2024-11-08"));

            WatchlistDAO watchlistDAO = instance.watchlistDAO();
            watchlistDAO.insert(new Watchlist(1, 1, "2024-11-12"));
            watchlistDAO.insert(new Watchlist(2, 2, "2024-11-13"));
            watchlistDAO.insert(new Watchlist(3, 1, "2024-11-14"));
        });

    }
}
