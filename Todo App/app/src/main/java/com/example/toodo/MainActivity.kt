@file:OptIn(ExperimentalMaterial3Api::class)

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
import com.example.toodo.Priority

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(viewModel: TodoViewModel = viewModel()) {
    val todos by viewModel.todos.collectAsState()
    var newTodoTitle by remember { mutableStateOf("") }
    var newTodoPriority by remember { mutableStateOf<Priority?>(null) }
    var titleError by remember { mutableStateOf(false) }
    var priorityError by remember { mutableStateOf(false) }
    var filterPriority by remember { mutableStateOf<Priority?>(null) }

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

            // Filtre par priorité
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterButton("Toutes", filterPriority == null) { filterPriority = null }
                Priority.values().forEach { p ->
                    FilterButton(p.label, filterPriority == p) { filterPriority = p }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Section pour ajouter un nouveau todo
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    TextField(
                        value = newTodoTitle,
                        onValueChange = {
                            newTodoTitle = it
                            if (titleError && it.isNotBlank()) titleError = false
                        },
                        isError = titleError,
                        label = { Text("Nouveau titre") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (titleError) {
                        Text(
                            text = "Le titre ne peut pas être vide",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Choix de la priorité
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            readOnly = true,
                            value = newTodoPriority?.label ?: "",
                            onValueChange = {},
                            label = { Text("Priorité") },
                            isError = priorityError,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            Priority.values().forEach { p ->
                                DropdownMenuItem(
                                    text = { Text(p.label) },
                                    onClick = {
                                        newTodoPriority = p
                                        priorityError = false
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    if (priorityError) {
                        Text(
                            text = "Choisissez une priorité",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    titleError = newTodoTitle.isBlank()
                    priorityError = newTodoPriority == null
                    if (titleError || priorityError) return@Button

                    val todoToAdd = Todo(
                        userId = 1,
                        id = 0,
                        title = newTodoTitle,
                        completed = false,
                        priority = newTodoPriority!!
                    )
                    viewModel.addTodo(todoToAdd)
                    newTodoTitle = ""
                    newTodoPriority = null
                }) {
                    Text("Ajouter")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (todos.isEmpty()) {
                Text(text = "Chargement...", style = MaterialTheme.typography.bodyMedium)
            } else {
                val displayed = if (filterPriority == null) todos
                else todos.filter { it.priority == filterPriority }
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(displayed) { todo ->
                        TodoItem(
                            todo = todo,
                            onToggleComplete = {
                                val updated = todo.copy(completed = !todo.completed)
                                viewModel.updateTodo(updated)
                            },
                            onDelete = { viewModel.deleteTodo(todo.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = if (selected)
            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        else
            ButtonDefaults.outlinedButtonColors()
    ) {
        Text(text)
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
                text = if (todo.completed) "Terminé" else "En attente",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Priorité : ${todo.priority.label}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onToggleComplete) { Text("Basculer") }
                Button(onClick = onDelete) { Text("Supprimer") }
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