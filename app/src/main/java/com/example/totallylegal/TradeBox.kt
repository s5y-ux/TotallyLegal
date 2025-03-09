package com.example.totallylegal

import MasterAPILib
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TradeBox(name: String, ticker: String, amount: String, type: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Trade: $ticker",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Amount: $amount",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Type: $type",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            /*
            Whoever took the delete profile button task hit the lottery
            All you have to do is delete this Button composable
            without breaking the build. You got this!
             */
            /*Button(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        // Create the TempTradeAPI instance with the given API URL
                        val tradeAPI =
                            MasterAPILib(
                                "https://house-stock-watcher-data.s3-us-west-2.amazonaws.com/data/transaction_report_for_05_14_2023.json"
                            )

                        // Fetch the API data asynchronously
                        Log.d("Internal Map", tradeAPI.toString())

                        // Alternatively, get the data as a Map and log it
                        /*val dataMap = tradeAPI.getDataObject()
                        dataMap?.let {
                            Log.d("Parsed Data", "User: ${it.name}, Transactions: ${it.transactions}")
                        }*/
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) */
            }
        }
    }
