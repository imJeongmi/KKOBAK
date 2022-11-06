package com.example.kkobak.data.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kkobak.data.room.dao.AccessTokenDao;
import com.example.kkobak.data.room.entity.AccessToken;

@Database(entities = {AccessToken.class}, version = 1, exportSchema = false)
public abstract class AccessTokenDatabase extends RoomDatabase {
    public abstract AccessTokenDao accessTokenDao();
    private static AccessTokenDatabase instance;

    public static AccessTokenDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AccessTokenDatabase.class, "kkobak-db").build();
        }
        return (instance);
    }

    public static void destroyInstance(){
        instance = null;
    }

}
