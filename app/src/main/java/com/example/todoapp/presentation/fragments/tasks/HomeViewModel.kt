package com.example.todoapp.presentation.fragments.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.Task
import com.example.todoapp.domain.usecases.task_usecases.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val mTaskUseCases: TaskUseCases
): ViewModel() {
    private val tasks = mTaskUseCases.getTasks()

    fun getTasks() = tasks

    fun deleteTask(task : Task){
        viewModelScope.launch(Dispatchers.IO){
            mTaskUseCases.deleteTask(task)
        }
    }

    fun addTask(task : Task){
        viewModelScope.launch(Dispatchers.IO){
            mTaskUseCases.addTask(task)
        }
    }

    fun editTask(task : Task){
        viewModelScope.launch(Dispatchers.IO) {
            mTaskUseCases.editTask(task)
        }
    }

    fun getTasksByDate(date : Long) : Flow<List<Task>>{
        return mTaskUseCases.getTasksByDate(date)
    }
}