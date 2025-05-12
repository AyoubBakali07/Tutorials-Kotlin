package com.example.efm_mobile_v3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.efm_mobile_v3.models.Intervention

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrudApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrudApp(interventionVM: InterventionViewModel = viewModel()) {
    var showDialog by remember { mutableStateOf(false) }
    var editingInter by remember { mutableStateOf<Intervention?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Interventions") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editingInter = null
                showDialog = true
            }) {
                Text("+")
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            InterventionList(
                items = interventionVM.interventions,
                onEdit = {
                    editingInter = it
                    showDialog = true
                },
                onDelete = { interventionVM.remove(it) }
            )
        }

        if (showDialog) {
            AddEditDialog(
                initial = editingInter,
                onCancel = { showDialog = false },
                onConfirm = { nom, statut, priorite ->
                    if (nom.isBlank()) return@AddEditDialog
                    val updated = editingInter
                        ?.copy(nom = nom, statut = statut, priorite = priorite)
                        ?: Intervention(nom = nom, statut = statut, priorite = priorite)
                    if (editingInter == null) interventionVM.add(updated)
                    else interventionVM.update(updated)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun InterventionList(
    items: List<Intervention>,
    onEdit: (Intervention) -> Unit,
    onDelete: (Intervention) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(items) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = item.nom)
                        Text(text = "Statut : ${item.statut}")
                        Text(text = "Priorité : ${item.priorite}")
                    }
                    Row {
                        IconButton(onClick = { onEdit(item) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Modifier")
                        }
                        IconButton(onClick = { onDelete(item) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Supprimer")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditDialog(
    initial: Intervention? = null,
    onCancel: () -> Unit,
    onConfirm: (nom: String, statut: String, priorite: String) -> Unit
) {
    var nom by remember { mutableStateOf(initial?.nom ?: "") }
    var statut by remember { mutableStateOf(initial?.statut ?: "En cours") }
    var priorite by remember { mutableStateOf(initial?.priorite ?: "Moyenne") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(if (initial == null) "Ajouter Intervention" else "Modifier Intervention") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = nom,
                    onValueChange = { nom = it },
                    label = { Text("Nom") },
                    modifier = Modifier.fillMaxWidth()
                )

                DropdownSelector(
                    label = "Statut",
                    options = listOf("En cours", "Terminé", "Annulé"),
                    selected = statut,
                    onSelect = { statut = it }
                )

                DropdownSelector(
                    label = "Priorité",
                    options = listOf("Haute", "Moyenne", "Basse"),
                    selected = priorite,
                    onSelect = { priorite = it }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(nom, statut, priorite) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Annuler")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = { },
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
