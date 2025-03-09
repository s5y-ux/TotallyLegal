package com.example.totallylegal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MyAppTheme(darkTheme = isSystemInDarkTheme()) {
                //val navController = rememberNavController()  // Create NavController

                NavHost(navController = navController, startDestination = "main_screen") {
                    composable("main_screen") {
                        HomeScreen(navController)
                    }
                    composable("article_screen") {
                        ArticleScreen()
                    }
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TabLayout(navController, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

