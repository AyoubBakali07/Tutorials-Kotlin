package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crudapp.data.network.NetworkModule
import com.example.crudapp.data.repository.ElementRepository
import com.example.crudapp.ui.screens.ListScreen
import com.example.crudapp.ui.theme.CrudAppTheme
import com.example.crudapp.viewmodel.ElementViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialisation du repository et ViewModel
        val repo = ElementRepository(NetworkModule.apiService)

        setContent {
            CrudAppTheme {
                // Surface container utilisant la couleur d’arrière-plan du thème
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Obtention du VM avec factory
                    val vm: ElementViewModel = viewModel(
                        factory = ElementViewModel.provideFactory(repo)
                    )
                    // Collecte de la liste d’éléments
                    val elements = vm.elements.collectAsState().value

                    // Affichage de l’écran principal
                    ListScreen(
                        elements = elements,
                        onAdd    = { vm.onAddRequested() },
                        onEdit   = { vm.onEditRequested(it) },
                        onDelete = { vm.delete(it) },
                        viewModel = vm
                    )
                }
            }
        }
    }
}
