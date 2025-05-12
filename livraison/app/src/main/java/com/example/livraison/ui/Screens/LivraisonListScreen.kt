package com.example.livraison.ui.screens

import androidx.compose.animation.animateItemPlacement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.livraison.model.Livraison
import com.example.livraison.ui.components.PriorityFilter
import com.example.livraison.viewmodel.LivraisonViewModel

@Composable
fun LivraisonListScreen(
    vm: LivraisonViewModel,
    onAdd: () -> Unit,
    onEdit: (String) -> Unit
) {
    val list by vm.filteredList.collectAsState()
    val filter by vm.filter.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mes Livraisons") }, actions = {
                IconButton(onClick = onAdd) { Icon(Icons.Default.Add, null) }
            })
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            PriorityFilter(
                current = filter,
                options = listOf("Tous", "Haute", "Moyenne", "Basse"),
                onSelect = vm::setFilter
            )
            Spacer(Modifier.height(8.dp))
            LazyColumn {
                items(list, key = { it.id }) { item ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .animateItemPlacement()
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(item.nom, style = MaterialTheme.typography.h6)
                                Text("Statut: ${item.statut}")
                                Text("Priorité: ${item.priorite}")
                            }
                            IconButton(onClick = { onEdit(item.id) }) {
                                Icon(Icons.Default.Edit, null)
                            }
                            IconButton(onClick = { vm.delete(item.id) }) {
                                Icon(Icons.Default.Delete, null)
                            }
                        }
                    }
                }
            }
        }
    }
}