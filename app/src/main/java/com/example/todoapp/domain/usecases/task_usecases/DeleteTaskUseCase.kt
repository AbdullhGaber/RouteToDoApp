package com.example.todoapp.domain.usecases.task_usecases

import com.example.todoapp.data.models.Task
import com.example.todoapp.domain.repositories.TaskRepository

class DeleteTaskUseCase(
    val mTaskRepository: TaskRepository
) {
    suspend operator fun invoke(task : Task){
        mTaskRepository.deleteTask(task)
    }
}