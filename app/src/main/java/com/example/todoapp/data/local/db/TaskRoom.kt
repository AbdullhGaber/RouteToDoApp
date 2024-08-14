package com.example.todoapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.data.local.dao.TaskDao
import com.example.todoapp.data.local.type_converters.TaskTypeConverter
import com.example.todoapp.data.models.Task

@Database(entities = [Task::class] , version = 3 , exportSchema = false)
@TypeConverters(TaskTypeConverter::class)
abstract class TaskRoom : RoomDatabase() {
    abstract val taskDao : TaskDao
}