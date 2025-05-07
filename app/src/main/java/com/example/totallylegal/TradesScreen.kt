package com.example.totallylegal

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun TradesScreen() {
    val coroutineScope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }

    // Cache the last known good data
    var cachedTradeList by remember { mutableStateOf<List<List<String>>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    fun fetchTrades() {
        coroutineScope.launch {
            try {
                val latestTrades = ModernTradeAPI().fetchLatestTradesData()
                cachedTradeList = latestTrades
            } catch (e: Exception) {
                Log.e("TradesScreen", "Error fetching trades: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    // Fetch data when the screen loads
    LaunchedEffect(Unit) {
        fetchTrades()
    }

    val filteredList = cachedTradeList.filter {
        it.getOrNull(5)?.contains(searchQuery, ignoreCase = true) == true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search by Ticker") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            if (cachedTradeList.isEmpty() && isLoading) {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.connect),
                            contentDescription = "Connecting To Server..."
                        )
                        Text(
                            text = "Connecting To Server...",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Thin,
                            color = Color.White
                        )
                    }
                }
            } else if (filteredList.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No trades found.",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Thin,
                            color = Color.White
                        )
                    }
                }
            } else {
                items(filteredList.size) { index ->
                    val ref = filteredList[index]
                    TradeBox(
                        ref.getOrNull(0) ?: "",
                        ref.getOrNull(1) ?: "",
                        ref.getOrNull(2) ?: "",
                        ref.getOrNull(3) ?: "",
                        ref.getOrNull(4) ?: "",
                        ref.getOrNull(5) ?: "",
                        ref.getOrNull(6) ?: "",
                        ref.getOrNull(7) ?: "",
                        ref.getOrNull(8) ?: "",
                        ref.getOrNull(9) ?: "",
                        ref.getOrNull(10) ?: "",
                        ref.getOrNull(11) ?: ""
                    )
                }
            }
        }
    }
}
