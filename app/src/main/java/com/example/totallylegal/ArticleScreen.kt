package com.example.totallylegal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ArticleScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("This is the Article Screen!")
    }
}