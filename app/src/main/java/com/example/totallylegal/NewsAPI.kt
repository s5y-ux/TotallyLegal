package com.example.totallylegal

import NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import parseNewsApiResponse

class NewsAPI {
    suspend fun fetchTradeData(): NewsResponse {
        return withContext(Dispatchers.IO) {
            val apiUrl = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=3e80b93ae48b47cd9b83f4dba7ff3711"
            parseNewsApiResponse(apiUrl) ?: NewsResponse(
                "N/A",
                totalResults = 0,
                articles = emptyList(),
            )
        }
    }
}