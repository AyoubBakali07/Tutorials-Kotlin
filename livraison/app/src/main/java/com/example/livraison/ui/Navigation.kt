package com.example.livraison.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.livraison.viewmodel.LivraisonViewModel
import com.example.livraison.ui.screens.LivraisonListScreen
import com.example.livraison.ui.screens.EditLivraisonScreen

sealed class Screen(val route: String) {
    object List : Screen("list")
    object Edit : Screen("edit?livraisonId={livraisonId}") {
        fun withArgs(id: String?) = "edit?livraisonId=$id"
    }
}

@Composable
fun AppNav() {
    val nav = rememberNavController()
    val vm: LivraisonViewModel = viewModel()
    NavHost(nav, startDestination = Screen.List.route) {
        composable(Screen.List.route) {
            LivraisonListScreen(
                vm = vm,
                onAdd = { nav.navigate(Screen.Edit.withArgs(null)) },
                onEdit = { id -> nav.navigate(Screen.Edit.withArgs(id)) }
            )
        }
        composable(Screen.Edit.route,
            arguments = listOf(navArgument("livraisonId") { nullable = true })
        ) { backStack ->
            val id = backStack.arguments?.getString("livraisonId")
            EditLivraisonScreen(vm, id) { nav.popBackStack() }
        }
    }
}