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
    val coroutineScope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }

    // Fetch API data when the screen is loaded
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            tradeList.value = TradeAPI().fetchTradeData()
            Log.d("Size", tradeList.value.size.toString())
        }
    }

    val filteredList = tradeList.value.filter {
        it["ticker"].toString().contains(searchQuery, ignoreCase = true)
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
            if (tradeList.value.isEmpty()) {
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
                        name = ref["representative"].toString(),
                        ticker = ref["ticker"].toString(),
                        amount = ref["amount"].toString(),
                        type = ref["type"].toString()
                    )
                }
            }
        }
    }
}
