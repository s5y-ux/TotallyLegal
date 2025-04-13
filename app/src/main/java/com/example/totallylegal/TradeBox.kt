package com.example.totallylegal

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Entry(type: String, holder: String){
    Text(
        text = "$type: $holder",
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun TradeBox(
    name: String,
    party: String,
    seat: String,
    state: String,
    companyName: String,
    ticker: String,
    disclosureTime: String,
    tradeDate: String,
    daysUntilDisclosure: String,
    tradeType: String,
    tradeAmount: String,
    tickerPrice: String
) {
    val context = LocalContext.current
    val cleanTicker = ticker.removeSuffix(":US")
    val yahooFinanceUrl = "https://finance.yahoo.com/quote/$cleanTicker"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = companyName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Entry("Ticker", cleanTicker)
            Entry("Trade Date", tradeDate)
            Entry("Days Until Disclosure", daysUntilDisclosure)
            Entry("Trade Type", tradeType)
            Entry("Trade Amount", tradeAmount)
            Entry("Ticker Price", tickerPrice)
            Entry("Politician Name", name)

            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(yahooFinanceUrl))
                context.startActivity(intent)
            }) {
                Text(text = "See Stock")
            }
        }
    }
}
