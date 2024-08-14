package com.example.todoapp.domain.usecases.task_usecases

data class TaskUseCases(
    val getTasks: GetTasksUseCase,
    val deleteTask: DeleteTaskUseCase,
    val addTask: AddTaskUseCase,
    val editTask: EditTaskUseCase,
    val getTasksByDate : GetTasksByDateUseCase
)
