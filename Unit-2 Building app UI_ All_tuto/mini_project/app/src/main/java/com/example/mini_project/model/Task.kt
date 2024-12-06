package com.example.mini_project.model
data class Task(
    val id: Int,
    val description: String,
    val isCompleted: Boolean = false
)