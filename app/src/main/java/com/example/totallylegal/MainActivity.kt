package com.example.totallylegal

import ArticleScreen
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.totallylegal.LogIn

class MainActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayer = MediaPlayer.create(this, R.raw.startup)
        mediaPlayer?.start()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MyAppTheme(darkTheme = isSystemInDarkTheme()) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = "main_screen") {
                            composable("main_screen") {
                                TabLayout(navController)
                            }
                            composable(
                                "article_screen/{id}",
                                arguments = listOf(navArgument("id") { type = NavType.StringType })
                            ) { backStackEntry ->
                                ArticleScreen(navController, articleId = backStackEntry.arguments?.getString("id"))
                            }
                            composable("news_screen") {
                                NewsScreen()
                            }
                        }
                    }
                    AppNavHost()
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Release resources when done
        mediaPlayer = null
    }
}

