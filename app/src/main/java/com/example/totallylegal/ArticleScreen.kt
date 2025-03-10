import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.totallylegal.NewsAPI
import com.example.totallylegal.R

@Composable
fun ArticleScreen(navController: NavController, articleId: String?) {
    val context = LocalContext.current
    val tradeList = remember { mutableStateOf(NewsResponse("N/A", 1, emptyList())) }
    val article = remember { mutableStateOf<Article?>(null) }

    LaunchedEffect(Unit) {
        val response = NewsAPI().fetchTradeData()
        tradeList.value = response
        article.value = response.articles.find { it.title == articleId }
        Log.d("Size", response.articles.size.toString())
    }

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
            text = article.value?.source?.name ?: "",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = article.value?.title ?: "",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Author: ${article.value?.author ?: "N/A"}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Published: ${article.value?.publishedAt ?: "N/A"}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = article.value?.description ?: "",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Primary Link: ",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = article.value?.url ?: "",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                article.value?.url?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            }
        )
    }
}