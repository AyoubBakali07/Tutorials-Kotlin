package com.example.mini_project.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mini_project.model.Task
import com.example.mini_project.ui.components.TaskInputField
import com.example.mini_project.ui.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen() {
    // List of tasks managed using rememberSaveable
    var tasks by rememberSaveable { mutableStateOf(listOf<Task>()) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Liste de tâches") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Input field for adding a task
            TaskInputField(onAddTask = { description ->
                tasks = tasks + Task(id = tasks.size + 1, description = description)
            })

            // LazyColumn for displaying tasks
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onToggleComplete = { updatedTask ->
                            tasks = tasks.map {
                                if (it.id == updatedTask.id) it.copy(isCompleted = !it.isCompleted) else it
                            }
                        }
                    )
                }
            }

            // Button to remove completed tasks
            Button(
                onClick = {
                    tasks = tasks.filter { !it.isCompleted }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Supprimer les tâches terminées")
            }
        }
    }
}
