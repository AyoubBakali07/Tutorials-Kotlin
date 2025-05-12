package com.example.livraison.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun PriorityFilter(current: String, options: List<String>, onSelect: (String) -> Unit) {
    var open by remember { mutableStateOf(false) }
    Row(modifier = Modifier.clickable { open = true }) {
        Text("Filtrer: $current")
        DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
            options.forEach { pri ->
                DropdownMenuItem(onClick = {
                    onSelect(pri)
                    open = false
                }) {
                    Text(pri)
                }
            }
        }
    }
}