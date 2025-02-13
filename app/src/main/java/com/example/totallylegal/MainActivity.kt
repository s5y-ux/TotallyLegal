package com.example.totallylegal

import TempTradeAPI
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppTheme(darkTheme = isSystemInDarkTheme()) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TabLayout(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TabLayout(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Trade History", "News")

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.onPrimary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title, fontSize = 18.sp, style = TextStyle(fontFamily = FontFamily.SansSerif)) }
                )
            }
        }
        when (selectedTabIndex) {
            0 -> HomeScreen()
            1 -> ProfileScreen()
        }
    }
}

@Composable
fun TradeBox() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Politician Name",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Trade: {Data Here}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = FontFamily.SansSerif

            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        // Create the TempTradeAPI instance with the given API URL
                        val tradeAPI = TempTradeAPI("https://house-stock-watcher-data.s3-us-west-2.amazonaws.com/data/transaction_report_for_05_14_2023.json")

                        // Fetch the API data asynchronously
                        tradeAPI.fetchApiData()

                        // After fetching the data, log it to Logcat
                        val dataString = tradeAPI.getDataString()
                        Log.d("APIcall", dataString ?: "No data received")

                        // Alternatively, get the data as a Map and log it
                        /*val dataMap = tradeAPI.getDataMAP()
                        Log.d("APIcallMap", dataMap.toString())*/
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Profile", fontFamily = FontFamily.SansSerif)
            }
        }
    }
}

@Composable
fun NewsBox() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Headline",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Summary: {Data Here}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { println("Working...") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("See Article!")
            }
        }
    }
}

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(10) {
            TradeBox()
        }
    }
}

@Composable
fun ProfileScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(10) {
            NewsBox()
        }
    }
}

@Composable
fun MyAppTheme(
    darkTheme: Boolean = false, // Toggle for light/dark theme
    content: @Composable () -> Unit // Content to be styled
) {
    // Define the color scheme (use Light or Dark depending on the toggle)
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(226,226,226),
            onPrimary = Color(0xFF000000),
            background = Color(25, 25, 25),
            onBackground = Color(0xFFFFFFFF)
            // Add other colors as needed
        )
    } else {
        lightColorScheme(
            primary = Color(26, 26, 26),
            onPrimary = Color(0xFFFFFFFF),
            background = Color(0xFFFFFBFE),
            onBackground = Color(0xFF000000)
            // Add other colors as needed
        )
    }

    // Apply MaterialTheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content        // Apply theme to the content
    )
}


@Preview(showBackground = true)
@Composable
fun TabLayoutPreview() {
    MyAppTheme(darkTheme = isSystemInDarkTheme()) {
        TabLayout()
    }

}
