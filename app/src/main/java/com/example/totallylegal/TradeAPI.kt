package com.example.totallylegal

import MasterAPILib
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TradeAPI {
    suspend fun fetchTradeData(): List<Map<String, Any>> {
        return withContext(Dispatchers.IO) {
            val apiUrl = "https://house-stock-watcher-data.s3-us-west-2.amazonaws.com/data/all_transactions.json"
            MasterAPILib(apiUrl) ?: emptyList()
        }
    }

    suspend fun fetchIndexedBy(key: String): Map<String, List<Map<String, Any>>> {
        val data = fetchTradeData()
        return data.groupBy { it[key]?.toString() ?: "Unknown" }
    }

    suspend fun fetchByRepresentative(repName: String): List<Map<String, Any>> {
        val indexedData = fetchIndexedBy("representative")
        return indexedData[repName] ?: emptyList()
    }
}
