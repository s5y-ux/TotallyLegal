package com.example.totallylegal

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    val tradeList = remember { mutableStateOf(emptyList<Map<String, Any>>()) }
    val newList = remember { mutableStateOf(mapOf<String, List<String>>()) }
    val coroutineScope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }

    // Fetch API data when the screen is loaded
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            tradeList.value = TradeAPI().fetchTradeData()
            newList.value = ModernTradeAPI().fetchTradeData() ?: emptyMap()
            Log.d("Size", tradeList.value.size.toString())
            Log.d("Modern", newList.value.toString())
        }
    }

    // Filtered trade items based on search input
    val filteredList = newList.value.filterKeys { key ->
        key.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Politicians") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
            singleLine = true
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                items(filteredList.keys.toList()) { key ->
                    val ref = filteredList[key]
                    PoliticianBox(
                        navController,
                        key,
                        ref?.get(0).toString(),
                        ref?.get(1).toString()
                    )
                }
            }
        }
    }
}
