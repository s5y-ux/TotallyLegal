import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.totallylegal.R

@Composable
fun ArticleScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back_arrow_white),
                contentDescription = "Back"
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Article Title",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Subtitle of the article",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Author: John Doe",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Date: January 1, 2024",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Source: News Agency",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "This is the body of the news article. Here you can write the content of the article. "
                    + "Make sure to provide enough information to make it look like a real news article. "
                    + "You can add more paragraphs and details as needed.",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Primary Link: ",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "https://www.example.com",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                // Handle link click
            }
        )
    }
}