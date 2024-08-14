package com.example.todoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun addTask(task : Task)

    @Update
    suspend fun editTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks")
    fun getTasks() : Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE date = :date")
    fun getTasksByDate(date : Long) : Flow<List<Task>>
}