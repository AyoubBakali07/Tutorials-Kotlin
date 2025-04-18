package com.example.toodo

import CounterViewModel
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toodo.ui.theme.ToodoTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.material.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTodo()


        }
    }
}
@Composable
fun MyTodo() {
    var title by remember { mutableStateOf("Chargement...") }

    // Launch a coroutine to perform the network call asynchronously
    LaunchedEffect(Unit) {
        try {
            // Execute the API call on the IO dispatcher
            val todo = withContext(Dispatchers.IO) { RetrofitClient.api.getTodo() }
            title = todo.title
        } catch (e: Exception) {
            title = "Erreur : ${e.message}"
        }
    }

    // Simple UI displaying the fetched title
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToodoTheme {
        MyTodo()
    }
}
