package com.example.kkobak.room.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kkobak.room.dao.AccessTokenDao;
import com.example.kkobak.room.dao.TodoDao;
import com.example.kkobak.room.data.AccessToken;
import com.example.kkobak.room.data.Todo;

@Database(entities = {AccessToken.class, Todo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase database;

    private static String DATABASE_NAME = "kkobak_db";

    public synchronized static AppDatabase getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()       // 스키마 버전 변경 가능
                    .allowMainThreadQueries()               // Main Thread에서 DB에 IO 가능하게 함.
                    .build();
        }
        return database;
    }

    public abstract AccessTokenDao tokenDao();
    public abstract TodoDao todoDao();
}
