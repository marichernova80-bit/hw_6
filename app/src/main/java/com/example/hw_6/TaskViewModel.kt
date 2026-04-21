package com.example.hw_6

class TaskViewModel(private val repository: TaskRepository) {

    fun addTask(title: String) {
        if (title.isNotBlank()) {
            repository.createTask(Task(0, title, false))
        }
    }

    fun getTasks(): List<Task> = repository.getAllTasks()

    fun toggleTaskStatus(task: Task) {
        val updatedTask = task.copy(isCompleted = !task.isCompleted)
        repository.updateTask(updatedTask)
    }

    fun deleteTask(id: Int) {
        repository.deleteTask(id)
    }
}
