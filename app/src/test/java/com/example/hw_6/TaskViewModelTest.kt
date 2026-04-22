package com.example.hw_6

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.*
import java.io.File
import java.util.UUID

class TaskViewModelTest {

    private val mockRepository: TaskRepository = mock()
    private val viewModel = TaskViewModel(mockRepository)
    val resultsDir = File("build/allure-results")
    private fun generateAllureStep(name: String) {
        val resultsDir = File("build/allure-results")
        if (!resultsDir.exists()) resultsDir.mkdirs()
        val uuid = UUID.randomUUID().toString()
        File(resultsDir, "$uuid-result.json").writeText("""
            {
                "uuid": "$uuid",
                "historyId": "${name.hashCode()}",
                "fullName": "com.example.hw_6.TaskViewModelTest.$name",
                "name": "$name",
                "status": "passed",
                "stage": "finished",
                "start": ${System.currentTimeMillis()},
                "stop": ${System.currentTimeMillis()}
            }
        """.trimIndent())
    }

    @Test
    fun `test CREATE`() {
        val taskTitle = "Buy groceries"

        viewModel.addTask(taskTitle)

        verify(mockRepository).createTask(argThat { title == taskTitle })
        generateAllureStep("test_CREATE")
    }

    @Test
    fun `test READ`() {
        val mockData = listOf(Task(1, "Task 1", false), Task(2, "Task 2", true))
        whenever(mockRepository.getAllTasks()).thenReturn(mockData)

        val result = viewModel.getTasks()

        assertEquals(2, result.size)
        assertEquals("Task 1", result[0].title)
        verify(mockRepository).getAllTasks()

        generateAllureStep("test_READ")
    }

    @Test
    fun `test UPDATE`() {
        val initialTask = Task(1, "Original Title", false)

        viewModel.toggleTaskStatus(initialTask)

        verify(mockRepository).updateTask(argThat { id == 1 && isCompleted })


        generateAllureStep("test_UPDATE")
    }

    @Test
    fun `test DELETE operation`() {
        val taskIdToDelete = 10

        viewModel.deleteTask(taskIdToDelete)

        verify(mockRepository).deleteTask(taskIdToDelete)

        generateAllureStep("test_DELETE")
    }
}
