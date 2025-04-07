package com.example.toodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    // État observable qui sera utilisé par l'UI
    private val _todos = MutableStateFlow<List<Task>>(emptyList())
    val todos: StateFlow<List<Task>> = _todos

    init {
        fetchTodos()
    }

    private fun fetchTodos() {
        viewModelScope.launch {
            try {
                // Appel réseau via Retrofit avec coroutine
                val todoList = RetrofitClient.api.getTask()
                _todos.value = todoList
            } catch (e: Exception) {
                // You might want to handle the error differently
                _todos.value = emptyList() // or maintain previous value and show error elsewhere
            }
        }
    }
}