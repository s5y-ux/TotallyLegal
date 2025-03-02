package com.example.totallylegal

import MasterAPILib
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsAPI {
    suspend fun fetchTradeData(): List<Map<String, Any>> {
        return withContext(Dispatchers.IO) {
            val apiUrl = "https://house-stock-watcher-data.s3-us-west-2.amazonaws.com/data/all_transactions.json"
            MasterAPILib(apiUrl) ?: emptyList()
        }
    }
}