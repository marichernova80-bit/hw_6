package com.example.hw_6

interface TaskRepository {
    fun createTask(task: Task)
    fun getAllTasks(): List<Task>
    fun updateTask(task: Task)
    fun deleteTask(id: Int)
}
