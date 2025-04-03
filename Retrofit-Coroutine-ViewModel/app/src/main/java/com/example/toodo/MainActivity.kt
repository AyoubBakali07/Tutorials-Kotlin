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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toodo.ui.theme.ToodoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTodo()
            
        }
    }
}
@Composable
fun MyTodo(viewModel: TodoViewModel = viewModel()) {
    // Observe le StateFlow et met à jour l'UI en cas de changement
    val title by viewModel.todoTitle.collectAsState()

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
