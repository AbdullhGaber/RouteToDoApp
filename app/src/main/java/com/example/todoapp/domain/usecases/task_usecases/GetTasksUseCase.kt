package com.example.todoapp.domain.usecases.task_usecases

import com.example.todoapp.data.models.Task
import com.example.todoapp.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasksUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke() : Flow<List<Task>> {
        return taskRepository.getTasks()
    }
}