package com.example.totallylegal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TabLayout(navController: NavController, modifier: Modifier = Modifier) {

    // Used to keep track of what tab you are on and the list of tabs
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
                    text = {
                        Text(
                            title,
                            fontSize = 18.sp
                        )
                    }
                )
            }
        }

        // This is a Kotlin switch statement that uses the HomeScreen
        // And profile screen composables
        when (selectedTabIndex) {
            0 -> HomeScreen(navController)
            1 -> NewsScreen(navController)
        }
    }
}
