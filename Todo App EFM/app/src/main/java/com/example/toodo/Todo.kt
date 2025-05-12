// File: Todo.kt
package com.example.toodo

<<<<<<< HEAD:Todo App EFM/app/src/main/java/com/example/toodo/Todo.kt
 data class Todo (
    val userId: Int,
=======
import com.google.gson.annotations.SerializedName
import kotlin.jvm.Transient

enum class Priority(val label: String) {
    HIGH("Haute"),
    MEDIUM("Moyenne"),
    LOW("Basse")
}

data class Todo(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
>>>>>>> 31926c23318d3124518510df97e335c59f3b06e8:Todo App/app/src/main/java/com/example/toodo/Todo.kt
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("completed")
    val completed: Boolean,

    @Transient
    val priority: Priority = Priority.MEDIUM
)
