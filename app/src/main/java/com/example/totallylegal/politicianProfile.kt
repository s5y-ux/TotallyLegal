package com.example.totallylegal
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController

/*@Composable
fun TradeItem(trade: Map<String, Any>) {
    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(text = "Representative: ${trade["representative"]}")
        Text(text = "Ticker: ${trade["ticker"]}")
        Text(text = "Amount: ${trade["amount"]}")
        Text(text = "Type: ${trade["type"]}")
    }
}*/

    @Composable
    fun PoliticianScreen(navController: NavController, politicianId: String?) {
        val trades = remember { mutableStateOf<Map<String, Any>?>(null) }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(politicianId) {
            coroutineScope.launch {
                trades.value = politicianId?.let { ModernTradeAPI().fetchPoliticianData(it) }
                Log.d("Modern", trades.value.toString())
            }
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_back_arrow_white),
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Politician Profile",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (trades.value == null) {
                CircularProgressIndicator()
            } else {
                LazyColumn {
                    trades.value?.let { trade ->
                        trade.forEach { (key, value) ->
                            item {
                                Text(
                                    text = key,
                                    style = MaterialTheme.typography.headlineMedium,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = value.toString(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )

                            }
                        }
                    }
                }
            }
        }
    }
