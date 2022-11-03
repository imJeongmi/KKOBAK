package com.example.kkobak.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.kkobak.room.data.Todo;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void setInsertTodo(Todo todo);

    @Query("update Todo set done=:d where id=:id")
    void changeTodoStatus(int id, boolean d);

    @Query("delete from Todo where id=:id")
    void deleteTodo(int id);

    @Query("select * from Todo")
    List<Todo> getListAll();
}
