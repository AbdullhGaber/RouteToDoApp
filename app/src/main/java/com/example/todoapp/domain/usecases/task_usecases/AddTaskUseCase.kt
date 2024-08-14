package com.example.todoapp.domain.usecases.task_usecases

import com.example.todoapp.data.models.Task
import com.example.todoapp.domain.repositories.TaskRepository

class AddTaskUseCase(
    val mTaskRepository: TaskRepository
) {
    suspend operator fun invoke(task : Task){
        mTaskRepository.addTask(task)
    }
}