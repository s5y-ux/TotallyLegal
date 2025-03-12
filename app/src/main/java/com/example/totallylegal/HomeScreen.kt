package com.example.totallylegal

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    val tradeList = remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    // Fetch API data when the screen is loaded
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            tradeList.value = TradeAPI().fetchTradeData()
            Log.d("Size", tradeList.value.size.toString())
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if(tradeList.value.size == 0){
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
        items(tradeList.value.size) { tradeItem ->
            val ref = tradeList.value.get(tradeItem)
            TradeBox(
                ref.getValue("representative").toString(),
                ref.getValue("ticker").toString(),
                ref.getValue("amount").toString(),
                ref.getValue("type").toString()
            )
        }
    }}
}