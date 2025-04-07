package com.example.toodo

import CounterViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val tasks by viewModel.todos.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks) { task ->
                TaskCard(task = task)
            }
        }
    }
}
@Composable
fun TaskCard(task: Task){
    Card(
        modifier = Modifier
                .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
    Text(
        text = task.title,
                modifier = Modifier
                .padding(16.dp),
        style = MaterialTheme.typography.bodyLarge
    )
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToodoTheme {
        MyTodo()
    }
}