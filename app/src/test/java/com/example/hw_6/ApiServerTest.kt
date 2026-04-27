package com.example.hw_6

import TaskApiService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.UUID

class ApiServerTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: TaskApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        apiService = TaskApiService(mockWebServer.url("/").toString())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun generateReport(name: String, status: String = "passed") {
        val resultsDir = File("build/allure-results")
        if (!resultsDir.exists()) resultsDir.mkdirs()
        val uuid = UUID.randomUUID().toString()
        resultsDir.resolve("$uuid-result.json").writeText("""
            {
                "uuid": "$uuid",
                "name": "$name",
                "status": "$status",
                "stage": "finished",
                "start": ${System.currentTimeMillis()},
                "stop": ${System.currentTimeMillis()}
            }
        """.trimIndent())
    }

    @Test
    fun testGet() {
        val mockResponse = MockResponse()
            .setBody("""[{"id": 1, "title": "Mock Task"}]""")
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val result = apiService.getTasks()

        assertEquals("""[{"id": 1, "title": "Mock Task"}]""", result)
        generateReport("API_GET_TASKS")
    }

    @Test
    fun testPost() {
        mockWebServer.enqueue(MockResponse().setResponseCode(201))

        val code = apiService.addTask("""{"title": "New API Task"}""")

        assertEquals(201, code)
        generateReport("API_POST_TASK")
    }

    @Test
    fun testDelete() {
        mockWebServer.enqueue(MockResponse().setResponseCode(204))

        val code = apiService.deleteTask(1)

        assertEquals(204, code)
        generateReport("API_DELETE_TASK")
    }
}
