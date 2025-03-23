package com.example.totallylegal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import parsePoliticianResponse
import parseTradeResponse

class ModernTradeAPI {
    suspend fun fetchTradeData(): Map<String, List<String>> {
        return withContext(Dispatchers.IO) {
            val apiUrl = "https://053d-23-243-227-235.ngrok-free.app/get_politicians"
            val data = parseTradeResponse(apiUrl)
            if (data != null) {
                data
            } else {
                println("Fallback: No trade data available")
                emptyMap() // Return an empty map instead of calling `politicianPage`
            }
        }
    }

    suspend fun fetchPoliticianData(id: String): Map<String, List<String>> {
        return withContext(Dispatchers.IO) {
            val apiUrl = "https://053d-23-243-227-235.ngrok-free.app/get_trades?name=$id"
            val data = parsePoliticianResponse(apiUrl)
            if (data != null) {
                data
            } else {
                println("Fallback: No politician data available")
                emptyMap() // Return an empty map instead of calling `politicianPage`
            }
        }
    }
}