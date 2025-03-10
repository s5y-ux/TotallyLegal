/*package com.example.totallylegal

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
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


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
        if(tradeList.value.articles.size == 0){
            item{
                Text(
                    text = "Connecting To Server...",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Thin,
                    color = Color.White
                )
            }
        } else {
        items(tradeList.value.articles) { tradeItem ->
            Log.d("Source Name", tradeItem.source.name)
            NewsBox(tradeItem.source.name, tradeItem.title)
        }
    }}
}*/
package com.example.totallylegal

import NewsResponse
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NewsScreen(navController: NavController) {
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
        if(tradeList.value.articles.isEmpty()) {
            item{
                Column(modifier = Modifier.fillMaxSize(),
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
            items(tradeList.value.articles) { tradeItem ->
                Log.d("Source Name", tradeItem.source.name)
                NewsBox(navController, tradeItem.source.name, tradeItem.title)
            }
        }
    }
}
