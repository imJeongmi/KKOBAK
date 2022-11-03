package com.example.kkobak.room.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.kkobak.room.dao.AccessTokenDao;

@Database(entities = {com.example.kkobak.room.db.AccessToken.class}, version = 1)
public abstract class AccessTokenDatabase extends RoomDatabase {
    public abstract AccessTokenDao tokenDao();
}
