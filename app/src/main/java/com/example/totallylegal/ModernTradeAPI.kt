package com.example.totallylegal

import PoliticianTrades
import ProfileClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import parseLatestTradesResponse
import parsePoliticianResponse
import parseProfileResponse
import parseTradeResponse

class ModernTradeAPI {
    suspend fun fetchTradeData(): Map<String, List<String>> {
        return withContext(Dispatchers.IO) {
            val apiUrl = "https://premium-formally-hawk.ngrok-free.app/get_politicians"
            val data = parseTradeResponse(apiUrl)
            if (data != null) {
                data
            } else {
                println("Fallback: No trade data available")
                emptyMap() // Return an empty map instead of calling `politicianPage`
            }
        }
    }

    suspend fun fetchProfileData(id: String): ProfileClass? {
        return withContext(Dispatchers.IO) {
            val apiUrl = "https://premium-formally-hawk.ngrok-free.app/get_profile?name=$id"
            val data = parseProfileResponse(apiUrl)
            if (data != null) {
                data
            } else {
                println("Fallback: No profile data available")
                null
            }
        }
    }

    suspend fun fetchPoliticianData(id: String): PoliticianTrades? {
        return withContext(Dispatchers.IO) {
            val apiUrl = "https://premium-formally-hawk.ngrok-free.app/get_trades?name=$id"
            val data = parsePoliticianResponse(apiUrl)
            if (data != null) {
                data
            } else {
                println("Fallback: No politician data available")
                null
            }
        }
    }

    suspend fun fetchLatestTradesData(): List<List<String>> {
        return withContext(Dispatchers.IO) {
            val apiUrl = "https://premium-formally-hawk.ngrok-free.app/get_latest_trades"
            val data = parseLatestTradesResponse(apiUrl)
            if (data != null) {
                data
            } else {
                println("Fallback: No latest trades data available")
                emptyList() // Return an empty list instead of calling `politicianPage`
            }
        }
    }
}