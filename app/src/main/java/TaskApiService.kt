import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class TaskApiService(private val baseUrl: String) {
    private val client = OkHttpClient()

    fun getTasks(): String? {
        val request = Request.Builder().url("$baseUrl/tasks").build()
        return client.newCall(request).execute().body?.string()
    }

    fun addTask(json: String): Int {
        val request = Request.Builder()
            .url("$baseUrl/tasks")
            .post(json.toRequestBody())
            .build()
        return client.newCall(request).execute().code
    }

    fun deleteTask(id: Int): Int {
        val request = Request.Builder()
            .url("$baseUrl/tasks/$id")
            .delete()
            .build()
        return client.newCall(request).execute().code
    }
}
