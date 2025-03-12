package com.example.totallylegal

import NewsResponse
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items


@Composable
fun NewsScreen() {
    val tradeList = remember { mutableStateOf(NewsResponse("N/A", 1, emptyList())) }

    // Fetch API data when the screen is loaded
    LaunchedEffect(Unit) {
        val response = NewsAPI().fetchTradeData()
        tradeList.value = response
        Log.d("Size", response.articles.size.toString())
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(tradeList.value.articles) { tradeItem ->
            Log.d("Source Name", tradeItem.source.name)
            NewsBox(tradeItem.source.name, tradeItem.title, tradeItem.url)
        }
    }
}
