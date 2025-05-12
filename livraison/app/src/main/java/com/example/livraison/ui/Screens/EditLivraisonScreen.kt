package com.example.livraison.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.livraison.model.Livraison
import com.example.livraison.viewmodel.LivraisonViewModel

@Composable
fun EditLivraisonScreen(
    vm: LivraisonViewModel,
    livraisonId: String?,
    onDone: () -> Unit
) {
    val existing = remember { vm.list.value.find { it.id == livraisonId } }
    var nom by remember { mutableStateOf(existing?.nom ?: "") }
    var statut by remember { mutableStateOf(existing?.statut ?: "") }
    var priorite by remember { mutableStateOf(existing?.priorite ?: "") }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(topBar = {
        TopAppBar(title = { Text(if (existing == null) "Nouvelle Livraison" else "Modifier Livraison") })
    }) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = nom,
                onValueChange = { nom = it },
                label = { Text("Nom") },
                isError = error != null && nom.isBlank()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = statut,
                onValueChange = { statut = it },
                label = { Text("Statut") },
                isError = error != null && statut.isBlank()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = priorite,
                onValueChange = { priorite = it },
                label = { Text("Priorité") },
                isError = error != null && priorite.isBlank()
            )
            if (error != null) {
                Text(error!!, color = MaterialTheme.colors.error)
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                if (nom.isBlank() || statut.isBlank() || priorite.isBlank()) {
                    error = "Tous les champs sont requis"
                } else {
                    val l = Livraison(id = existing?.id ?: "", nom = nom, statut = statut, priorite = priorite)
                    if (existing == null) {
                        vm.add(l) { onDone() }
                    } else {
                        vm.update(l) { onDone() }
                    }
                }
            }) {
                Text("Enregistrer")
            }
        }
    }
}