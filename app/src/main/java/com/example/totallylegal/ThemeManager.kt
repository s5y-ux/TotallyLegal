package com.example.totallylegal

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MyAppTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(226, 226, 226),
            onPrimary = Color(0xFF000000),
            background = Color(25, 25, 25),
            onBackground = Color(0xFFFFFFFF)
        )
    } else {
        lightColorScheme(
            primary = Color(26, 26, 26),
            onPrimary = Color(0xFFFFFFFF),
            background = Color(0xFFFFFBFE),
            onBackground = Color(0xFF000000)
        )
    }
    MaterialTheme(colorScheme = colorScheme, content = content)
}
