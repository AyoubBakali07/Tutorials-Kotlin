package com.example.tuto_counter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tuto_counter.ui.theme.Tuto_counterTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tuto_counterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent()
                }
            }
        }
    }
}

@Composable
fun AppContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to the Interactive App", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        InteractiveButton()
        Spacer(modifier = Modifier.height(16.dp))
        DateandTimeDisplay()

    }
}


@Composable
fun InteractiveButton() {
    var count by remember { mutableStateOf(0) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Clics : $count")
        Button(onClick = { count++ }) {
            Text("Cliquez-moi")
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun DateandTimeDisplay(){
    var timeText by remember { mutableStateOf("Loading...") }
    var coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        coroutineScope.launch {
        while (true){
            timeText = fetchData()
        }
            }
    }
    Text(timeText, style = MaterialTheme.typography.bodyLarge)

}


@SuppressLint("NewApi")
suspend fun fetchData(): String {
    delay(5000)
    val currentTime = LocalTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return "Date et heure actuelles : ${currentTime.format(formatter)}"
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AsyncDataPreview() {
    Tuto_counterTheme {
        AppContent()

    }
}
