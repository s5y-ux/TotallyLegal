package com.example.totallylegal
import PoliticianTrades
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
    val trades = remember { mutableStateOf<PoliticianTrades?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(politicianId) {
        coroutineScope.launch {
            trades.value = politicianId?.let { ModernTradeAPI().fetchPoliticianData(it) }
            Log.d("Modern", trades.value.toString())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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
            val politicianData = trades.value!!

            Text(
                text = "${politicianData.politician} (${politicianData.party})",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn {
                items(politicianData.tradesData.size) { trade ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Ticker: ${politicianData.tradesData.get(trade).ticker}", style = MaterialTheme.typography.bodyLarge)
                            Text(text = "Date: ${politicianData.tradesData.get(trade).date}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Delay: ${politicianData.tradesData.get(trade).delay}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Type: ${politicianData.tradesData.get(trade).type}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Amount: ${politicianData.tradesData.get(trade).amount}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

