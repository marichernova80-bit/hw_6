package com.example.hw_6

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.*

class TaskViewModelTest {

    private val mockRepository: TaskRepository = mock()
    private val viewModel = TaskViewModel(mockRepository)

    @Test
    fun `test CREATE`() {
        val taskTitle = "Buy groceries"

        viewModel.addTask(taskTitle)

        verify(mockRepository).createTask(argThat { title == taskTitle })
    }

    @Test
    fun `test READ`() {
        val mockData = listOf(Task(1, "Task 1", false), Task(2, "Task 2", true))
        whenever(mockRepository.getAllTasks()).thenReturn(mockData)

        val result = viewModel.getTasks()

        assertEquals(2, result.size)
        assertEquals("Task 1", result[0].title)
        verify(mockRepository).getAllTasks()
    }

    @Test
    fun `test UPDATE`() {
        val initialTask = Task(1, "Original Title", false)

        viewModel.toggleTaskStatus(initialTask)

        verify(mockRepository).updateTask(argThat { id == 1 && isCompleted })
    }

    @Test
    fun `test DELETE operation`() {
        val taskIdToDelete = 10

        viewModel.deleteTask(taskIdToDelete)

        verify(mockRepository).deleteTask(taskIdToDelete)
    }
}
