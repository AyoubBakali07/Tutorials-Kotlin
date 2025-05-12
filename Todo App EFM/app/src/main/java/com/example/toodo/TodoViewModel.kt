package com.example.toodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    // Holds the list of todos, initially empty.
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    init {
        fetchTodos()
    }

    private fun fetchTodos() {
        viewModelScope.launch {
            try {
                val todosList = RetrofitClient.api.getTodos()
                _todos.value = todosList
            } catch (e: Exception) {
                // Log or handle error here
                _todos.value = emptyList()
            }
        }
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                val newTodo = RetrofitClient.api.addTodo(todo)
                // Append the new todo to the list
                _todos.value = _todos.value + newTodo
            } catch (e: Exception) {
                // Handle error (logging, showing a message, etc.)
            }
        }
    }

    fun updateTodo(updatedTodo: Todo) {
        viewModelScope.launch {
            try {
                val todoResponse = RetrofitClient.api.updateTodo(updatedTodo.id, updatedTodo)
                // Replace the old todo with the updated one
                _todos.value = _todos.value.map { if (it.id == updatedTodo.id) todoResponse else it }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun deleteTodo(todoId: Int) {
        viewModelScope.launch {
            try {
                RetrofitClient.api.deleteTodo(todoId)
                // Remove the todo from the list
                _todos.value = _todos.value.filter { it.id != todoId }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
