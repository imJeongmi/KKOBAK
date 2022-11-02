package com.example.kkobak.data.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.kkobak.data.room.entity.AccessToken;

import java.util.List;

@Dao
public interface AccessTokenDao {
    @Insert
    void insert(AccessToken accessToken);

    @Delete
    void delete(AccessToken accessToken);

    @Query("SELECT * FROM AccessToken")
    List<AccessToken> getAll();

    @Query("DELETE FROM AccessToken")
    void deleteAll();
}
