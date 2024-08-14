package com.example.todoapp.data.repositories

import com.example.todoapp.data.local.dao.TaskDao
import com.example.todoapp.data.models.Task
import com.example.todoapp.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(val mTaskDao : TaskDao) : TaskRepository {
    override fun getTasks(): Flow<List<Task>> {
        return mTaskDao.getTasks()
    }

    override suspend fun deleteTask(task: Task) {
         mTaskDao.deleteTask(task)
    }

    override suspend fun addTask(task: Task) {
        mTaskDao.addTask(task)
    }

    override suspend fun editTask(task: Task) {
        mTaskDao.editTask(task)
    }

    override fun getTasksByDate(date: Long): Flow<List<Task>> {
        return mTaskDao.getTasksByDate(date)
    }
}