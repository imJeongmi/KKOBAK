package com.example.kkobak.room.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kkobak.room.dao.AccessTokenDao;

@Database(entities = {com.example.kkobak.room.db.AccessToken.class}, version = 1)
public abstract class AccessTokenDatabase extends RoomDatabase {

    private static AccessTokenDatabase database;

    private static String DATABASE_NAME = "kkobak_db";

    public synchronized static AccessTokenDatabase getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), AccessTokenDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()       // 스키마 버전 변경 가능
                    .allowMainThreadQueries()               // Main Thread에서 DB에 IO 가능하게 함.
                    .build();
        }
        return database;
    }

    public abstract AccessTokenDao tokenDao();
}
