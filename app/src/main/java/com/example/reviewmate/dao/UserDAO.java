package com.example.reviewmate.dao;

import androidx.lifecycle.LiveData;
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
    long insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM userlogin WHERE id = :userId")
    LiveData<User> getUserById(int userId);

    @Query("SELECT * FROM userlogin WHERE email = :email AND password = :password")
    User getUserByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM userlogin")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM userlogin WHERE email = :email")
    LiveData<User> findByEmail(String email);

    @Query("UPDATE userinfo SET fname = :firstName, lname = :lastName, bio = :bio, address = :address WHERE user_id = :userId")
    int updateUserProfile(int userId, String firstName, String lastName, String bio, String address);

    @Query("UPDATE userlogin SET password = :newPassword WHERE id = :userId")
    int updateUserPassword(int userId, String newPassword);
}

