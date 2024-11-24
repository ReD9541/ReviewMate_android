package com.example.reviewmate.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.reviewmate.model.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM userlogin WHERE id = :id")
    User getUserById(int id);

    @Query("SELECT * FROM userlogin WHERE email = :email AND password = :password ")
    User getUserByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM userlogin")
    List<User> getAllUsers();
}
