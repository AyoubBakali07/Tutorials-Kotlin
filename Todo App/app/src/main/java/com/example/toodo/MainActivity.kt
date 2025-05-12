<<<<<<< HEAD
package com.example.toodo.ui
=======
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.toodo
>>>>>>> 31926c23318d3124518510df97e335c59f3b06e8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
<<<<<<< HEAD
import com.example.toodo.model.Livraison
import com.example.toodo.model.Priority
import com.example.toodo.model.Status
import com.example.toodo.viewmodel.LivraisonViewModel
=======
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toodo.ui.theme.ToodoTheme
import com.example.toodo.Priority
>>>>>>> 31926c23318d3124518510df97e335c59f3b06e8

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = LivraisonViewModel()
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AppContent(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
<<<<<<< HEAD
fun AppContent(vm: LivraisonViewModel) {
    val livs by vm.livraisonsFiltree.collectAsState()
    val filter by vm.filterPriority.collectAsState()
    val error by vm.error.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editItem by remember { mutableStateOf<Livraison?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestion des Livraisons") },
                actions = {
                    FilterDropdown(filter) { vm.setFilter(it) }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editItem = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (error != null) {
                Text(
                    text = error!!,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            LazyColumn {
                items(livs, key = { it.id }) { item ->
                    LivraisonRow(
                        livraison = item,
                        onEdit = { editItem = it; showDialog = true },
                        onDelete = { vm.deleteLivraison(it.id) }
                    )
=======
fun TodoScreen(viewModel: TodoViewModel = viewModel()) {
    val todos by viewModel.todos.collectAsState()
    var newTodoTitle by remember { mutableStateOf("") }
    var newTodoPriority by remember { mutableStateOf<Priority?>(null) }
    var titleError by remember { mutableStateOf(false) }
    var priorityError by remember { mutableStateOf(false) }
    var filterPriority by remember { mutableStateOf<Priority?>(null) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Todo List", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Filtre par priorité
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterButton("Toutes", filterPriority == null) { filterPriority = null }
                Priority.values().forEach { p ->
                    FilterButton(p.label, filterPriority == p) { filterPriority = p }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Section pour ajouter un nouveau todo
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    TextField(
                        value = newTodoTitle,
                        onValueChange = {
                            newTodoTitle = it
                            if (titleError && it.isNotBlank()) titleError = false
                        },
                        isError = titleError,
                        label = { Text("Nouveau titre") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (titleError) {
                        Text(
                            text = "Le titre ne peut pas être vide",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Choix de la priorité
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            readOnly = true,
                            value = newTodoPriority?.label ?: "",
                            onValueChange = {},
                            label = { Text("Priorité") },
                            isError = priorityError,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            Priority.values().forEach { p ->
                                DropdownMenuItem(
                                    text = { Text(p.label) },
                                    onClick = {
                                        newTodoPriority = p
                                        priorityError = false
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    if (priorityError) {
                        Text(
                            text = "Choisissez une priorité",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    titleError = newTodoTitle.isBlank()
                    priorityError = newTodoPriority == null
                    if (titleError || priorityError) return@Button

                    val todoToAdd = Todo(
                        userId = 1,
                        id = 0,
                        title = newTodoTitle,
                        completed = false,
                        priority = newTodoPriority!!
                    )
                    viewModel.addTodo(todoToAdd)
                    newTodoTitle = ""
                    newTodoPriority = null
                }) {
                    Text("Ajouter")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (todos.isEmpty()) {
                Text(text = "Chargement...", style = MaterialTheme.typography.bodyMedium)
            } else {
                val displayed = if (filterPriority == null) todos
                else todos.filter { it.priority == filterPriority }
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(displayed) { todo ->
                        TodoItem(
                            todo = todo,
                            onToggleComplete = {
                                val updated = todo.copy(completed = !todo.completed)
                                viewModel.updateTodo(updated)
                            },
                            onDelete = { viewModel.deleteTodo(todo.id) }
                        )
                    }
>>>>>>> 31926c23318d3124518510df97e335c59f3b06e8
                }
            }
        }
    }

    if (showDialog) {
        AddEditDialog(
            initial = editItem,
            onCancel = { showDialog = false },
            onConfirm = { nom, stat, prio ->
                if (editItem == null) vm.addLivraison(nom, stat, prio)
                else vm.updateLivraison(editItem!!.copy(nom = nom, statut = stat, priorite = prio))
                showDialog = false
            }
        )
    }
}

@Composable
<<<<<<< HEAD
fun LivraisonRow(
    livraison: Livraison,
    onEdit: (Livraison) -> Unit,
    onDelete: (Livraison) -> Unit
=======
fun FilterButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = if (selected)
            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        else
            ButtonDefaults.outlinedButtonColors()
    ) {
        Text(text)
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onToggleComplete: () -> Unit,
    onDelete: () -> Unit
>>>>>>> 31926c23318d3124518510df97e335c59f3b06e8
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
<<<<<<< HEAD
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(livraison.nom, style = MaterialTheme.typography.h6)
                Text(livraison.statut.label)
                Text(livraison.priorite.label)
            }
            IconButton(onClick = { onEdit(livraison) }) {
                Icon(Icons.Default.Edit, contentDescription = "Modifier")
            }
            IconButton(onClick = { onDelete(livraison) }) {
                Icon(Icons.Default.Delete, contentDescription = "Supprimer")
            }
        }
    }
}

@Composable
fun FilterDropdown(
    selected: Priority?,
    onSelect: (Priority?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        TextButton(onClick = { expanded = true }) {
            Text(selected?.label ?: "Toutes")
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = { onSelect(null); expanded = false }) {
                Text("Toutes")
            }
            Priority.values().forEach { prio ->
                DropdownMenuItem(onClick = { onSelect(prio); expanded = false }) {
                    Text(prio.label)
                }
=======
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = todo.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (todo.completed) "Terminé" else "En attente",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Priorité : ${todo.priority.label}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onToggleComplete) { Text("Basculer") }
                Button(onClick = onDelete) { Text("Supprimer") }
>>>>>>> 31926c23318d3124518510df97e335c59f3b06e8
            }
        }
    }
}

@Composable
<<<<<<< HEAD
fun AddEditDialog(
    initial: Livraison?,
    onCancel: () -> Unit,
    onConfirm: (String, Status, Priority) -> Unit
) {
    var nom by remember { mutableStateOf(initial?.nom ?: "") }
    var statut by remember { mutableStateOf(initial?.statut ?: Status.EN_COURS) }
    var priorite by remember { mutableStateOf(initial?.priorite ?: Priority.MOYENNE) }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(if (initial == null) "Ajouter Livraison" else "Modifier Livraison") },
        text = {
            Column {
                OutlinedTextField(
                    value = nom,
                    onValueChange = { nom = it },
                    label = { Text("Nom") },
                    isError = nom.isBlank()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Status.values().forEach { st ->
                    Row(Modifier.fillMaxWidth()) {
                        RadioButton(
                            selected = statut == st,
                            onClick = { statut = st }
                        )
                        Text(st.label, modifier = Modifier.padding(start = 4.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Priority.values().forEach { pr ->
                    Row(Modifier.fillMaxWidth()) {
                        RadioButton(
                            selected = priorite == pr,
                            onClick = { priorite = pr }
                        )
                        Text(pr.label, modifier = Modifier.padding(start = 4.dp))
                    }
                }
                error?.let { Text(it, color = MaterialTheme.colors.error) }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (nom.isBlank()) error = "Nom requis"
                else onConfirm(nom, statut, priorite)
            }) {
                Text("Valider")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) { Text("Annuler") }
        }
    )
}
=======
fun TodoPreview() {
    ToodoTheme {
        TodoScreen()
    }
}
>>>>>>> 31926c23318d3124518510df97e335c59f3b06e8
