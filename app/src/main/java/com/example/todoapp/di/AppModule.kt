package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.local.dao.TaskDao
import com.example.todoapp.data.local.db.TaskRoom
import com.example.todoapp.data.repositories.TaskRepositoryImpl
import com.example.todoapp.domain.repositories.TaskRepository
import com.example.todoapp.domain.usecases.task_usecases.AddTaskUseCase
import com.example.todoapp.domain.usecases.task_usecases.DeleteTaskUseCase
import com.example.todoapp.domain.usecases.task_usecases.EditTaskUseCase
import com.example.todoapp.domain.usecases.task_usecases.GetTasksByDateUseCase
import com.example.todoapp.domain.usecases.task_usecases.GetTasksUseCase
import com.example.todoapp.domain.usecases.task_usecases.TaskUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getRoomDB(
        @ApplicationContext context: Context
    ) : TaskRoom {
        return Room.databaseBuilder(
            context,
            TaskRoom::class.java,
            "todo_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(
        database : TaskRoom
    ) : TaskDao{
        return database.taskDao
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        taskDao: TaskDao
    ) : TaskRepository{
        return TaskRepositoryImpl(taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(
        taskRepository: TaskRepository
    ) : TaskUseCases{
        return TaskUseCases(
            getTasks = GetTasksUseCase(taskRepository),
            deleteTask = DeleteTaskUseCase(taskRepository),
            addTask = AddTaskUseCase(taskRepository),
            editTask = EditTaskUseCase(taskRepository),
            getTasksByDate = GetTasksByDateUseCase(taskRepository)
        )
    }
}