import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.JsonObject
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

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

data class Source(
    val id: String?,
    val name: String
)

data class TradeEntry(
    val ticker: String,
    val date: String,
    val delay: String,
    val type: String,
    val amount: String
)

data class PoliticianTrades(
    val party: String,
    val politician: String,
    val tradesData: List<TradeEntry>
)

fun parseLatestTradesResponse(url: String): List<List<String>>? {
    return try {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                println("API Error: ${response.code}")
                return null
            }

            val jsonBody = response.body?.string()
            if (jsonBody != null) {
                val type = object : TypeToken<List<List<String>>>() {}.type
                return Gson().fromJson(jsonBody, type)
            }
            null
        }
    } catch (e: Exception) {
        println("Error fetching or parsing JSON: ${e.message}")
        null
    }
}


fun parseTradeResponse(url: String): Map<String, List<String>>? {
    return try {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                println("API Error: ${response.code}")
                return null
            }

            val jsonResponse = response.body?.string()
            jsonResponse?.let { json ->
                val type = object : TypeToken<Map<String, List<String>>>() {}.type
                return Gson().fromJson(json, type)
            }
        }
        null
    } catch (e: Exception) {
        println("Error fetching or parsing JSON: ${e.message}")
        null
    }
}

// TODO: Change This
fun parsePoliticianResponse(url: String): PoliticianTrades? {
    return try {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                println("API Error: ${response.code}")
                return null
            }

            val jsonResponse = response.body?.string()
            jsonResponse?.let { json ->
                val jsonObject = Gson().fromJson(json, JsonObject::class.java)

                val party = jsonObject["Party"]?.asString ?: return null
                val politician = jsonObject["Politician"]?.asString ?: return null

                val tradesJson = jsonObject["TradesData"]
                val tradesType = object : TypeToken<List<List<String>>>() {}.type
                val tradesList: List<List<String>> = Gson().fromJson(tradesJson, tradesType)

                val tradeEntries = tradesList.mapNotNull { trade ->
                    if (trade.size == 5) {
                        TradeEntry(
                            ticker = trade[0],
                            date = trade[1],
                            delay = trade[2],
                            type = trade[3],
                            amount = trade[4]
                        )
                    } else null
                }

                return PoliticianTrades(
                    party = party,
                    politician = politician,
                    tradesData = tradeEntries
                )
            }
        }
        null
    } catch (e: Exception) {
        println("Error fetching or parsing JSON: ${e.message}")
        null
    }
}

// End Method

// Function to parse JSON response
fun parseNewsApiResponse(url: String): NewsResponse? {
    return try {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            val jsonResponse = response.body?.string()
            jsonResponse?.let {
                Gson().fromJson(it, NewsResponse::class.java)
            }
        } else {
            println("API Error: ${response.code}")
            null
        }
    } catch (e: Exception) {
        println("Error fetching or parsing JSON: ${e.message}")
        null
    }
}
