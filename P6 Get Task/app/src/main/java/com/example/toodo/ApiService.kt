package com.example.toodo
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("todos")
    suspend fun getTask(): List<Task>  // Using the corrected data class Todo
}
