package com.example.toodo;

public class test {
    // MainActivity.kt
//package com.example.toodo
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//            import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//            import androidx.compose.runtime.*
//            import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.toodo.ui.theme.ToodoTheme
//
//    class MainActivity : ComponentActivity() {
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            setContent {
//                ToodoTheme {
//                    TodoScreen()
//                }
//            }
//        }
//    }
//
//// Priority options
//    enum class Priority(val label: String, val color: Color) {
//        Haute("Haute", Color.Red),
//        Moyenne("Moyenne", Color(0xFFFFA500)), // Orange
//        Basse("Basse", Color.Green)
//    }
//
//    @Composable
//    fun PriorityDropdown(
//            selected: Priority,
//            onSelect: (Priority) -> Unit
//) {
//        var expanded by remember { mutableStateOf(false) }
//        Box {
//            Button(onClick = { expanded = true }) {
//                Text("Priorité : ${selected.label}")
//            }
//            DropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false }
//            ) {
//                Priority.values().forEach { priority ->
//                        DropdownMenuItem(
//                                text = { Text(priority.label) },
//                                onClick = {
//                                        onSelect(priority)
//                                        expanded = false
//                                }
//                        )
//                }
//            }
//        }
//    }
//
//    @Composable
//    fun TodoScreen(viewModel: TodoViewModel = viewModel()) {
//        val todos by viewModel.todos.collectAsState()
//        var newTitle by remember { mutableStateOf("") }
//        var newPriority by remember { mutableStateOf(Priority.Moyenne) }
//
//        // Count of pending tasks
//        val pendingCount = todos.count { !it.completed }
//
//        Surface(modifier = Modifier.fillMaxSize()) {
//            Column(
//                    modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp),
//                    verticalArrangement = Arrangement.Top,
//                    horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(text = "Todo List", style = MaterialTheme.typography.headlineMedium)
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(text = "Tâches en cours : $pendingCount", style = MaterialTheme.typography.bodyLarge)
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Add new todo
//                Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                ) {
//                    PriorityDropdown(selected = newPriority, onSelect = { newPriority = it })
//                    Spacer(modifier = Modifier.width(8.dp))
//                    TextField(
//                            value = newTitle,
//                            onValueChange = { newTitle = it },
//                            label = { Text("Nouvelle tâche") },
//                            modifier = Modifier.weight(1f)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Button(onClick = {
//                    if (newTitle.isNotBlank()) {
//                        val todo = Todo(
//                                userId = 1,
//                                id = 0,
//                                title = newTitle,
//                                completed = false,
//                                priority = newPriority.label
//                        )
//                        viewModel.addTodo(todo)
//                        newTitle = ""
//                        newPriority = Priority.Moyenne
//                    }
//                }) {
//                        Text("Ajouter")
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                if (todos.isEmpty()) {
//                    Text(text = "Chargement...", style = MaterialTheme.typography.bodyLarge)
//                } else {
//                    LazyColumn(modifier = Modifier.fillMaxSize()) {
//                        items(todos) { todo ->
//                                TodoItem(
//                                        todo = todo,
//                                        onToggleComplete = {
//                                                viewModel.updateTodo(todo.copy(completed = !todo.completed))
//                                        },
//                                        onDelete = { viewModel.deleteTodo(todo.id) }
//                                )
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Composable
//    fun TodoItem(
//            todo: Todo,
//            onToggleComplete: () -> Unit,
//    onDelete: () -> Unit
//) {
//        Card(
//                modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 4.dp),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//        ) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(text = todo.title, style = MaterialTheme.typography.titleMedium)
//                    // Priority indicator
//                    Text(
//                            text = todo.priority,
//                            color = when (todo.priority) {
//                        Priority.Haute.label -> Priority.Haute.color
//                        Priority.Moyenne.label -> Priority.Moyenne.color
//                        else -> Priority.Basse.color
//                    },
//                    style = MaterialTheme.typography.bodyMedium
//                )
//                }
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                        text = if (todo.completed) "Terminée" else "En attente",
//                        style = MaterialTheme.typography.bodyMedium
//            )
//                Spacer(modifier = Modifier.height(8.dp))
//                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                    Button(onClick = onToggleComplete) { Text("Basculer") }
//                    Button(onClick = onDelete) { Text("Supprimer") }
//                }
//            }
//        }
//    }
//
//    @Preview(showBackground = true)
//    @Composable
//    fun TodoPreview() {
//        ToodoTheme {
//            TodoScreen()
//        }
//    }
//
//// TodoViewModel.kt
//package com.example.toodo
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//    class TodoViewModel : ViewModel() {
//        private val _todos = MutableStateFlow<List<Todo>>(emptyList())
//        val todos: StateFlow<List<Todo>> = _todos
//
//        init { fetchTodos() }
//
//        private fun fetchTodos() {
//            viewModelScope.launch {
//                try {
//                    val list = RetrofitClient.api.getTodos()
//                    // Ensure default priority
//                    _todos.value = list.map { it.copy(priority = it.priority) }
//                } catch (e: Exception) {
//                    _todos.value = emptyList()
//                }
//            }
//        }
//
//        fun addTodo(todo: Todo) {
//            viewModelScope.launch {
//                try {
//                    val created = RetrofitClient.api.addTodo(todo)
//                    _todos.value = _todos.value + created
//                } catch (_: Exception) {}
//            }
//        }
//
//        fun updateTodo(updated: Todo) {
//            viewModelScope.launch {
//                try {
//                    val resp = RetrofitClient.api.updateTodo(updated.id, updated)
//                    _todos.value = _todos.value.map { if (it.id == updated.id) resp else it }
//                } catch (_: Exception) {}
//            }
//        }
//
//        fun deleteTodo(id: Int) {
//            viewModelScope.launch {
//                try {
//                    RetrofitClient.api.deleteTodo(id)
//                    _todos.value = _todos.value.filter { it.id != id }
//                } catch (_: Exception) {}
//            }
//        }
//    }
//
//// Todo.kt
//package com.example.toodo
//
//    data class Todo(
//            val userId: Int,
//            val id: Int,
//            val title: String,
//            val completed: Boolean,
//            val priority: String = "Moyenne"
//    )
//
//// RetrofitClient.kt
//package com.example.toodo
//
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//    object RetrofitClient {
//        val api: ApiService by lazy {
//            Retrofit.Builder()
//                    .baseUrl("https://jsonplaceholder.typicode.com/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//                    .create(ApiService::class.java)
//        }
//    }
//
//// ApiService.kt
//package com.example.toodo
//
//import retrofit2.http.Body
//import retrofit2.http.DELETE
//import retrofit2.http.GET
//import retrofit2.http.POST
//import retrofit2.http.PUT
//import retrofit2.http.Path
//
//    interface ApiService {
//        @GET("todos")
//        suspend fun getTodos(): List<Todo>
//
//        @POST("todos")
//        suspend fun addTodo(@Body todo: Todo): Todo
//
//        @PUT("todos/{id}")
//        suspend fun updateTodo(@Path("id") id: Int, @Body todo: Todo): Todo
//
//        @DELETE("todos/{id}")
//        suspend fun deleteTodo(@Path("id") id: Int)
//    }

}
