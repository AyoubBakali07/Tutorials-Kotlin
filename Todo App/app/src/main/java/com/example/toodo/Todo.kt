// File: app/src/main/java/com/example/toodo/Todo.kt
package com.example.toodo

import com.google.gson.annotations.SerializedName
import kotlin.jvm.Transient

enum class Priority(val label: String) {
    HIGH("Haute"),
    MEDIUM("Moyenne"),
    LOW("Basse")
}

data class Todo(
    @SerializedName("userId")    val userId: Int,
    @SerializedName("id")        val id: Int,
    @SerializedName("title")     val title: String,
    @SerializedName("status")   val status: String,
    @SerializedName("completed") val completed: Boolean,
    @Transient                   val priority: Priority = Priority.MEDIUM
)
