package com.example.totallylegal

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun LogIn(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = {
                navController.navigate("login_screen")  // Navigate to login screen
                println("Button clicked!")
            },
            modifier = Modifier
                .width(configuration.screenWidthDp.dp * 0.4f)
                .height(configuration.screenHeightDp.dp * 0.07f)
                .offset(y = configuration.screenHeightDp.dp * -0.04f)
        ) {
            Text("Log In")
        }
    }
}

@Composable
fun LogInScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,  // Horizontal centering
        verticalArrangement = Arrangement.Center
    ) {
        Text(" ")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LogIn(navController) }
        composable("login_screen") { LogInScreen(navController) }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    AppNavHost()
}


