import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun MasterAPILib(url: String): List<Map<String, Any>>? {
    return withContext(Dispatchers.IO) { // Run in background thread
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build()

        try {
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val jsonResponse = response.body?.string()
                println("Raw JSON response: $jsonResponse") // Debugging

                return@withContext jsonResponse?.let {
                    val listType = object : TypeToken<List<Map<String, Any>>>() {}.type
                    Gson().fromJson(it, listType)
                }
            } else {
                println("API Error: ${response.code}")
                return@withContext null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }
}
