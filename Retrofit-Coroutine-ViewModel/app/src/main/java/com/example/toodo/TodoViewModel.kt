package com.example.toodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    // État observable qui sera utilisé par l'UI
    private val _todoTitle = MutableStateFlow("Chargement...")
    val todoTitle: StateFlow<String> = _todoTitle

    init {
        fetchTodo()
    }

    private fun fetchTodo() {
        viewModelScope.launch {
            try {
                // Appel réseau via Retrofit avec coroutine
                val todo = RetrofitClient.api.getTodo()
                _todoTitle.value = todo.title
            } catch (e: Exception) {
                _todoTitle.value = "Erreur : ${e.message}"
            }
        }
    }
}
