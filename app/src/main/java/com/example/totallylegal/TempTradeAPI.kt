import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TempTradeAPI(private val apiCall: String) {

    private var apiData: String? = null

    // Fetch API data asynchronously
    suspend fun fetchApiData() {
        // Use the IO dispatcher for network calls
        withContext(Dispatchers.IO) {
            val url = URL(apiCall)
            val connection = url.openConnection() as HttpURLConnection
            try {
                connection.requestMethod = "GET"
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    apiData = reader.readText()
                    reader.close()
                }
            } finally {
                connection.disconnect()
            }
        }
    }

    // Method to return data as a String
    fun getDataString(): String? {
        return apiData
    }

    // Method to return data as a Map

}
