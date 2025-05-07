package com.example.totallylegal

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
    val cachedTradeList = rememberSaveable { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    val cachedNewList = rememberSaveable { mutableStateOf<Map<String, List<String>>>(emptyMap()) }
    val starred = rememberSaveable { mutableStateOf(mutableSetOf<String>()) }
    val coroutineScope = rememberCoroutineScope()
    var searchQuery by rememberSaveable { mutableStateOf("") }

    // Only fetch if cache is empty
    LaunchedEffect(Unit) {
        if (cachedTradeList.value.isEmpty() && cachedNewList.value.isEmpty()) {
            coroutineScope.launch {
                cachedTradeList.value = TradeAPI().fetchTradeData()
                cachedNewList.value = ModernTradeAPI().fetchTradeData()
                Log.d("Size", cachedTradeList.value.size.toString())
                Log.d("Modern", cachedNewList.value.toString())
            }
        }
    }

    val filteredAndSorted = cachedNewList.value
        .filterKeys { it.contains(searchQuery, ignoreCase = true) }
        .toList()
        .sortedWith(compareByDescending<Pair<String, List<String>>> { (name, _) ->
            name in starred.value
        }.thenBy { it.first })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Politicians") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            singleLine = true
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (cachedTradeList.value.isEmpty()) {
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
                items(filteredAndSorted) { (name, data) ->
                    PoliticianBox(
                        navController = navController,
                        name = name,
                        state = data.getOrNull(0).orEmpty(),
                        party = data.getOrNull(1).orEmpty(),
                        isStarred = name in starred.value,
                        onStarToggle = {
                            if (name in starred.value) {
                                starred.value.remove(name)
                            } else {
                                starred.value.add(name)
                            }
                        }
                    )
                }
            }
        }
    }
}
