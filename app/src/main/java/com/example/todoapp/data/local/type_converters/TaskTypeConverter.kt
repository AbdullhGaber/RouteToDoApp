package com.example.todoapp.data.local.type_converters

import androidx.room.TypeConverter
import com.example.todoapp.data.models.TaskStatus

class TaskTypeConverter {
    @TypeConverter
    fun fromTaskStatusToString(status : TaskStatus) : String {
        return when(status){
            TaskStatus.PENDING -> status.name
            TaskStatus.COMPLETED -> status.name
        }
    }

    @TypeConverter
    fun fromStringToTaskStatus(status : String) : TaskStatus{
        return when(status){
            TaskStatus.PENDING.name -> TaskStatus.PENDING
            else -> TaskStatus.COMPLETED
        }
    }
}