package com.example.todoapp.domain.repositories

import com.example.todoapp.data.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks() : Flow<List<Task>>
    fun getTasksByDate(date : Long) : Flow<List<Task>>
    suspend fun deleteTask(task: Task)
    suspend fun addTask(task : Task)
    suspend fun editTask(task : Task)
}