package com.example.totallylegal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import parseTradeResponse
import politicianPage

class ModernTradeAPI {
    suspend fun fetchTradeData(): politicianPage {
        return withContext(Dispatchers.IO) {
            val apiUrl = "http://127.0.0.1:5000/get_politicians"
            parseTradeResponse(apiUrl) ?: politicianPage(
                name = "N/A",
                quickInfo = arrayListOf()
            )
        }
    }
}