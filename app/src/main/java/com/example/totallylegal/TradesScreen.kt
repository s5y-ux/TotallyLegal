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
    val tradeList = remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    val newTradeList = remember { mutableStateOf<List<List<String>>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }

    // Fetch API data when the screen is loaded
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            tradeList.value = TradeAPI().fetchTradeData()
            newTradeList.value = ModernTradeAPI().fetchLatestTradesData()
            Log.d("Size", newTradeList.value.size.toString())
        }
    }

    val filteredList = newTradeList.value.filter {
        it[5].contains(searchQuery, ignoreCase = true)
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
            if (newTradeList.value.isEmpty()) {
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
            } else {
                items(filteredList.size) { index ->
                    val ref = filteredList[index]
                    TradeBox(
                        ref[0],
                        ref[1],
                        ref[2],
                        ref[3],
                        ref[4],
                        ref[5],
                        ref[6],
                        ref[7],
                        ref[8],
                        ref[9],
                        ref[10],
                        ref[11]
                    )
                }
            }
        }
    }
}
