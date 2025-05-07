package com.example.totallylegal

import ProfileClass
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

@Composable
fun AIAnalysisButton(profile: ProfileClass?) {
    var showDialog by remember { mutableStateOf(false) }

    Column {
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("AI Analysis")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Close")
                    }
                },
                title = { Text("AI Analysis") },
                text = {
                    if (profile != null) {
                        Text(profile.aiResponse)
                    }
                }
            )
        }
    }
}


@Composable
fun PoliticianScreen(navController: NavController, politicianId: String?) {
    var cachedProfileData by remember { mutableStateOf<ProfileClass?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(politicianId) {
        coroutineScope.launch {
            try {
                politicianId?.let {
                    cachedProfileData = ModernTradeAPI().fetchProfileData(it)
                }
            } catch (e: Exception) {
                Log.e("PoliticianScreen", "Error fetching profile: ${e.message}")
            } finally {
                isLoading = false
            }
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
            text = cachedProfileData?.name?:" This will take a while...",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if(cachedProfileData != null) {
            AIAnalysisButton(cachedProfileData)
        }

        when {
            isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            cachedProfileData == null -> {
                Text(
                    "Failed to load profile.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            else -> {
                val profile = cachedProfileData!!

                ProfileInfoSection(profile)
                Spacer(modifier = Modifier.height(16.dp))
                IssuerSectorSection(profile)
                Spacer(modifier = Modifier.height(16.dp))
                TradeDataSection(profile)
            }
        }
    }
}

@Composable
fun ProfileInfoSection(profile: ProfileClass) {
    Text("District: ${profile.district}", style = MaterialTheme.typography.headlineMedium)

    Spacer(modifier = Modifier.height(8.dp))
    Column {
        Text("Age: ${profile.age}", style = MaterialTheme.typography.bodyLarge)
        Text("Date of Birth: ${profile.dateOfBirth}", style = MaterialTheme.typography.bodyMedium)
        Text("Last Traded: ${profile.lastTraded}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun IssuerSectorSection(profile: ProfileClass) {
    Column {
        Text("Most Traded Issuers:", style = MaterialTheme.typography.titleMedium)
        profile.mostTradedIssuers.forEach { (issuer, count) ->
            Text("$issuer: $count", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Most Traded Sectors:", style = MaterialTheme.typography.titleMedium)
        profile.mostTradedSectors.forEach { (sector, count) ->
            Text("$sector: $count", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun TradeDataSection(profile: ProfileClass) {
    Text("Trade History:", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))

    LazyColumn {
        items(profile.tradeData.size) { index ->
            val trade = profile.tradeData[index]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Issuer: ${trade.getOrNull(0) ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
                    Text("Ticker: ${trade.getOrNull(1) ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
                    Text("Amount: ${trade.getOrNull(2) ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
                    Text("Date: ${trade.getOrNull(3) ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
