package com.example.totallylegal

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val RobotoFontFamily = FontFamily(
    Font(R.font.roboto, FontWeight.Normal)
)

val CustomTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    labelSmall = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )
)

@Composable
fun MyAppTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(255, 255, 255, 255),
            onPrimary = Color(18, 18, 18),
            background = Color(24, 24, 24, 255),
            onBackground = Color(245, 245, 245)

            )
    } else {
        lightColorScheme(
            primary = Color(26, 26, 26),
            onPrimary = Color(0xFFFFFFFF),
            background = Color(0xFFFFFBFE),
            onBackground = Color(0xFF000000)
        )
    }
    MaterialTheme(colorScheme = colorScheme, content = content, typography = CustomTypography)
}
