package com.example.todoapp.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title : String = "",
    val content : String = "",
    @ColumnInfo(index = true)
    val date : Long = 0,
    val status : TaskStatus = TaskStatus.PENDING
) : Parcelable

enum class TaskStatus{PENDING , COMPLETED}
