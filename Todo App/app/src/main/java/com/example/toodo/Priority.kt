package com.example.toodo

import androidx.compose.ui.graphics.Color

/**
 * Enum representing priority levels with labels and colors
 */
enum class Priority(val label: String, val color: Color) {
    Haute("Haute", Color.Red),
    Moyenne("Moyenne", Color(0xFFFFA500)),
    Basse("Basse", Color.Green)
}
