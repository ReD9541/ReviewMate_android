package com.example.reviewmate.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.reviewmate.model.Userinfo;

import java.util.List;

@Dao
public interface UserinfoDAO {

    @Insert
    void insert(Userinfo userinfo);

    @Update
    void update(Userinfo userinfo);

    @Delete
    void delete(Userinfo userinfo);

    @Query("SELECT * FROM userinfo WHERE user_id = :userId")
    Userinfo getUserinfoByUserId(int userId);

    @Query("SELECT * FROM userinfo")
    List<Userinfo> getAllUserinfo();
}
