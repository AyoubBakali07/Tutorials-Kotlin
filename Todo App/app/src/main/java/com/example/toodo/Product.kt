package com.example.toodo

 data class Product (
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean,
//    val priority: String
    val priority: String = Priority.Moyenne.label

 )