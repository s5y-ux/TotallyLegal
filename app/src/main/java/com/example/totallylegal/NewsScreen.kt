package com.example.totallylegal

import NewsResponse
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NewsScreen(navController: NavController) {
    val tradeList = remember { mutableStateOf(NewsResponse("N/A", 1, emptyList())) }
    var searchQuery by remember { mutableStateOf("") }

    // Fetch API data when the screen is loaded
    LaunchedEffect(Unit) {
        val response = NewsAPI().fetchTradeData()
        tradeList.value = response
        Log.d("Size", response.articles.size.toString())
    }

    // Filtered news articles based on search input
    val filteredArticles = tradeList.value.articles.filter { article ->
        article.title.contains(searchQuery, ignoreCase = true) ||
                article.source.name.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search News") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
            singleLine = true
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (tradeList.value.articles.isEmpty()) {
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
                items(filteredArticles) { tradeItem ->
                    Log.d("Source Name", tradeItem.source.name)
                    NewsBox(navController, tradeItem.source.name, tradeItem.title)
                }
            }
        }
    }
}
