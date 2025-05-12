package com.example.toodo

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
            ToodoTheme {
                TodoScreen()
            }
        }
    }
}

@Composable
fun TodoScreen(viewModel: TodoViewModel = viewModel()) {
    // Observe the list of todos from the ViewModel
    val todos by viewModel.todos.collectAsState()
    var newTodoTitle by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Todo List", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Section to add a new todo
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = newTodoTitle,
                    onValueChange = { newTodoTitle = it },
                    label = { Text("New Todo Title") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if (newTodoTitle.isNotBlank()) {
                        // Create a new Todo. In JSONPlaceholder the id is generated on the server, but here we simulate with 0.
                        val todoToAdd = Todo(userId = 1, id = 0, title = newTodoTitle, completed = false)
                        viewModel.addTodo(todoToAdd)
                        newTodoTitle = ""
                    }
                }) {
                    Text("Add")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (todos.isEmpty()) {
                Text(text = "Chargement...")
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(todos) { todo ->
                        TodoItem(todo,
                            onToggleComplete = {
                                // Toggle the completed status when update is requested
                                val updatedTodo = todo.copy(completed = !todo.completed)
                                viewModel.updateTodo(updatedTodo)
                            },
                            onDelete = {
                                viewModel.deleteTodo(todo.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onToggleComplete: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = todo.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (todo.completed) "Completed" else "Pending",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onToggleComplete) {
                    Text("Toggle")
                }
                Button(onClick = onDelete) {
                    Text("Delete")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoPreview() {
    ToodoTheme {
        TodoScreen()
    }
}
