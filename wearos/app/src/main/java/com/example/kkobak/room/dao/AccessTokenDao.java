package com.example.kkobak.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.kkobak.room.db.AccessToken;

import java.util.List;

@Dao
public interface AccessTokenDao {

    @Insert
    void setInsertToken(AccessToken token);

    @Query("delete from AccessToken where id=:no")
    void setDeleteToken(int no);

    @Query("select * from AccessToken")
    List<AccessToken> getTokenAll();

}
