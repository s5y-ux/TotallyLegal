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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.*

@Composable
fun TabLayout(navController: NavController, modifier: Modifier = Modifier) {

    // Used to keep track of what tab you are on and the list of tabs
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Politicians", "Trades", "News")

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
        var isSwipeEnabled = true
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        val swipeThreshold = 5                          //SENSITIVE
                        if (isSwipeEnabled) {
                            if (dragAmount < -swipeThreshold && selectedTabIndex < 2) {
                                selectedTabIndex++
                            } else if (dragAmount > swipeThreshold && selectedTabIndex > 0) {
                                selectedTabIndex--
                            }

                            isSwipeEnabled = false
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(300) //seems 'bout right
                                isSwipeEnabled = true
                            }
                        }
                    }
                }
        ) {
            when (selectedTabIndex) {
                0 -> HomeScreen(navController)
                1 -> TradesScreen()
                2 -> NewsScreen(navController)
            }
        }
    }
}
